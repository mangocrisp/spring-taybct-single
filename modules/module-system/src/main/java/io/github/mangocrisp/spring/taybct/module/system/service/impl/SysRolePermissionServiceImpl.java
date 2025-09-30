package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRolePermission;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysPermissionGroupMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysPermissionMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRolePermissionMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionVO;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.ROLE;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRolePermissionService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.BaseEntity;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xijieyin
 */
@Slf4j
@EnableAsync
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
        implements ISysRolePermissionService {

    @Autowired(required = false)
    protected SysPermissionMapper sysPermissionMapper;
    @Autowired(required = false)
    protected SysPermissionGroupMapper sysPermissionGroupMapper;
    @Autowired(required = false)
    protected RedisTemplate<Object, Object> redisTemplate;
    @Autowired(required = false)
    protected ISecurityUtil securityUtil;
    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;
    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<SysRolePermission> entityList, Integer primaryBy) {
        if (primaryBy == null) {
            primaryBy = 1;
        }
        // 关联的所有的角色 id
        Set<Long> roleIdSet = entityList.stream().map(SysRolePermission::getRoleId).collect(Collectors.toSet());
        Optional<SysRolePermission> first = entityList.stream().findFirst();
        // 需要保存的列表
        Set<SysRolePermission> wallSaveSet;
        // 关联的所有的权限 id
        Set<Long> permissionIdSet = entityList.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toSet());
        if (primaryBy.equals(1)) {
            Assert.isTrue(first.isPresent() && first.get().getRoleId() != null,
                    "至少要知道是在操作哪个角色，可能要传，例：[{\"roleId\":\"xxx\"}]");

            // 先检查数据的有效性，这里根据关联的角色 id 查询出检查需要的字段
            PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                    , () -> sysParamsObtainService
                    , sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                            .select(SysRole::getId, SysRole::getCode, SysRole::getCreateUser)
                            .in(SysRole::getId, roleIdSet)));

            // 也有可能把分组当父级传过来
            Set<Long> groupIdSet = sysPermissionGroupMapper.selectList(Wrappers.lambdaQuery()).stream().map(BaseEntity::getId).collect(Collectors.toSet());
            // 前端有可能把默认分组也传过来
            groupIdSet.add(-1L);
            ILoginUser loginUser = securityUtil.getLoginUser();
            if (loginUser.checkAuthorities().contains(ROLE.ROOT) || loginUser.checkAuthorities().contains(ROLE.ADMIN)) {
                // 如果是 ROOT 或者 ADMIN 角色，就可以直接删除这个角色所有的权限
                // 先把之前关联的删除
                remove(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getRoleId, roleIdSet));
            } else {
                permissionIdSet.addAll(groupIdSet);
                // 如果不是 ROOT 或者 ADMIN 角色，就需要看当前登录的用户的角色有哪些权限，然后只能删除这些关联的权限
                roleIdSet.forEach(roleId ->
                        baseMapper.removeByLoginRoleCode(roleId, loginUser.getAuthorities(), permissionIdSet));
            }
            // 过滤掉没有 PermissionId 的关系
            wallSaveSet = entityList.stream().filter(entity ->
                            // 需要过滤一下为空的 permission id 以及与 group id 相同的 permission id
                            entity.getPermissionId() != null && entity.getRoleId() != null && !groupIdSet.contains(entity.getPermissionId()))
                    .collect(Collectors.toSet());
        } else {
            PermissionsValidityCheckTool.checkOperateOnlineAdmin(() -> securityUtil);

            Assert.isTrue(first.isPresent() && first.get().getPermissionId() != null,
                    "至少要知道是在操作哪个权限，可能要传，例：[{\"permissionId\":\"xxx\"}]");

            // 先把之前关联的删除
            remove(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getPermissionId, permissionIdSet));

            // 过滤掉没有 RoleId 的关系
            wallSaveSet = entityList.stream().filter(entity ->
                    entity.getRoleId() != null && entity.getPermissionId() != null).collect(Collectors.toSet());
        }
        // 如果已经没有关联就直接返回了
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            // 如果只有一个元素，而且是没有菜单的，说明是要清除所有的菜单关联
            return true;
        }
        // 然后再批量插入
        if (super.saveBatch(wallSaveSet)) {
            iniConfig();
            return true;
        }
        return false;
    }

    @Override
    public void clearDirtyData() {
        syncClear();
    }

    @Async
    public void syncClear() {
        log.debug("清理权限垃圾数据...");
        getBaseMapper().clearDirtyData();
        log.debug("...清理权限垃圾数据完成");
    }

    @Override
    public void iniConfig() {
        syncIni();
    }

    @Async
    public void syncIni() {
        log.debug("初始化角色权限配置...");
        List<PermissionVO> permissionVoList = sysPermissionMapper.loadPermissionRoles();
        Map<String, Set<String>> resourceRolesMap = new TreeMap<>();
        permissionVoList.forEach(permissionVO -> {
                    if (CollectionUtil.isEmpty(resourceRolesMap.get(permissionVO.getUrlPerm()))) {
                        resourceRolesMap.put(permissionVO.getUrlPerm(), permissionVO.getRoles().stream()
                                .map(SysRole::getCode).collect(Collectors.toSet()));
                    } else {
                        resourceRolesMap.get(permissionVO.getUrlPerm()).addAll(permissionVO.getRoles().stream()
                                .map(SysRole::getCode).collect(Collectors.toSet()));
                    }
                }
        );
        redisTemplate.delete(CacheConstants.Perm.URL);
        redisTemplate.opsForHash().putAll(CacheConstants.Perm.URL, resourceRolesMap);
        log.debug("...初始化角色权限配置完成");
    }

}




