package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Set;

/**
 * 权限 VO
 *
 * @author xijieyin <br> 2022/8/5 10:15
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "权限 VO 类")
public class PermissionVO extends SysPermission {
    @Serial
    private static final long serialVersionUID = 1660771852957433441L;
    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String menuName;
    /**
     * 组名
     */
    @Schema(description = "组名")
    private String groupName;
    /**
     * 该权限下的角色
     */
    @Schema(description = "该权限下的角色")
    private Set<SysRole> roles;

}
