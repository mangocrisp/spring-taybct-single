package io.github.mangocrisp.spring.taybct.auth.exception;

import org.springframework.security.authentication.AccountStatusException;

import java.io.Serial;

/**
 * 用户异常
 *
 * @author xijieyin <br> 2022/12/30 14:08
 */
public class AccountException extends AccountStatusException implements JsonResponseException {

    @Serial
    private static final long serialVersionUID = 1717972004481857851L;

    public AccountException(String msg) {
        super(msg);
    }

    public AccountException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
