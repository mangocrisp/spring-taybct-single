package io.github.mangocrisp.spring.taybct.auth.security.granter.pki;

import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticationToken;

import java.io.Serial;

/**
 * PKI的授权码鉴权 token
 *
 * @author xijieyin
 */
public class PKIAuthenticationToken extends CustomizeAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 2352784561229538426L;

    public PKIAuthenticationToken(CustomizeAuthenticationToken token) {
        super(token);
    }
}
