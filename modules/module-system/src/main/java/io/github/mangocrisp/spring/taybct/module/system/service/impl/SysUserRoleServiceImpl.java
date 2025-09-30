package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserRole;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserRoleService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xijieyin
 */
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
        implements ISysUserRoleService {

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;
    @Autowired(required = false)
    protected ISecurityUtil securityUtil;
    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;
    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;
    @Autowired(required = false)
    protected SysUserMapper sysUserMapper;

    /**
     * 过滤 root
     */
    private LambdaQueryWrapper<SysUserRole> filterRole(QueryWrapper<SysUserRole> queryWrapper) {
        LambdaQueryWrapper<SysUserRole> lambda = queryWrapper.lambda();
        // 不能查询 root 用户
        lambda.ne(SysUserRole::getUserId, Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID)));
        // 不能查询 root 角色
        lambda.ne(SysUserRole::getRoleId, Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.ROLE_ROOT_ID)));
        return lambda;
    }

    @Override
    public List<SysUserRole> list(Wrapper<SysUserRole> queryWrapper) {
        return super.list(filterRole((QueryWrapper<SysUserRole>) queryWrapper));
    }

    @Override
    public <E extends IPage<SysUserRole>> E page(E page, Wrapper<SysUserRole> queryWrapper) {
        return super.page(page, filterRole((QueryWrapper<SysUserRole>) queryWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<SysUserRole> entityList) {

        // 先检查数据的有效性，这里根据关联的角色 id 查询出检查需要的字段
        PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                , () -> sysParamsObtainService
                , sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                        .select(SysRole::getId, SysRole::getCode, SysRole::getCreateUser)
                        .in(SysRole::getId, entityList.stream()
                                .map(SysUserRole::getRoleId).collect(Collectors.toSet())))
                , true);

        Optional<SysUserRole> first = entityList.stream().findFirst();
        Assert.isTrue(first.isPresent() && first.get().getUserId() != null,
                "至少要知道是在操作哪个用户，可能要传，例：[{\"userId\":\"xxx\"}]");

        // 用户关联角色，除了要检查角色，还要检查用户
        PermissionsValidityCheckTool.checkOperateUser(() -> securityUtil
                , () -> sysParamsObtainService, Collections.singletonList(sysUserMapper.selectById(first.get().getUserId())));

        ILoginUser loginUser = securityUtil.getLoginUser();
        String tenantId = loginUser.getTenantId();
        Set<Long> userIdSet = entityList.stream()
                .map(SysUserRole::getUserId)
                .collect(Collectors.toSet());


        // 先把之前关联的删除
        remove(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, userIdSet)
                .exists(String.format("select 1 from sys_role where id = sys_user_role.role_id and tenant_id = '%s'", tenantId)));
        // 过滤掉没有需要关联角色的关联关系
        Set<SysUserRole> wallSaveSet = entityList.stream().filter(entity ->
                        // 这里要过滤掉 root 角色 和 root 用户，root 用户/角色是不允许被修改的
                        entity.getRoleId() != null && entity.getUserId() != null
                                && !entity.getRoleId().equals(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.ROLE_ROOT_ID)))
                                && !entity.getUserId().equals(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID)))
                )
                .collect(Collectors.toSet());
        // 如果已经没有关联就直接返回了
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            // 如果只有一个元素，而且是没有菜单的，说明是要清除所有的菜单关联
            return true;
        }
        // 然后再批量插入
        if (super.saveBatch(wallSaveSet)) {
            Long[] userIds = new Long[userIdSet.size()];
            userIdSet.toArray(userIds);
            sysUserOnlineService.forceAllClientUserById("用户关联角色发生改变，强制登出！", userIds);
            return true;
        }
        return false;
    }
}




