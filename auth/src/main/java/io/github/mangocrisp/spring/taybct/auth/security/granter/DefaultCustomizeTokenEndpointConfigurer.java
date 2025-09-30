package io.github.mangocrisp.spring.taybct.auth.security.granter;

import cn.hutool.core.util.ObjectUtil;
import io.github.mangocrisp.spring.taybct.auth.exception.*;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.*;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.auth.security.service.ICustomizeUserDetailsService;
import io.github.mangocrisp.spring.taybct.auth.security.util.OAuth2Util;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.OAuth2GrantType;
import io.github.mangocrisp.spring.taybct.tool.core.support.IEncryptedPassable;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM3Coder;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Set;

/**
 * 默认的 token 端口自定义配置
 *
 * @author xijieyin <br> 2022/12/29 12:51
 */
@Data
@Builder
public class DefaultCustomizeTokenEndpointConfigurer implements ICustomizeTokenEndpointConfigurer {

    /**
     * 自定义的用户查询服务
     */
    @NonNull
    private ICustomizeUserDetailsService customizeUserDetailsService;

    /**
     * 加密器
     */
    @NonNull
    private PasswordEncoder passwordEncoder;

    /**
     * 成功回调
     */
    private AuthenticationSuccessHandler accessTokenResponseHandler;

    /**
     * 失败回调
     */
    private AuthenticationFailureHandler errorResponseHandler;

    /**
     * token 生成器
     */
    @NonNull
    private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * 你还可以自定义一些对端点的配置
     */
    @NonNull
    private IOtherTokenEndpointConfigurer otherTokenEndpointConfigurer = tokenEndpoint -> {
    };
    /**
     * 这里可以设置使用 redis 来获取验证码来做校验
     */
    @NonNull
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 鉴权管理，可以从这里根据刷新 token 获取新的 token 什么的
     */
    @NonNull
    private OAuth2AuthorizationService authorizationService;

    @NonNull
    private IEncryptedPassable encryptedPassable;

    @Override
    public void customize(OAuth2TokenEndpointConfigurer tokenEndpoint) {
        customizeUserDetailsService.setEncoder(password -> passwordEncoder.encode(password));

        authByUserName(tokenEndpoint);

        authByPhone(tokenEndpoint);

        authByRefreshToken(tokenEndpoint);

        // 继续添加处理器和转换器
        otherTokenEndpointConfigurer.customize(tokenEndpoint);
        // 成功回调
        tokenEndpoint.accessTokenResponseHandler(accessTokenResponseHandler);
        // 失败回调
        tokenEndpoint.errorResponseHandler(errorResponseHandler);
    }

