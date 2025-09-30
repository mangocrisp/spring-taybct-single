package io.github.mangocrisp.spring.taybct.api.system.dto;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户查询 dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户查询 dto")
public class SysUserQueryDTO extends SysUser {

    private static final long serialVersionUID = -1686280786678061917L;

    /**
     * 是否是当前登录用户创建的
     */
    @Schema(description = "是否是当前登录用户创建的")
    private Integer isCreateByLoginUser;
    /**
     * 按用户过滤
     */
    @Schema(description = "按用户过滤")
    private Integer filterByUser;
    /**
     * 按角色过滤
     */
    @Schema(description = "按角色过滤")
    private Integer filterByRole;
    /**
     * 是否要查询所有的父级(一般是查询树的时候用，可以把所有的父级都查询回来)
     */
    @Schema(description = "是否要查询所有的父级(一般是查询树的时候用，可以把所有的父级都查询回来)")
    private Integer includeParents;
    /**
     * 是否要查询所有的子集
     */
    @Schema(description = "是否要查询所有的子集")
    private Integer includeChildren;
    /**
     * 查询部门 id
     */
    @Schema(description = "查询部门 id")
    private Long deptId;

}