package io.github.mangocrisp.spring.taybct.api.system.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 安全输出对象，去掉实体类不允许查看的字段，或者说，直接让这个字段有数据可读，或者加密一些敏感信息等操作。
 *
 * @author xijieyin <br> 2022/8/5 10:18
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user")
@Data
@Schema(description = "系统用户")
public class SysUserSafeOut extends SysUser {

    @Serial
    private static final long serialVersionUID = -2954735882102360086L;

    @Override
    public String getRealName() {
        return StringUtil.encrypt(super.getRealName());
    }

    @Override
    public String getPhone() {
        return StringUtil.encrypt(super.getPhone(), 3, 4);
    }

}
