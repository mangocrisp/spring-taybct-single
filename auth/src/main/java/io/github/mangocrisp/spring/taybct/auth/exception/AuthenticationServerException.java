package io.github.mangocrisp.spring.taybct.auth.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**
 * 鉴权服务器异常，这个一般是发生在鉴权的时候，其他时候都应该有对应的异常处理器去处理异常
 *
 * @author xijieyin <br> 2022/12/30 15:24
 */
public class AuthenticationServerException extends AuthenticationException implements JsonResponseException {
    @Serial
    private static final long serialVersionUID = 4015485126564312480L;

    public AuthenticationServerException(String msg) {
        super(msg);
    }

    public AuthenticationServerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
