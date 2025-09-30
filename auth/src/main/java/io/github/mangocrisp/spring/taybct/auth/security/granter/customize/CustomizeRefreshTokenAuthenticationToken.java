package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的刷新 token
 *
 * @author xijieyin <br> 2023/3/2 下午7:38
 */
public class CustomizeRefreshTokenAuthenticationToken extends CustomizeAuthenticationToken {
    @Serial
    private static final long serialVersionUID = -3367688481635584785L;
    @Getter
    private final String refreshToken;
    @Getter
    private final Set<String> scopes;

    public CustomizeRefreshTokenAuthenticationToken(CustomizeAuthenticationToken token) {
        super(token);
        this.refreshToken = token.getPrincipal().toString();
        Set<String> requestedScopes = null;
        if (StrUtil.isNotBlank((String) token.getCredentials())) {
            requestedScopes = new HashSet<>(StrUtil.split((String) token.getCredentials(), " "));
        }
        this.scopes = requestedScopes;
    }
}

