package io.github.mangocrisp.spring.taybct.single.security.granter.demo;

import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticationToken;

import java.io.Serial;

/**
 * 自定义的 demo 鉴权 token
 *
 * @author xijieyin
 */
public class DemoAuthenticationToken extends CustomizeAuthenticationToken {

    @Serial
    private static final long serialVersionUID = -8843701231315487939L;

    public DemoAuthenticationToken(CustomizeAuthenticationToken token) {
        super(token);
    }
}
