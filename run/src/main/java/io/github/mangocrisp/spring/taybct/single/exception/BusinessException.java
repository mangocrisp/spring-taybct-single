package io.github.mangocrisp.spring.taybct.single.exception;

import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.IResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import org.springframework.http.HttpStatus;

/**
 * 业务异常
 *
 * @author XiJieYin <br> 2023/1/30 9:40
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = -9167570506403463213L;

    private String code = ResultCode.BASE_ERROR.getCode();
    /**
     * http 状态码
     */
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public BusinessException(IResultCode resultCode) {
        super(resultCode);
        this.code = resultCode.getCode();
    }

    public BusinessException(IResultCode resultCode, String message) {
        super(resultCode, message);
        this.code = resultCode.getCode();
    }

    /**
     * 这里可以使用格式化输出可以看一下 <a href="https://geek-docs.com/java/java-string/java-string-format.html">java String format</a>
     *
     * @param formatMessage
     * @param args
     */
    public BusinessException(String formatMessage, Object... args) {
        super(String.format(formatMessage, args));
    }

    public BusinessException(Throwable e, String formatMessage, Object... args) {
        super(String.format(formatMessage, args), e);
    }

    /**
     * 这里可以自定义 code 应该是什么 code
     *
     * @param code code
     * @return 异常类
     */
    public BusinessException setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * 可以设置状态码
     *
     * @param httpStatus 状态码
     * @return 异常类
     * @see HttpStatus
     */
    public BusinessException setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
