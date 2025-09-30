package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户角色关联<br>
 * sys_user_role
 *
 * @author xijieyin <br> 2022/8/5 10:06
 * @since 1.0.0
 */
@TableName(value = "sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户角色关联")
public class SysUserRole implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 2537996099879802389L;
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;

}
