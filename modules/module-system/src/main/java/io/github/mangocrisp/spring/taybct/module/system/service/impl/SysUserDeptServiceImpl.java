package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserDept;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserDeptService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 针对表【sys_user_dept(用户部门关联表，可以知道用户有多少部门，也可以知道部门有多少用户)】的数据库操作Service实现
 *
 * @author admin
 * 2023-06-14 17:40:21
 */
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept>
        implements ISysUserDeptService {

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;
    @Autowired(required = false)
    protected SysUserMapper sysUserMapper;
    @Autowired(required = false)
    protected ISecurityUtil securityUtil;
    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserDept(Set<SysUserDept> entityList) {
        Optional<SysUserDept> first = entityList.stream().findFirst();
        Assert.isTrue(first.isPresent() && first.get().getUserId() != null,
                "至少要知道是在操作哪个用户，可能要传，例：[{\"userId\":\"xxx\"}]");
        // 要检查用户是否被允许操作
        PermissionsValidityCheckTool.checkOperateUser(() -> securityUtil
                , () -> sysParamsObtainService, Collections.singletonList(sysUserMapper.selectById(first.get().getUserId())));

        ILoginUser loginUser = securityUtil.getLoginUser();
        String tenantId = loginUser.getTenantId();
        Set<Long> userIdSet = entityList.stream()
                .map(SysUserDept::getUserId)
                .collect(Collectors.toSet());

        // 先把之前关联的删除
        remove(Wrappers.<SysUserDept>lambdaQuery().in(SysUserDept::getUserId, userIdSet)
                .exists(String.format("select 1 from sys_dept where id = sys_user_dept.dept_id and tenant_id = '%s'", tenantId)));

        // 过滤掉没有需要关联的部门的关联关系
        Set<SysUserDept> wallSaveSet = entityList.stream().filter(entity ->
                        // 这里要过滤掉 root 用户，root 用户是不允许被修改的
                        entity.getDeptId() != null && entity.getUserId() != null
                                && !entity.getUserId().equals(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID)))
                )
                .collect(Collectors.toSet());
        // 如果已经没有关联就直接返回了
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            // 如果只有一个元素，而且是没有菜单的，说明是要清除所有的菜单关联
            return true;
        }

        // TODO 这里还要做一步检查，关联的部门是否是当前用户可以操作的部门，就是，可能有人利用接口来把不是他的部门分配给其他人

        // 然后再批量插入
        if (super.saveBatch(wallSaveSet)) {
            Long[] userIds = new Long[userIdSet.size()];
            userIdSet.toArray(userIds);
            sysUserOnlineService.forceAllClientUserById("用户关联部门发生改变，强制登出！", userIds);
            return true;
        }
        return false;
    }
}




