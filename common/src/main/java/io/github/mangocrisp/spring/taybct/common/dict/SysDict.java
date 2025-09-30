package io.github.mangocrisp.spring.taybct.common.dict;

import lombok.Getter;

import java.io.Serial;

/**
 * 系统字典
 *
 * @author xijieyin <br> 2022/8/5 18:37
 * @since 1.0.0
 */
public class SysDict {

    /**
     * 性别
     */
    @Getter
    public static final class Gender extends AbstractSysDict {
        @Serial
        private static final long serialVersionUID = 7090523811026556297L;
        /**
         * 字典的键(数字)
         */
        private final Integer intValue;
        /**
         * 男
         */
        public static final Gender MALE = new Gender("1", 1, "男");
        /**
         * 女
         */
        public static final Gender FEMALE = new Gender("2", 2, "女");

        public Gender(String key, Integer intValue, String value) {
            super(key, value);
            this.intValue = intValue;
        }

    }

    /**
     * 用户类型
     */
    @Getter
    public static final class UserType extends AbstractSysDict {

        @Serial
        private static final long serialVersionUID = -5079638641492474344L;

        /**
         * 系统用户
         */
        public static final UserType SYSTEM = new UserType("00", "系统用户");
        /**
         * 临时用户
         */
        public static final UserType TEMP = new UserType("01", "临时用户");


        public UserType(String key, String val) {
            super(key, val);
        }
    }

    /**
     * 用户状态
     */
    @Getter
    public static final class UserStatus extends AbstractSysDict {

        @Serial
        private static final long serialVersionUID = -4446454737292593085L;

        /**
         * 可用
         */
        public static final UserStatus ENABLE = new UserStatus("1", "可用");

        /**
         * 不可用
         */
        public static final UserStatus DISABLE = new UserStatus("0", "不可用");

        /**
         * 冻结
         */
        public static final UserStatus FREEZE = new UserStatus("-1", "冻结");

        public UserStatus(String key, String val) {
            super(key, val);
        }
    }

    /**
     * 通知类型
     *
     * @author xijieyin <br> 2022/10/11 10:23
     * @since 1.0.5
     */
    @Getter
    public static final class NoticeType extends AbstractSysDict {
        @Serial
        private static final long serialVersionUID = -5875738105270863106L;
        /**
         * 通知类型是用户
         */
        public static final NoticeType USER = new NoticeType("1", "通知类型是用户");

        public NoticeType(String key, String val) {
            super(key, val);
        }
    }

}
