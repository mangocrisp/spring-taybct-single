package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.tool.core.util.tree.TreeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/4/19 00:29
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文档操作权限")
public class DeptUserTreeVO implements Serializable, TreeUtil.Tree<DeptUserTreeVO> {
    @Serial
    private static final long serialVersionUID = -3439880898504667280L;

    /**
     * 显示的 label，用户名或者部门名
     */
    @Schema(description = "显示的 label，用户名或者部门名")
    private String name;
    /**
     * 类型，user 或者 dept
     */
    @Schema(description = "类型，user 或者 dept")
    private String type;
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private String deptId;
    /**
     * 用户id，用户是有部门id的，因为是用户属于哪个部门
     */
    @Schema(description = "用户id，用户是有部门id的，因为是用户属于哪个部门")
    private String userId;
    /**
     * 用于确定上下级的 id
     */
    @Schema(description = "用于确定上下级的 id")
    private String id;
    /**
     * 用于确定上下级的父级 id
     */
    @Schema(description = "用于确定上下级的父级 id")
    private String parentId;
    /**
     * 子集，只有部门会有子集
     */
    @Schema(description = "子集，只有部门会有子集")
    private List<DeptUserTreeVO> children;

}
