package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRolePermission;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysPermissionMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionGroupVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionVO;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysPermissionService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRolePermissionService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xijieyin
 */
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionMapper, SysPermission>
        implements ISysPermissionService {

    @Resource
    ISysRolePermissionService sysRolePermissionService;

    @Override
    public boolean save(SysPermission entity) {
        PermissionsValidityCheckTool.checkOperateOnlineRoot(() -> securityUtil);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<SysPermission> entityList) {
        PermissionsValidityCheckTool.checkOperateOnlineRoot(() -> securityUtil);
        boolean result = false;
        try {
            Optional<SysPermission> first = entityList.stream().findFirst();
            Assert.isTrue(first.isPresent() && first.get().getMenuId() != null,
                    "至少要知道是在操作哪个菜单，可能要传，例：[{\"menuId\":\"xxx\"}]");
            // 这次更新或者新增的列表里面的 id
            Set<Long> updateIds = entityList.stream().map(SysPermission::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            // 然后把不在这次更新或者新增的列表里面的 id 的权限删除
            removeByIds(
                    // 先查询到菜单所有的关联的权限
                    list(Wrappers.<SysPermission>lambdaQuery()
                            .in(SysPermission::getMenuId,
                                    entityList.stream()
                                            .map(SysPermission::getMenuId)
                                            .collect(Collectors.toSet()))
                    ).stream()
                            .map(SysPermission::getId)
                            .filter(id -> !updateIds.contains(id))
                            .collect(Collectors.toSet())
            );
            Set<SysPermission> wallSaveSet = entityList.stream().filter(sp -> sp.getUrlPerm() != null).collect(Collectors.toSet());
            // 如果已经没有关联就直接返回了
            if (CollectionUtil.isEmpty(wallSaveSet)) {
                // 如果只有一个元素，而且是没有权限的，说明是要清除所有的菜单关联
                return true;
            }
            return (result = super.saveOrUpdateBatch(wallSaveSet));
        } finally {
            if (result) {
                // 删除权限与角色的关联之后初始化权限角色配置
                sysRolePermissionService.iniConfig();
            }
        }
    }

    @Override
    public boolean updateBatchById(Collection<SysPermission> entityList) {
        PermissionsValidityCheckTool.checkOperateOnlineRoot(() -> securityUtil);
        boolean result = false;
        try {
            return (result = super.updateBatchById(entityList));
        } finally {
            if (result) {
                // 删除权限与角色的关联之后初始化权限角色配置
                sysRolePermissionService.iniConfig();
            }
        }
    }

    @Override
    public boolean updateById(SysPermission entity) {
        PermissionsValidityCheckTool.checkOperateOnlineRoot(() -> securityUtil);
        boolean result = false;
        try {
            return (result = update(Wrappers.<SysPermission>lambdaUpdate()
                    .set(SysPermission::getUrlPerm, entity.getUrlPerm())
                    .set(SysPermission::getBtnPerm, entity.getBtnPerm())
                    .set(SysPermission::getGroupId, entity.getGroupId())
                    .set(SysPermission::getName, entity.getName())
                    .set(SysPermission::getMenuId, entity.getMenuId())
                    .eq(SysPermission::getId, entity.getId())));
        } finally {
            if (result) {
                // 删除权限与角色的关联之后初始化权限角色配置
                sysRolePermissionService.iniConfig();
            }
        }
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        PermissionsValidityCheckTool.checkOperateOnlineRoot(() -> securityUtil);
        boolean result = false;
        try {
            return (result = super.removeByIds(idList));
        } finally {
            if (result) {
                // 删除权限的同时删除权限与角色的关联
                sysRolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getPermissionId, idList));
                // 删除权限与角色的关联之后初始化权限角色配置
                sysRolePermissionService.iniConfig();
            }
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        PermissionsValidityCheckTool.checkOperateOnlineRoot(() -> securityUtil);
        boolean result = false;
        try {
            return (result = super.removeById(id));
        } finally {
            if (result) {
                // 删除权限的同时删除权限与角色的关联
                sysRolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getPermissionId, id));
                // 删除权限与角色的关联之后初始化权限角色配置
                sysRolePermissionService.iniConfig();
            }
        }
    }

    @Override
    public List<PermissionVO> listWithMenu(SysPermission dto) {
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(dto));
        params.remove("expansion");
        authoritiesFilter(params);
        return getBaseMapper().selectListVO(params).stream().peek(permissionVO -> {
            permissionVO.setMenuName(Optional.ofNullable(permissionVO.getMenuName()).orElse("未分组"));
            permissionVO.setMenuId(Optional.ofNullable(permissionVO.getMenuId()).orElse(-1L));
        }).collect(Collectors.toList());
    }

    @Override
    public List<PermissionGroupVO> listGroupWithMenu(SysPermission dto) {
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(dto));
        params.remove("expansion");
        authoritiesFilter(params);
        List<PermissionGroupVO> groupVOList = new ArrayList<>();
        getBaseMapper().selectListVO(params).forEach(permissionVO -> {
            Long groupId = Optional.ofNullable(permissionVO.getGroupId()).orElse(-1L);
            String groupName = Optional.ofNullable(permissionVO.getGroupName()).orElse("未分组");
            PermissionGroupVO permissionGroupVO = groupVOList.stream().filter(vo -> vo.getGroupId().equals(groupId))
                    .findFirst().orElseGet(() -> {
                        PermissionGroupVO groupVO = new PermissionGroupVO();
                        groupVO.setId(groupId);
                        groupVO.setGroupId(groupId);
                        groupVO.setName(groupName);
                        groupVO.setGroupName(groupName);
                        groupVO.setPermissionVOS(new LinkedHashSet<>());
                        groupVOList.add(groupVO);
                        return groupVO;
                    });
            permissionGroupVO.getPermissionVOS().add(permissionVO);
        });
        return groupVOList;
    }

    @Override
    public IPage<PermissionVO> pageWithMenu(Map<String, Object> sqlQueryParams) {
        Page<PermissionVO> page = MyBatisUtil.genPage(sqlQueryParams);
        SysPermission dto = JSONObject.parseObject(JSONObject.toJSONString(sqlQueryParams), SysPermission.class);
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(dto));
        params.remove("expansion");
        authoritiesFilter(params);
        return getBaseMapper().selectPageVo(page, params);
    }

}




