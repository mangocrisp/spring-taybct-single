package io.github.mangocrisp.spring.taybct.common.enums;

import java.io.Serializable;

/**
 * 用户状态
 *
 * @author xijieyin <br> 2022/8/5 18:30
 * @since 1.0.0
 */
public record OAuthUserStatus(Byte status, String description) implements Serializable {

    /**
     * 可用
     */
    public static final OAuthUserStatus ENABLE = new OAuthUserStatus((byte) 1, "可用");
    /**
     * 不可用
     */
    public static final OAuthUserStatus DISABLE = new OAuthUserStatus((byte) 0, "不可用");
    /**
     * 冻结
     */
    public static final OAuthUserStatus FREEZE = new OAuthUserStatus((byte) -1, "冻结");

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        OAuthUserStatus that = (OAuthUserStatus) obj;
        return this.status().equals(that.status());
    }

    @Override
    public int hashCode() {
        return this.status().hashCode();
    }

}
