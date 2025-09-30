package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import io.github.mangocrisp.spring.taybct.auth.security.util.OAuth2Util;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * 请求参数转换身份验证信息，这个和之前版本的 AbstractTokenGranter 差不多
 *
 * @author xijieyin <br> 2022/12/28 23:15
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter
 */
@RequiredArgsConstructor
public class CustomizeAuthenticationConverter implements AuthenticationConverter {

    /**
     * 授权类型
     */
    private final AuthorizationGrantType authorizationGrantType;
    /**
     * 将默认的自定义鉴权令牌转换成自己想要的类型，这里方便后面在 provider 里面做 supports
     */
    private final Converter<CustomizeAuthenticationToken, ? extends CustomizeAuthenticationToken> customizeAuthenticationGenerator;
    /**
     * 主休键，比如用户名，手机号
     */
    private final String principalKey;
    /**
     * 凭证键，比如密码，验证码？
     */
    private final String credentialsKey;
    /**
     * 必要的参数，这里已经提供了默认的两个参数，用户名和密码，你可以通过这个 consumer 来操作这些参数，添加或者修改，就很随意
     * <br>
     * 参数名:描述
     */
    private final Consumer<Map<String, String>> requiredParameterConsumer;
    /**
     * 默认只有对参数非空的判断，你还可以指定其他的规则
     */
    private final Consumer<HttpServletRequest> additionalParameterChecks;

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE).trim();
        if (!authorizationGrantType.getValue().equals(grantType)) {
            return null;
        }
        // 这样才能创建一个 session id ?
        request.getSession();
        // 获取到客户端的身份验证
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        HashMap<String, String> requiredParameter = new HashMap<>();
        requiredParameterConsumer.accept(requiredParameter);

        // 过滤出用户名和密码的其他参数
        Map<String, Object> additionalParameters = new HashMap<>();

        MultiValueMap<String, String> parameters = OAuth2Util.getParameters(request);

        // 用户名 (REQUIRED)
        AtomicReference<String> principal = new AtomicReference<>();
        // 密码
        AtomicReference<String> credentials = new AtomicReference<>();

        requiredParameter.forEach((key, description) -> {
            String value = parameters.getFirst(key);
            if (!StringUtils.hasText(value) ||
                    parameters.get(key).size() != 1) {
                throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST
                        , description
                        , null));
            }
        });

        additionalParameterChecks.accept(request);

        parameters.forEach((k, v) -> {
            if (principalKey != null) {
                if (k.equalsIgnoreCase(principalKey)) {
                    principal.set(v.get(0));
                }
            }
            if (credentialsKey != null) {
                if (k.equalsIgnoreCase(credentialsKey)) {
                    credentials.set(v.get(0));
                }
            }
            if (!k.equals(AuthHeaderConstants.PASSWORD_KEY) &&
                    !k.equals(AuthHeaderConstants.USER_NAME_KEY)) {
                additionalParameters.put(k, v.get(0));
            }
        });

        return customizeAuthenticationGenerator.convert(new CustomizeAuthenticationToken(authorizationGrantType
                , clientPrincipal
                , additionalParameters
                , principal.get()
                , credentials.get()
                , buildDetails(request)));
    }

    /**
     * 构建 session id
     *
     * @param request 请求
     * @return details
     */
    protected Object buildDetails(HttpServletRequest request) {
        return this.authenticationDetailsSource.buildDetails(request);
    }

}
