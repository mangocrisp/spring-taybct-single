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
 * 角色权限关联表<br>
 * sys_role_permission
 *
 * @author xijieyin <br> 2022/8/5 10:05
 * @since 1.0.0
 */
@TableName(value = "sys_role_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "角色权限关联")
public class SysRolePermission implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 5582482627114720688L;
    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 权限id
     */
    @Schema(description = "权限id")
    private Long permissionId;

}
