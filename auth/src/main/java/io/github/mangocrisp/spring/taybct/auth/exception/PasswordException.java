package io.github.mangocrisp.spring.taybct.auth.exception;

import org.springframework.security.authentication.AccountStatusException;

import java.io.Serial;

/**
 * 用户密码异常
 *
 * @author xijieyin <br> 2022/12/30 14:08
 */
public class PasswordException extends AccountStatusException implements JsonResponseException {

    @Serial
    private static final long serialVersionUID = 2190225376230071816L;

    public PasswordException(String msg) {
        super(msg);
    }

    public PasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
