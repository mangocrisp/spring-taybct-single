package io.github.mangocrisp.spring.taybct.single.security;

import cn.hutool.core.lang.UUID;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserRoleMapper;
import io.github.mangocrisp.spring.taybct.auth.security.granter.DefaultCustomizeTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.ICustomizeTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.IOtherTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.OtherTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticatedToken;
import io.github.mangocrisp.spring.taybct.auth.security.granter.pki.PKITokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.auth.security.service.ICustomizeUserDetailsService;
import io.github.mangocrisp.spring.taybct.auth.security.util.ResponseHandler;
import io.github.mangocrisp.spring.taybct.common.constants.JwtTokenKeyConstants;
import io.github.mangocrisp.spring.taybct.common.constants.LoginConstants;
import io.github.mangocrisp.spring.taybct.common.constants.OAuth2GrantType;
import io.github.mangocrisp.spring.taybct.common.enums.OAuthenticationMethodType;
import io.github.mangocrisp.spring.taybct.single.security.granter.demo.DemoTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.support.IEncryptedPassable;
import io.github.mangocrisp.spring.taybct.tool.pki.prop.PKIProp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 鉴权服务器配置
 * <br>
 * 配置如何生成 token，定义 token 载体规则和内容，
 * <br>
 * 配置
 *
 * @author xijieyin
 */
@AutoConfiguration
public class AuthServerConfigurer {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;
    @Value("${spring.application.name:api}")
    private String applicationName;

    @Bean
    public DemoTokenEndpointConfigurer demoTokenEndpointConfigurer(PasswordEncoder passwordEncoder
            , OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator
            , OAuth2AuthorizationService authorizationService
            , SysUserMapper sysUserMapper
            , SysUserRoleMapper sysUserRoleMapper
            , ISysParamsObtainService sysParamsObtainService
            , RedisTemplate<String, String> redisTemplate) {
        return new DemoTokenEndpointConfigurer(passwordEncoder
                , tokenGenerator
                , authorizationService
                , sysUserMapper
                , sysUserRoleMapper
                , sysParamsObtainService
                , redisTemplate);
    }

    @Bean
    public PKITokenEndpointConfigurer pkiTokenEndpointConfigurer(PasswordEncoder passwordEncoder
            , OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator
            , OAuth2AuthorizationService authorizationService
            , PKIProp pkiProp
            , StringRedisTemplate redisTemplate) {
        return new PKITokenEndpointConfigurer(passwordEncoder
                , tokenGenerator
                , authorizationService
                , pkiProp
                , redisTemplate
                , idCard -> {
            // 这里需要提供一个根据身份证查询用户的返回结果
            throw new BaseException("未找到用户");
        });
    }

    /**
     * 配置自定义的其他的鉴权方式
     *
     * @return 其他鉴权
     */
    @Bean("otherTokenEndpointConfigurer")
    public IOtherTokenEndpointConfigurer otherTokenEndpointConfigurer(DemoTokenEndpointConfigurer demoTokenEndpointConfigurer
            , PKITokenEndpointConfigurer pkiTokenEndpointConfigurer) {

        return new OtherTokenEndpointConfigurer(Arrays.asList(demoTokenEndpointConfigurer
                , pkiTokenEndpointConfigurer));
    }

