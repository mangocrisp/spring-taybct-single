package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.io.Serial;
import java.util.Map;

/**
 * 就是把表单信息转换成身份实体类？？？方便统一来做处理吧
 *
 * @author xijieyin <br> 2022/12/28 23:16
 * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 * @see org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 */
public class CustomizeAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    @Serial
    private static final long serialVersionUID = -233580409780109990L;
    /**
     * 这个在验证入方向，也就是往服务器传值是用户名，出去就是用户信息了，但是，如果是强制只能是字符串就是用户名，这个在
     */
    private final Object principal;

    /**
     * 凭证，密码
     */
    private final Object credentials;


    public CustomizeAuthenticationToken(CustomizeAuthenticationToken token) {
        super(token.getGrantType(), (Authentication) token.getClientPrincipal(), token.getAdditionalParameters());
        this.principal = token.getPrincipal();
        this.credentials = token.getCredentials();
        this.setDetails(token.getDetails());
    }

    public CustomizeAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                        Authentication clientPrincipal
            , Map<String, Object> additionalParameters
            , String principal
            , @Nullable String credentials
            , Object detail) {
        super(authorizationGrantType, clientPrincipal, additionalParameters);
        this.principal = principal;
        this.credentials = credentials;
        this.setDetails(detail);
    }


    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * 获取到客户端的 Principal
     *
     * @return ClientPrincipal
     */
    public Object getClientPrincipal() {
        return super.getPrincipal();
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }
}
