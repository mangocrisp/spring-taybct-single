package io.github.mangocrisp.spring.taybct.api.system.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户安全输入对象
 *
 * @author xijieyin <br> 2022/8/5 10:08
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "api_log")
@Data
@Schema(description = "用户安全输入对象")
public class SysUserSafeIn extends SysUser {

    @Serial
    private static final long serialVersionUID = 5691577743118994807L;

    @Override
    public void setUsername(String username) {
        // 用户名是不能直接改的
        super.setUsername(null);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void setPassword(String password) {
        // 不允许直接修改密码
        super.setPassword(null);
    }

    @Override
    public String getPassword() {
        return null;
    }
}
