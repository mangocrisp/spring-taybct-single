package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleDept;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRoleDeptService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 针对表【sys_role_dept(角色部门关联表，可以知道角色有多少部门，也可以知道部门有多少角色)】的数据库操作Service实现
 *
 * @author admin
 * 2023-06-14 17:40:21
 */
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept>
        implements ISysRoleDeptService {

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;
    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;
    @Autowired(required = false)
    protected ISecurityUtil securityUtil;
    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleDept(Set<SysRoleDept> entityList) {
        Optional<SysRoleDept> first = entityList.stream().findFirst();
        Assert.isTrue(first.isPresent() && first.get().getRoleId() != null,
                "至少要知道是在操作哪个角色，可能要传，例：[{\"roleId\":\"xxx\"}]");
        // 先检查数据的有效性，这里根据关联的角色 id 查询出检查需要的字段
        PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                , () -> sysParamsObtainService
                , sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                        .select(SysRole::getId, SysRole::getCode, SysRole::getCreateUser)
                        .in(SysRole::getId, entityList.stream()
                                .map(SysRoleDept::getRoleId).collect(Collectors.toSet()))));

        ILoginUser loginUser = securityUtil.getLoginUser();
        String tenantId = loginUser.getTenantId();
        Set<Long> roleIdSet = entityList.stream()
                .map(SysRoleDept::getRoleId)
                .collect(Collectors.toSet());

        // 先把之前关联的删除
        remove(Wrappers.<SysRoleDept>lambdaQuery().in(SysRoleDept::getRoleId, roleIdSet)
                .exists(String.format("select 1 from sys_dept where id = sys_role_dept.dept_id and tenant_id = '%s'", tenantId))
                .exists(String.format("select 1 from sys_role where id = sys_user_role.role_id and tenant_id = '%s'", tenantId)));

        // 过滤掉没有需要关联的部门的关联关系
        Set<SysRoleDept> wallSaveSet = entityList.stream().filter(entity ->
                        // 这里要过滤掉 root 角色，root 角色是不允许被修改的
                        entity.getDeptId() != null && entity.getRoleId() != null
                                && !entity.getRoleId().equals(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.ROLE_ROOT_ID)))
                )
                .collect(Collectors.toSet());
        // 如果已经没有关联就直接返回了
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            // 如果只有一个元素，而且是没有菜单的，说明是要清除所有的菜单关联
            return true;
        }
        // 然后再批量插入
        if (super.saveBatch(wallSaveSet)) {
            Long[] roleIds = new Long[roleIdSet.size()];
            roleIdSet.toArray(roleIds);
            sysUserOnlineService.forceAllClientUserByRole("角色关联部门发生改变，被强制登出！", roleIds);
            return true;
        }
        return false;
    }
}




