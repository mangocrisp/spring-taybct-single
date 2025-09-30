package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Set;

/**
 * 权限分组 VO
 *
 * @author xijieyin <br> 2022/8/5 10:15
 * @since 1.0.4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "权限分组 VO 类")
public class PermissionGroupVO extends SysPermission {
    @Serial
    private static final long serialVersionUID = -4559558831148901295L;

    /**
     * 组名
     */
    @Schema(description = "组名")
    private String groupName;
    /**
     * 该分组下的所有的权限
     */
    @Schema(description = "该分组下的所有的权限")
    private Set<PermissionVO> permissionVOS;

}
