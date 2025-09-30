package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.tool.core.util.tree.TreeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * 部门树
 *
 * @author XiJieYin <br> 2023/6/8 14:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class SysDeptTreeVO extends SysDept implements Serializable, TreeUtil.Tree<SysDeptTreeVO> {

    private static final long serialVersionUID = -2763939599364941603L;

    @Override
    public Serializable getParentId() {
        return getPid();
    }

    /**
     * 子集菜单
     */
    private List<SysDeptTreeVO> children;

}