    /**
     * 自定义添加修改 token 的内容，不过 headers 好像只能加，不能删？？？还是我打开的方式不对,总之,配置大概就是这样,你可以根据自己的 grant_type 去扩展你想要的 token 内容
     *
     * @return 自定义
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(IUserDetailsHandle userDetailsHandle) {
        return context -> {
            // 头信息
            JwsHeader.Builder headers = context.getJwsHeader();
            // 载体信息
            JwtClaimsSet.Builder claims = context.getClaims();
            // 只有 access token 才添加
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                AuthorizationGrantType authorizationGrantType = context.getAuthorizationGrantType();
                String grant = authorizationGrantType.getValue();
                String clientId = context.getRegisteredClient().getClientId();
                Set<String> authorizedScopes = context.getAuthorizedScopes();
                UserDetails userDetails;
                // 授权码模式的用户
                User codeUser;
                if (context.getPrincipal() instanceof CustomizeAuthenticatedToken customizeAuthenticationToken) {
                    userDetails = customizeAuthenticationToken.getUserDetails();
                    codeUser = null;
                } else if (context.getPrincipal() instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
                    codeUser = (User) usernamePasswordAuthenticationToken.getPrincipal();
                    userDetails = null;
                } else {
                    userDetails = null;
                    codeUser = null;
                }
                if (userDetails != null) {
                    if (userDetails instanceof OAuth2UserDetails oAuth2UserDetails) {
                        if (oAuth2UserDetails.getGrantType() != null) {
                            grant = oAuth2UserDetails.getGrantType();
                        }
                    }
                }
                headers.type("JWT");
                // kid 替代不了 jti，这里主要是为了作一个唯一，知道每次登录都是新的登录
                headers.header(TokenConstants.JWT_JTI, UUID.randomUUID().toString(true));
                String finalGrant = grant;
                claims.claims(map -> {
                    // String sub = map.get("sub").toString();
                    map.remove("sub");
                    map.remove("aud");
                    // map.remove("nbf");
                    map.remove("iss");
                    // map.remove("iat");
                    map.put("scope", authorizedScopes);
                    map.put("client_id", clientId);
                    map.put("grant_type", finalGrant);
                    // 授权码模式，刷新 token 模式
                    if (finalGrant.equals(OAuth2GrantType.AUTHORIZATION_CODE)
                            || finalGrant.equals(AuthorizationGrantType.REFRESH_TOKEN.getValue())) {
                        if (codeUser != null) {
                            if (userDetailsHandle != null) {
                                // 这里直接再根据用户名获取一遍用户信息，所以这里建议 userDetailsHandle.getUserByUsername 使用缓存
                                Optional.ofNullable(userDetailsHandle.getUserByUsername(codeUser.getUsername()))
                                        .ifPresent(dto -> map.put(JwtTokenKeyConstants.USER_ID, dto.getUserId().toString()));
                            }
                            // 当然也是通过用户名认证的
                            map.put(JwtTokenKeyConstants.AUTH_M, OAuthenticationMethodType.USERNAME.value());
                            map.put(JwtTokenKeyConstants.USERNAME, codeUser.getUsername());
                            map.put(JwtTokenKeyConstants.AUTHORITIES, codeUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                        }
                    }
                    // 用户名密码登录模式
                    if (finalGrant.equals(OAuth2GrantType.TAYBCT)
                            || finalGrant.equals(OAuth2GrantType.SMS)
                            || finalGrant.equals(DemoTokenEndpointConfigurer.GRANT_TYPE.getValue())) {
                        if (userDetails != null) {
                            if (userDetails instanceof OAuth2UserDetails oAuth2UserDetails) {
                                map.put(JwtTokenKeyConstants.USER_ID, oAuth2UserDetails.getUserId().toString());
                                map.put(JwtTokenKeyConstants.AUTH_M, oAuth2UserDetails.getAuthenticationMethod());
                            }
                            map.put(JwtTokenKeyConstants.USERNAME, userDetails.getUsername());
                            map.put(JwtTokenKeyConstants.AUTHORITIES, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                        }
                    }

                    // 如果是自定义的 demo grant type
                    if (finalGrant.equals(DemoTokenEndpointConfigurer.GRANT_TYPE.getValue())) {
                        if (userDetails != null) {
                            if (userDetails instanceof OAuth2UserDetails oAuth2UserDetails) {
                                map.put(TokenConstants.PRINCIPAL, oAuth2UserDetails.getPrincipal());
                            }
                        }
                    }
                    // 如果是其他方式？
                    //
                });
            }
//            else if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
//                // Customize headers/claims for id_token
//
//            }
        };
    }

    /**
     * 自定义 token 端点配置，这里注意一下：
     * <br>
     * 没有具体摸清楚 OAuth2 是怎么拦截异常的，反正 {@link org.springframework.web.bind.annotation.RestControllerAdvice } 在这里不好使
     * 也就是说，只要你不是抛出的 {@link org.springframework.security.oauth2.core.OAuth2AuthenticationException}
     * ，就不会被 {@link org.springframework.security.web.authentication.AuthenticationFailureHandler} 拦截
     * ，也不会被 {@link org.springframework.web.bind.annotation.RestControllerAdvice } 拦截，那，意思就是说
     * ，根本就不会返回给客户端任何提示了。。。属实是有点难搞，所以我这里还是老老实实按他的要求，配置好了异常返回。
     *
     * @param passwordEncoder             加密器
     * @param customizeUserDetailsService 自定义用户查询 Service
     * @return ICustomizeTokenEndpointConfigurer
     */
    @Bean
    public ICustomizeTokenEndpointConfigurer customizeTokenEndpointConfigurer(PasswordEncoder passwordEncoder
            , ICustomizeUserDetailsService customizeUserDetailsService
            , OAuth2TokenGenerator<?> tokenGenerator
            , IUserDetailsHandle userDetailsHandle
            , RedisTemplate<Object, Object> redisTemplate
            , @Qualifier("otherTokenEndpointConfigurer") IOtherTokenEndpointConfigurer otherTokenEndpointConfigurer
            , OAuth2AuthorizationService authorizationService
            , IEncryptedPassable encryptedPassable
            , ResponseHandler responseHandler) {

        responseHandler.setSuccessResultConverter(this::printResponse);

        responseHandler.setOperationsBeforeOutput(authentication -> resultHandle(userDetailsHandle, authentication));

        return DefaultCustomizeTokenEndpointConfigurer.builder()
                .passwordEncoder(passwordEncoder)
                .customizeUserDetailsService(customizeUserDetailsService)
                .tokenGenerator(tokenGenerator)
                .errorResponseHandler(responseHandler)
                .redisTemplate(redisTemplate)
                .authorizationService(authorizationService)
                .otherTokenEndpointConfigurer(otherTokenEndpointConfigurer)
                .encryptedPassable(encryptedPassable)
                .accessTokenResponseHandler(responseHandler)
                .build();
    }

