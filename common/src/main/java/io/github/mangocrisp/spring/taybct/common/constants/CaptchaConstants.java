package io.github.mangocrisp.spring.taybct.common.constants;

/**
 * 验证码常量
 *
 * @author XiJieYin <br> 2023/5/23 16:20
 */
public interface CaptchaConstants {

    /**
     * 验证码键
     */
    String CAPTCHA_CODE_KEY = "captcha_code";

    /**
     * 存储在缓存里面的验证码的键 uuid
     */
    String CAPTCHA_UUID_KEY = "captcha_uuid";

    /**
     * 验证码图片
     */
    String CAPTCHA_IMG_KEY = "captcha_img";

}
