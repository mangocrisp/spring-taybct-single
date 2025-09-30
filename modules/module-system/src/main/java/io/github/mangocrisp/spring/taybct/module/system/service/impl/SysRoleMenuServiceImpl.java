package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleMenu;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMenuMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysRoleMenuVO;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.ROLE;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRoleMenuService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
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
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
        implements ISysRoleMenuService {

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;
    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;
    @Autowired(required = false)
    protected ISecurityUtil securityUtil;
    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRoleMenuVO> list(SysRoleMenu dto) {
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(dto));
        params.remove("expansion");
        ILoginUser loginUser = securityUtil.getLoginUser();
        return getBaseMapper().list(params, loginUser.checkAuthorities(), loginUser.checkRoot());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<SysRoleMenu> entityList, Integer primaryBy) {
        // 先检查数据的有效性，这里根据关联的角色 id 查询出检查需要的字段
        PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                , () -> sysParamsObtainService
                , sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                        .select(SysRole::getId, SysRole::getCode, SysRole::getCreateUser)
                        .in(SysRole::getId, entityList.stream()
                                .map(SysRoleMenu::getRoleId).collect(Collectors.toSet()))));

        if (primaryBy == null) {
            primaryBy = 1;
        }
        // 关联的角色集合
        Set<Long> roleIdSet = Collections.emptySet();
        Optional<SysRoleMenu> first = entityList.stream().findFirst();
        Set<SysRoleMenu> wallSaveSet;
        if (primaryBy.equals(1)) {
            Assert.isTrue(first.isPresent() && first.get().getRoleId() != null,
                    "至少要知道是在操作哪个角色，可能要传，例：[{\"roleId\":\"xxx\"}]");
            // 所有关联的角色 id

            // 过滤掉没有 MenuId 的关联关系
            wallSaveSet = entityList.stream().filter(entity -> entity.getMenuId() != null && entity.getRoleId() != null).collect(Collectors.toSet());

            // 所有关联的角色 id
            roleIdSet = entityList.stream().map(SysRoleMenu::getRoleId).collect(Collectors.toSet());

            ILoginUser loginUser = securityUtil.getLoginUser();
            if (loginUser.checkAuthorities().contains(ROLE.ROOT) || loginUser.checkAuthorities().contains(ROLE.ADMIN)) {
                // 如果是 ROOT 或者 ADMIN 角色，就可以直接删除这个角色所有的菜单
                // 先把之前关联的删除
                remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, roleIdSet));
            } else {
                // 如果不是 ROOT 或者 ADMIN 角色，就需要看当前登录的用户的角色有哪些菜单，然后只能删除这些关联的菜单
                roleIdSet.forEach(roleId ->
                        baseMapper.removeByLoginRoleCode(roleId, loginUser.getAuthorities()));
            }
        } else {

            PermissionsValidityCheckTool.checkOperateOnlineAdmin(() -> securityUtil);

            Assert.isTrue(first.isPresent() && first.get().getMenuId() != null,
                    "至少要知道是在操作哪个菜单，可能要传，例：[{\"menuId\":\"xxx\"}]");
            // 所有关联的菜单 id
            // 先把之前关联的删除
            remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getMenuId,
                    entityList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet())));
            // 过滤掉没有 RoleId 的关联关系
            wallSaveSet = entityList.stream().filter(entity -> entity.getRoleId() != null && entity.getMenuId() != null).collect(Collectors.toSet());
        }
        // 如果已经没有关联就直接返回了
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            // 如果只有一个元素，而且是没有菜单的，说明是要清除所有的菜单关联
            return true;
        }
        // 找一下菜单关联里面有没有关联 Layout
        Optional<SysRoleMenu> hasLayout = wallSaveSet.stream()
                .filter(entity -> entity.getMenuId().equals(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.MENU_LAYOUT))))
                .findFirst();
        // 要加一个 Layout 的菜单，这个是顶级父级菜单
        if (hasLayout.isEmpty()) {
            // 如果没有就加
            wallSaveSet.add(new SysRoleMenu(first.get().getRoleId()
                    , Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.MENU_LAYOUT))
                    , (byte) 1));
        }
        // 然后再批量插入
        if (super.saveBatch(wallSaveSet) && roleIdSet.size() > 0) {
            Long[] roleIds = new Long[roleIdSet.size()];
            roleIdSet.toArray(roleIds);
            sysUserOnlineService.forceAllClientUserByRole("角色信息发生改变，被强制登出！", roleIds);
            return true;
        }
        return false;
    }

}
