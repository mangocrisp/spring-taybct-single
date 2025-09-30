package io.github.mangocrisp.spring.taybct.module.system.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysDeptQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.vo.DeptUserTreeVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysDeptTreeVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;

import java.util.List;
import java.util.Set;

/**
 * 针对表【sys_dept(部门)】的数据库操作Service
 *
 * @author admin
 * 2023-06-08 14:00:20
 */
public interface ISysDeptService extends IBaseService<SysDept> {

    /**
     * 条件查询条件来获取树结构
     *
     * @param dto         查询条件
     * @param makeTree    是否要生成树结构
     * @param includeUser 是否包含搜索用户
     * @return 结果列表
     */
    List<DeptUserTreeVO> deptUserTreeByCondition(JSONObject dto, boolean makeTree, boolean includeUser);

    /**
     * 部门和用户一起的树
     *
     * @param deptIdSet   部门 id 集合
     * @param includeUser 是否包含搜索用户
     * @return 结果列表
     */
    List<DeptUserTreeVO> deptUserTree(Set<Long> deptIdSet, boolean includeUser);


    /**
     * 获取到所有的部门和部门下的人
     *
     * @param deptIdSet   按部门查询
     * @param makeTree    是否要生成树结构
     * @param includeUser 是否包含搜索用户
     * @return 部门和部门下的人
     */
    List<DeptUserTreeVO> deptUserTree(Set<Long> deptIdSet, boolean makeTree, boolean includeUser);

    /**
     * 查询树
     *
     * @param dto 查询参数
     * @return 树结构
     */
    List<SysDeptTreeVO> tree(SysDeptQueryDTO dto);

    /**
     * 分页查询
     *
     * @param dto        查询参数
     * @param page       分页参数
     * @param pageParams 分页参数
     * @return 分页结果
     */
    IPage<? extends SysDept> page(SysDeptQueryDTO dto, IPage<?> page, SqlQueryParams pageParams);

}