    /**
     * 根据刷新 token 鉴权，OAuth2 自带的刷新 token 模式只能适用于他自己的授权码模式，
     * 如果是使用 {@linkplain OAuth2GrantType#TAYBCT taybct} 模式获取到的 token
     * 只能使用 {@linkplain OAuth2GrantType#TAYBCT_REFRESH taybct_refresh} 来刷新
     *
     * @param tokenEndpoint token 端点
     */
    private void authByRefreshToken(OAuth2TokenEndpointConfigurer tokenEndpoint) {
        // start: 刷新 token 模式

        // 因为和用户名差不多，是使用手机号来查询
        CustomizeAuthenticationConverter refreshConverter = new CustomizeAuthenticationConverter(
                new AuthorizationGrantType(OAuth2GrantType.TAYBCT_REFRESH)
                , CustomizeRefreshTokenAuthenticationToken::new
                , "refresh_token"
                , null
                , map -> map.put("refresh_token", "[refresh_token]不能为空，且只能有一个值"),
                request -> {
                });
        tokenEndpoint.accessTokenRequestConverter(refreshConverter);

        CustomizeAuthenticationProvider refreshAuthenticationProvider = new CustomizeAuthenticationProvider(
                new AuthorizationGrantType(OAuth2GrantType.TAYBCT_REFRESH)
                , customizeAuthenticationToken -> {
            //  先拿到缓存管理的刷新 token
            OAuth2Authorization authorization = authorizationService.findByToken((String) customizeAuthenticationToken.getPrincipal(), OAuth2TokenType.REFRESH_TOKEN);
            if (authorization == null) {
                throw new AccountException("刷新 token 校验失败（未找到，可能已经过期！）");
            }
            OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
            if (refreshToken == null || !refreshToken.isActive()) {
                throw new AccountException("刷新 token 校验失败（已经过期！）");
            }
            String principalName = authorization.getPrincipalName();
            AuthorizationGrantType authorizationGrantType = authorization.getAuthorizationGrantType();
            if (authorizationGrantType.equals(new AuthorizationGrantType(OAuth2GrantType.TAYBCT))) {
                // 验证没问题之后使用 principalName（一般是用户名）去按用户名查询到用户
                UserDetails userDetails = customizeUserDetailsService.loadUserByUsername(principalName);
                ((OAuth2UserDetails) userDetails).setGrantType(OAuth2GrantType.TAYBCT);
                return userDetails;
            }
            if (authorizationGrantType.equals(new AuthorizationGrantType(OAuth2GrantType.SMS))) {
                // 验证没问题之后使用 principalName（一般是手机号）去按手机号查询到用户
                UserDetails userDetails = customizeUserDetailsService.loadUserByPhone(principalName);
                ((OAuth2UserDetails) userDetails).setGrantType(OAuth2GrantType.SMS);
                return userDetails;
            }
            return otherTokenEndpointConfigurer.way2FindUserInRefreshModel().apply(authorization);
        }
                , passwordEncoder
                , tokenGenerator
                , () -> CustomizeRefreshTokenAuthenticationToken.class
                , authorizationService);
        refreshAuthenticationProvider.setAdditionalAuthenticationChecks((UserDetails userDetails, Authentication authentication) -> {
            // 对刷新 token 做一些校验
            CustomizeRefreshTokenAuthenticationToken refreshTokenAuthentication = (CustomizeRefreshTokenAuthenticationToken) authentication;
            OAuth2ClientAuthenticationToken clientPrincipal = OAuth2Util.getAuthenticatedClientElseThrowInvalidClient(refreshTokenAuthentication);
            RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
            OAuth2Authorization authorization = authorizationService.findByToken(authentication.getPrincipal().toString(), OAuth2TokenType.REFRESH_TOKEN);
            if (registeredClient != null && authorization != null) {
                if (!registeredClient.getId().equals(authorization.getRegisteredClientId())) {
                    throw new AccountException("刷新 token 客户端校验失败");
                }
                Set<String> scopes = refreshTokenAuthentication.getScopes();
                Set<String> authorizedScopes = authorization.getAuthorizedScopes();
                if (scopes != null && !authorizedScopes.containsAll(scopes)) {
                    throw new AccountException("刷新 token 域校验失败！");
                }
            }
        });
        refreshAuthenticationProvider.setPreSaveOAuth2Authorization(((authentication, builder) -> {
            // 对刷新 token 做一些校验
            CustomizeRefreshTokenAuthenticationToken refreshTokenAuthentication = (CustomizeRefreshTokenAuthenticationToken) authentication;
            OAuth2ClientAuthenticationToken clientPrincipal = OAuth2Util.getAuthenticatedClientElseThrowInvalidClient(refreshTokenAuthentication);
            RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
            OAuth2Authorization authorization = authorizationService.findByToken(authentication.getPrincipal().toString(), OAuth2TokenType.REFRESH_TOKEN);
            if (registeredClient != null && authorization != null) {
                builder.authorizationGrantType(authorization.getAuthorizationGrantType());
                authorizationService.remove(authorization);
            }
        }));
        tokenEndpoint.authenticationProvider(refreshAuthenticationProvider);
        // end: 刷新 token 模式
    }

