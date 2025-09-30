package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import io.github.mangocrisp.spring.taybct.auth.security.config.AuthorizationServerConfig;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

/**
 * 已鉴权通过的 token？？？载体
 * <br>
 * 这个主要是为了来生成 token 用的
 *
 * @author xijieyin <br> 2022/12/29 16:21
 * @see AuthorizationServerConfig#jwtCustomizer
 */
public class CustomizeAuthenticatedToken extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = -622944963136741931L;
    /**
     * 用户名
     */
    @Getter
    private final String username;
    /**
     * 客户端 id
     */
    @Getter
    private final String clientId;
    /**
     * 用户详细
     */
    @Getter
    private final UserDetails userDetails;

    public CustomizeAuthenticatedToken(Collection<? extends GrantedAuthority> authorities
            , String clientId
            , UserDetails userDetails) {
        super(authorities);
        this.clientId = clientId;
        this.userDetails = userDetails;
        this.username = userDetails.getUsername();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

}
