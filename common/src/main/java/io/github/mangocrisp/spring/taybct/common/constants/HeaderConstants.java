package io.github.mangocrisp.spring.taybct.common.constants;

/**
 * 请求头常量
 *
 * @author XiJieYin <br> 2023/5/22 14:17
 */
public interface HeaderConstants {

    /**
     * 自定义认证前缀
     */
    String CUSTOMIZE_PREFIX = "taybct ";

    /**
     * 微信 openid
     */
    String WX_OPENID_KEY = "wx_openid";

    /**
     * 微信登录唯一标识
     */
    String WX_STATE_KEY = "wx_state";

    /**
     * 手机号码
     */
    String PHONE_KEY = "phone";

    /**
     * 验证码
     */
    String VERIFY_KEY = "verify";

}
