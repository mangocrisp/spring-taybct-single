package io.github.mangocrisp.spring.taybct.api.system.dto;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 部门查询 dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "部门查询 dto")
public class SysDeptQueryDTO extends SysDept {

    private static final long serialVersionUID = -1686280786678061917L;

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
     * 从哪个父级开始往下生子子集，默认从 0 开始
     */
    @Schema(description = "从哪个父级开始往下生子子集(查询树时使用)，默认从 0 开始")
    private Long parentId = 0L;
    /**
     * 是否包含顶级父级，如果不包含，会有多个根目录
     */
    @Schema(description = "是否包含顶级父级(查询树时使用)，如果不包含，会有多个根目录，默认不包含父级(false)")
    private boolean includeTopParent = false;

}