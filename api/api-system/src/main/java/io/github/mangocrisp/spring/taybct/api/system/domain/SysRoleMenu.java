package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色菜单关联<br>
 * sys_role_menu
 *
 * @author xijieyin <br> 2022/8/5 10:05
 * @since 1.0.0
 */
@TableName(value = "sys_role_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "角色菜单关联")
public class SysRoleMenu implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 6212459343133549364L;
    /**
     * 角色id
     */
    @TableId(value = "role_id")
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 菜单id
     */
    @Schema(description = "菜单id")
    private Long menuId;

    /**
     * 是否选中，因为有上下级关系，这里要确定是否是选中的，没选中的说明是上级，1选中，0否
     */
    @Schema(description = "是否选中，因为有上下级关系，这里要确定是否是选中的，没选中的说明是上级")
    private Byte checked;

}