    /**
     * 根据手机号鉴权
     *
     * @param tokenEndpoint token 端点
     */
    private void authByPhone(OAuth2TokenEndpointConfigurer tokenEndpoint) {
        // start: 短信模式

        // 因为和用户名差不多，是使用手机号来查询
        CustomizeAuthenticationConverter smsConverter = new CustomizeAuthenticationConverter(
                new AuthorizationGrantType(OAuth2GrantType.SMS)
                , PhoneAuthenticationToken::new
                , "phone"
                , "verify"
                , map -> {
            map.put("phone", "[phone]不能为空，且只能有一个值");
            map.put("verify", "[verify]不能为空，且只能有一个值");
        },
                request -> {
                });
        tokenEndpoint.accessTokenRequestConverter(smsConverter);

        CustomizeAuthenticationProvider phoneAuthenticationProvider = new CustomizeAuthenticationProvider(
                new AuthorizationGrantType(OAuth2GrantType.SMS)
                , customizeAuthenticationToken -> customizeUserDetailsService.loadUserByPhone((String) customizeAuthenticationToken.getPrincipal())
                , passwordEncoder
                , tokenGenerator
                , () -> PhoneAuthenticationToken.class
                , authorizationService);
        phoneAuthenticationProvider.setAdditionalAuthenticationChecks((UserDetails userDetails, Authentication authentication) -> {
            String phoneNumber = userDetails.getUsername();
            String verify = authentication.getCredentials().toString();
            // 拿到 redis 上缓存的 验证码
            Object cacheVerify = redisTemplate.opsForValue().get(CacheConstants.SMS.VERIFY + phoneNumber);
            if (ObjectUtil.isEmpty(cacheVerify)) {
                throw new VerifyCodeExpiredException("验证码过期了！");
            }
            if (!verify.equals(cacheVerify)) {
                throw new VerifyCodeMismatchException("验证码不正确！");
            }
            redisTemplate.delete(CacheConstants.SMS.VERIFY + phoneNumber);
        });
        tokenEndpoint.authenticationProvider(phoneAuthenticationProvider);

        // end: 短信模式
    }

    /**
     * 根据用户名鉴权
     *
     * @param tokenEndpoint token 端点
     */
    private void authByUserName(OAuth2TokenEndpointConfigurer tokenEndpoint) {
        // start: 用户名，密码模式

        // 设置默认的请求转换器
        CustomizeAuthenticationConverter usernameConverter = new CustomizeAuthenticationConverter(
                new AuthorizationGrantType(OAuth2GrantType.TAYBCT)
                , UsernameAuthenticationToken::new
                , "username"
                , "password"
                , map -> {
            map.put("username", "[username]不能为空，且只能有一个值");
            map.put("password", "[password]不能为空，且只能有一个值");
        }
                , request -> {
        });
        tokenEndpoint.accessTokenRequestConverter(usernameConverter);
        // 设置默认的鉴权处理器，用户名，密码
        CustomizeAuthenticationProvider usernameAuthenticationProvider = new CustomizeAuthenticationProvider(
                new AuthorizationGrantType(OAuth2GrantType.TAYBCT)
                , customizeAuthenticationToken -> customizeUserDetailsService.loadUserByUsername((String) customizeAuthenticationToken.getPrincipal())
                , passwordEncoder
                , tokenGenerator
                , () -> UsernameAuthenticationToken.class
                , authorizationService);
        usernameAuthenticationProvider.setAdditionalAuthenticationChecks((UserDetails userDetails, Authentication authentication) -> {
            // 前端传进来的是 RSA | SM2 或者其他非对称 加密的
            String presentedPassword = authentication.getCredentials().toString();
            // 这里使用 RSA | SM2 或者其他非对称 证书加密的密码，前端也用 非对称 加密进来，在这里用同样的证书解密，然后做比对
            try {
                // 私钥 解密之后的密码
                String presentedPasswordD = SM3Coder.getSM3().digestHex(encryptedPassable.apply(presentedPassword));
                if (!this.passwordEncoder.matches(presentedPasswordD, userDetails.getPassword())) {
                    throw new PasswordMismatchException("用户名密码不匹配");
                }
            } catch (PasswordException e) {
                throw e;
            } catch (Exception e) {
                throw new PasswordException("解密失败，密码必须是由服务器提供的公钥加密", e);
            }
        });
        tokenEndpoint.authenticationProvider(usernameAuthenticationProvider);

        // end: 用户名，密码模式
    }

}
