package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysMenu;
import io.github.mangocrisp.spring.taybct.tool.core.util.tree.TreeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单列表
 *
 * @author xijieyin <br> 2022/8/5 10:17
 * @since 1.0.0
 */
@Schema(description = "菜单列表")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysMenuVO extends SysMenu implements TreeUtil.Tree<SysMenuVO> {

    @Serial
    private static final long serialVersionUID = 7577197144305024800L;
    @Schema(description = "是否有子级")
    Boolean hasChildren;

    /**
     * 是否被选中
     */
    Boolean checked = false;

    /**
     * 子集
     */
    List<SysMenuVO> children = new ArrayList<>();

    @Override
    public void setChildren(List<SysMenuVO> children) {
        this.children = children;
    }

}
