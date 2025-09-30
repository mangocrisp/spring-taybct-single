package io.github.mangocrisp.spring.taybct.auth.exception;

import org.springframework.security.authentication.AccountStatusException;

import java.io.Serial;

/**
 * 验证码过期异常
 *
 * @author xijieyin <br> 2022/12/30 14:16
 */
public class VerifyCodeExpiredException extends AccountStatusException implements JsonResponseException {
    @Serial
    private static final long serialVersionUID = 3737556715682071217L;

    public VerifyCodeExpiredException(String msg) {
        super(msg);
    }

    public VerifyCodeExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
