package io.github.mangocrisp.spring.taybct.common.constants;

/**
 * 缓存常量
 *
 * @author xijieyin <br> 2022/8/5 18:16
 * @since 1.0.0
 */
public class CacheConstants {

    /**
     * 系统前缀
     */
    public static final String SYSTEM_PREFIX = "tb:";

    /**
     * 鉴权相关的缓存
     */
    public interface OAuth {
        String PREFIX = SYSTEM_PREFIX + "oauth:";
        /**
         * 客户端
         */
        String CLIENT = PREFIX + "client";
        /**
         * 用户
         */
        String USER = PREFIX + "user";
        /**
         * 使用用户名登录的
         */
        String USERNAME = USER + ":username";
        /**
         * 使用 open id 登录的
         */
        String OPENID = USER + ":openid";
        /**
         * 使用 手机号码 登录的
         */
        String PHONE = USER + ":phone";
        /**
         * 使用 手机号码 登录的
         */
        String USERID = USER + ":userid";
        /**
         * 登出黑名单
         */
        String BLACKLIST = PREFIX + "blacklist:";
        /**
         * 在线用户
         */
        String ONLINE = PREFIX + "online";
        /**
         * 在线用户索引，用于做分页
         */
        String ONLINE_INDEX = ONLINE + "-index";
        /**
         * 租户关联
         */
        String TENANT_RELATION = PREFIX + "tenant-relation:";

        /**
         * 计数缓存 key
         */
        String COUNT_CACHE_KEY = PREFIX + "pwd_ck_fail_c:";
    }

    /**
     * 系统模块相关的缓存
     */
    public interface System {
        String PREFIX = SYSTEM_PREFIX + "sys:";
        /**
         * 用户
         */
        String USER = PREFIX + "user";
        /**
         * 系统字典
         */
        String DICT = PREFIX + "dict";
        /**
         * 系统参数
         */
        String PARAMS = PREFIX + "params";
    }

    /**
     * 存储权限
     */
    public interface Perm {
        String PREFIX = SYSTEM_PREFIX + "perm:";
        /**
         * 请求
         */
        String URL = PREFIX + "url";
        /**
         * 按钮
         */
        String BTN = PREFIX + "btn";
    }

    /**
     * 存储验证码
     */
    public interface Captcha {

        String PREFIX = SYSTEM_PREFIX + "captcha:";
        /**
         * 验证码的 key
         */
        String KEY = PREFIX + "key:";
    }

    /**
     * 存储短信验证码
     */
    public interface SMS {
        /**
         * 前缀
         */
        String PREFIX = SYSTEM_PREFIX + "sms:";
        /**
         * 短信验证码 key
         */
        String VERIFY = PREFIX + "verify:";
    }

    /**
     * 微信相关
     */
    public interface WX {

        String PREFIX = SYSTEM_PREFIX + "wx:";
        /**
         * 向微信发送 Authorization 请求时会传一个 state ，这个 state 先存储在 redis 里面，
         * 微信授权通过会原样把这个 state 返回，这里，可以做一个验证，确定是微信给我们发的 state
         */
        String STATE = PREFIX + "state:";
        /**
         * 存储微信登录成功后的 token 信息
         */
        String TOKEN_INFO = PREFIX + "token_info:";
        /**
         * 存储微信登录成功后的用户信息
         */
        String USER_INFO = PREFIX + "user_info:";

    }

    /**
     * 默认数据缓存
     */
    public interface Params {

        String PREFIX = System.PARAMS;
        /**
         * 菜单默认的 Layout 的 id
         */
        String MENU_LAYOUT = "menu_layout";
        /**
         * 默认的 ROOT 角色 id 是 1
         */
        String ROLE_ROOT_ID = "role_root_id";
        /**
         * 默认 ROOT 用户的 id 是 1
         */
        String USER_ROOT_ID = "user_root_id";
        /**
         * 默认密码
         */
        String USER_PASSWD = "user_passwd";
        /**
         * 默认角色，游客
         */
        String USER_ROLE = "user_role";
        /**
         * 默认的游客角色 id
         */
        String USER_ROLE_ID = "user_role_id";
        /**
         * 用户默认状态
         */
        String USER_STATUS = "user_status";
        /**
         * 默认租户 id
         */
        String TENANT_ID = "tenant_id";
        /**
         * 是否允许同一个客户端有多个 token 即重复登录同一个客户端
         */
        String ALLOW_MULTIPLE_TOKEN_ONE_CLIENT = "allow_multiple_token_one_client";
        /**
         * 是否需要验证码登录
         */
        String ENABLE_CAPTCHA = "enable_captcha";
        /**
         * 验证码的类型
         *
         * @see CaptchaType
         */
        String CAPTCHA_TYPE = "captcha_type";

        /**
         * 要求修改密码的时间间隔(单位：月)
         */
        String PASSWD_REQUIRE = "passwd_require";

    }

}
