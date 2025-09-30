package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysDeptQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.vo.DeptUserTreeVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysDeptTreeVO;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDeptService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.tree.TreeUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 针对表【sys_dept(部门)】的数据库操作Service实现
 *
 * @author admin 2023-06-08 14:00:20
 */
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept>
        implements ISysDeptService {

    @Override
    public List<DeptUserTreeVO> deptUserTreeByCondition(JSONObject dto, boolean makeTree, boolean includeUser) {
        if (!dto.containsKey("keyWord")) {
            return new ArrayList<>();
        }
        String keyWord = dto.getString("keyWord");
        List<Long> deptId = getBaseMapper().selectList(Wrappers.<SysDept>lambdaQuery()
                        .select(SysDept::getId)
                        .like(SysDept::getName, keyWord))
                .stream().map(SysDept::getId).toList();
        List<Long> userDeptId = includeUser ? getBaseMapper().getDeptIdsByUserNickname(keyWord) : new ArrayList<>();
        return deptUserTree(new HashSet<>(CollectionUtil.union(deptId, userDeptId)), makeTree, includeUser);
    }

    @Override
    public List<DeptUserTreeVO> deptUserTree(Set<Long> deptIdSet, boolean includeUser) {
        return deptUserTree(deptIdSet, true, includeUser);
    }

    @Override
    public List<DeptUserTreeVO> deptUserTree(Set<Long> deptIdSet, boolean makeTree, boolean includeUser) {
        if (CollectionUtil.isEmpty(deptIdSet)) {
            return new ArrayList<>();
        }
        List<SysDept> sysDeptList = getBaseMapper().selectList(Wrappers.<SysDept>lambdaQuery()
                .select(SysDept::getId, SysDept::getPidAll)
                .in(SysDept::getId, deptIdSet));
        // 直接顶级部门的部门 id 集合（不包含最顶级的）
        Set<Long> deptIdSetUntilTop = sysDeptList.stream()
                .map(SysDept::getPidAll)
                .filter(StringUtil::isNotBlank)
                .flatMap(s -> Arrays.stream(s.split(",")))
                .map(Convert::toLong)
                .filter(i -> i != null && !i.equals(0L))
                .collect(Collectors.toSet());
        deptIdSet.addAll(deptIdSetUntilTop);
        return makeTree ? TreeUtil.tree(getBaseMapper().deptUserTree(deptIdSet, includeUser), "d_0") : getBaseMapper().deptUserTree(deptIdSet, includeUser);
    }

    @Override
    public List<SysDeptTreeVO> tree(SysDeptQueryDTO dto) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        return getBaseMapper().tree(dto
                , loginUser.getUserId()
                , loginUser.checkAuthorities()
                , loginUser.checkRoot());
    }

    @Override
    public IPage<? extends SysDept> page(SysDeptQueryDTO dto, IPage<?> page, SqlQueryParams pageParams) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        // 排序字段
        String pageOrder = MyBatisUtil.getPageOrder(pageParams);
        return getBaseMapper().deptFilterPage(page
                , pageOrder
                , dto
                , loginUser.getUserId()
                , loginUser.checkAuthorities()
                , loginUser.checkRoot());
    }

}
