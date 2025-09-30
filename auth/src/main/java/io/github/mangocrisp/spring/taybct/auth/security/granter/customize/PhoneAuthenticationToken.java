package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import java.io.Serial;

/**
 * 自定义的手机短信鉴权 token
 *
 * @author xijieyin <br> 2022/12/30 11:30
 */
public class PhoneAuthenticationToken extends CustomizeAuthenticationToken {

    @Serial
    private static final long serialVersionUID = -9063700405726113759L;

    public PhoneAuthenticationToken(CustomizeAuthenticationToken token) {
        super(token);
    }
}
