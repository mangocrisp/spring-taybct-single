package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.Serial;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 默认的 {@link OAuth2AccessTokenAuthenticationToken} 把角色置空了，真难受。。。
 * <br>
 * 这个 token 主要是为了输出给前端看的
 * {@code AuthSecurityConfig#customizeTokenEndpointConfigurer}
 *
 * @author xijieyin <br> 2022/12/29 22:36
 */
@Getter
public class CustomizeTokenAuthenticationToken extends OAuth2AccessTokenAuthenticationToken {

    @Serial
    private static final long serialVersionUID = -4057271352133900733L;
    /**
     * 角色列表，权限
     */
    private final Collection<GrantedAuthority> authorities;
    /**
     * token id
     */
    private final String jti;
    /**
     * 超时时间
     */
    private final long expiresIn;
    /**
     * 访问令牌
     */
    private final String accessTokenValue;
    /**
     * 刷新 token
     */
    private final String refreshTokenValue;
    /**
     * 域
     */
    private final Set<String> scope;
    /**
     * 令牌类型
     */
    private final String tokenType;
    /**
     * 用户名
     */
    private final String username;

    private final UserDetails userDetails;

    public CustomizeTokenAuthenticationToken(RegisteredClient registeredClient
            , Authentication clientPrincipal
            , String jti
            , OAuth2AccessToken accessToken, @Nullable OAuth2RefreshToken refreshToken
            , Map<String, Object> additionalParameters
            , Collection<GrantedAuthority> authorities
            , UserDetails userDetails
            , Object details) {
        super(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
        this.authorities = authorities;
        this.jti = jti;
        this.expiresIn = Optional.ofNullable(accessToken.getExpiresAt())
                .map(i -> i.getEpochSecond() - System.currentTimeMillis() / 1000).orElse(0L);
        this.accessTokenValue = accessToken.getTokenValue();
        this.refreshTokenValue = refreshToken != null ? refreshToken.getTokenValue() : null;
        this.scope = accessToken.getScopes();
        this.tokenType = accessToken.getTokenType().getValue();
        this.username = userDetails.getUsername();
        this.userDetails = userDetails;
        this.setDetails(details);
        this.setAuthenticated(true);
    }

}
