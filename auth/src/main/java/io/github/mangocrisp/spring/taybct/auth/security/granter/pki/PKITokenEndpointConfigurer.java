package io.github.mangocrisp.spring.taybct.auth.security.granter.pki;

import io.github.mangocrisp.spring.taybct.auth.security.granter.IOtherTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticationConverter;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticationProvider;
import io.github.mangocrisp.spring.taybct.tool.pki.prop.PKIProp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.function.Function;

/**
 * PKI的授权码鉴权配置
 */
@RequiredArgsConstructor
@Slf4j
public class PKITokenEndpointConfigurer implements IOtherTokenEndpointConfigurer {

    public static final AuthorizationGrantType GRANT_TYPE = new AuthorizationGrantType("pki");

    final PasswordEncoder passwordEncoder;
    final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    /**
     * 鉴权管理，可以从这里根据刷新 token 获取新的 token 什么的
     */
    final OAuth2AuthorizationService authorizationService;
    /**
     * PKI 配置
     */
    final PKIProp pkiProp;
    /**
     * 用于查找缓存 code
     */
    private final StringRedisTemplate redisTemplate;
    /**
     * 根据身份证号查找用户
     */
    final Function<String, UserDetails> userFinder;

    @Override
    public void customize(OAuth2TokenEndpointConfigurer oAuth2TokenEndpointConfigurer) {

        // 因为和用户名差不多，是使用微信 code 来登录
        CustomizeAuthenticationConverter demoConverter = new CustomizeAuthenticationConverter(
                GRANT_TYPE
                , PKIAuthenticationToken::new
                , "idCard"
                , "code"
                , map -> {
            map.put("idCard", "[idCard]不能为空，且只能有一个值");
            map.put("code", "[code]不能为空，且只能有一个值");
        },
                request -> {
                });
        oAuth2TokenEndpointConfigurer.accessTokenRequestConverter(demoConverter);

        CustomizeAuthenticationProvider demoAuthenticationProvider = new CustomizeAuthenticationProvider(
                GRANT_TYPE
                , customizeAuthenticationToken -> userFinder.apply((String) customizeAuthenticationToken.getPrincipal())
                , passwordEncoder
                , tokenGenerator
                , () -> PKIAuthenticationToken.class
                , authorizationService);

        demoAuthenticationProvider.setAdditionalAuthenticationChecks((UserDetails userDetails, Authentication authentication) -> {
            String idCard = userDetails.getUsername();
            String cacheKey = pkiProp.getOauthCachePrefix() + "pki:" + idCard;
            String cacheCode = redisTemplate.opsForValue().get(cacheKey);
            if (cacheCode == null) {
                throw new BadCredentialsException("缓存 code 查询失败或超时");
            }
            String code = authentication.getCredentials().toString();
            // 验证完删掉 key
            redisTemplate.delete(cacheKey);
            if (!code.toString().equalsIgnoreCase(cacheCode)) {
                throw new BadCredentialsException("缓存 code 验证失败，请通过正确的 PKI 登录方式登录");
            }
        });
        oAuth2TokenEndpointConfigurer.authenticationProvider(demoAuthenticationProvider);
    }

    @Override
    public boolean refreshSupport(OAuth2Authorization oAuth2Authorization) {
        // 之前登录的时候使用的登录类型
        return oAuth2Authorization.getAuthorizationGrantType().equals(GRANT_TYPE);
    }
}
