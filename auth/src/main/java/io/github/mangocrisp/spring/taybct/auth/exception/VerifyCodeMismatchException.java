package io.github.mangocrisp.spring.taybct.auth.exception;

import java.io.Serial;

/**
 * 验证码不匹配异常
 *
 * @author xijieyin <br> 2022/12/30 14:02
 */
public class VerifyCodeMismatchException extends PasswordException implements JsonResponseException {

    @Serial
    private static final long serialVersionUID = 904962912667063451L;

    public VerifyCodeMismatchException(String msg) {
        super(msg);
    }

    public VerifyCodeMismatchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