    /**
     * 打印结果给前端
     *
     * @param authentication 鉴权信息
     * @param response       response
     * @return 返回结果对象
     */
    private @NotNull R<?> printResponse(Authentication authentication, HttpServletResponse response) {
        if (authentication instanceof OAuth2AccessTokenAuthenticationToken token) {
            JSONObject data = new JSONObject();

            OAuth2AccessToken accessToken = token.getAccessToken();
            JWT jwt = JWTUtil.parseToken(accessToken.getTokenValue());
            String grantType = (String) jwt.getPayload(JwtTokenKeyConstants.GRANT_TYPE);
            String jti = (String) jwt.getHeader(TokenConstants.JWT_JTI);

            data.put("access_token", accessToken.getTokenValue());
            data.put("access_token_exp", Optional.ofNullable(accessToken.getExpiresAt())
                    .map(i -> i.getEpochSecond() - System.currentTimeMillis() / 1000).orElse(0L));
            data.put("token_type", accessToken.getTokenType().getValue());
            data.put("scope", accessToken.getScopes());
            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken != null) {
                data.put(AuthHeaderConstants.REFRESH_TOKEN_KEY, refreshToken.getTokenValue());
                data.put("refresh_token_exp", Optional.ofNullable(refreshToken.getExpiresAt())
                        .map(i -> i.getEpochSecond() - System.currentTimeMillis() / 1000).orElse(0L));
            }
            data.put(TokenConstants.JWT_JTI, jti);
            data.put(TokenConstants.AUTHENTICATION_METHOD, Optional.ofNullable(jwt.getPayload(JwtTokenKeyConstants.AUTH_M)).orElse(grantType));
            // 帮忙给前端设置好 jti
            Cookie jtiCookie = new Cookie(applicationName.toUpperCase() + "-" + TokenConstants.JWT_JTI.toUpperCase(), jti);
            jtiCookie.setPath(contextPath);
            jtiCookie.setMaxAge(data.getInteger("access_token_exp"));
            response.addCookie(jtiCookie);
            return R.data(data);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return R.fail();
    }

    /**
     * 鉴权结果处理
     *
     * @param userDetailsHandle 用户结果处理器
     * @param authentication    鉴权信息
     */
    private void resultHandle(IUserDetailsHandle userDetailsHandle, Authentication authentication) {
        if (authentication instanceof OAuth2AccessTokenAuthenticationToken token) {
            String clientId = token.getRegisteredClient().getClientId();
            OAuth2AccessToken accessToken = token.getAccessToken();
            JWT jwt = JWTUtil.parseToken(accessToken.getTokenValue());
            String grantType = (String) jwt.getPayload(JwtTokenKeyConstants.GRANT_TYPE);
            if (grantType.equals(OAuth2GrantType.TAYBCT)
                    || grantType.equals(OAuth2GrantType.SMS)
                    || grantType.equals(DemoTokenEndpointConfigurer.GRANT_TYPE.getValue())
                    || grantType.equals(OAuth2GrantType.AUTHORIZATION_CODE)
                    || grantType.equals(AuthorizationGrantType.REFRESH_TOKEN.getValue())) {
                // 只有授权模式是 taybct 才会调用登录接口去登录
                JSONObject data = new JSONObject();
                String jti = (String) jwt.getHeader(TokenConstants.JWT_JTI);
                data.put(TokenConstants.JWT_JTI, jti);
                data.put("ip", ((WebAuthenticationDetails) token.getDetails()).getRemoteAddress());
                data.put(AuthHeaderConstants.CLIENT_ID_KEY, clientId);
                long exp = Optional.ofNullable(accessToken.getExpiresAt())
                        .map(Instant::getEpochSecond).orElseThrow(() -> new BaseException("超时时间为空！"));
                data.put(TokenConstants.JWT_EXP, exp);
                data.put(TokenConstants.AUTHENTICATION_METHOD, Optional.ofNullable(jwt.getPayload(JwtTokenKeyConstants.AUTH_M)).orElse(grantType));
                data.put(TokenConstants.USER_NAME_KEY, jwt.getPayload(JwtTokenKeyConstants.USERNAME));
                data.put(TokenConstants.USER_ID_KEY, jwt.getPayload(JwtTokenKeyConstants.USER_ID));
                data.put(LoginConstants.ACCESS_TOKEN, accessToken.getTokenValue());
                userDetailsHandle.login(data);
            }
        }
    }

}
