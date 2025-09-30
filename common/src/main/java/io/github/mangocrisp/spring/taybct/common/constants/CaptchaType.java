package io.github.mangocrisp.spring.taybct.common.constants;

/**
 * 验证码类型
 *
 * @author xijieyin <br> 2022/12/7 15:24
 * @since 2.0.2
 */
public interface CaptchaType {
    /**
     * 圆圈干扰验证码
     */
    String CIRCLE = "CIRCLE";
    /**
     * gif 动图验证码类
     */
    String GIF = "GIF";
    /**
     * 使用干扰线方式生成的图形验证码
     */
    String LINE = "LINE";
    /**
     * 扭曲干扰验证码
     */
    String SHEAR = "SHEAR";
}