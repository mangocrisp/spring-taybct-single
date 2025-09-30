package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRolePermission;
import io.github.mangocrisp.spring.taybct.module.system.controller.IRolePermController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRolePermissionService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色权限相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:24
 * @see SysRolePermission
 * @since 1.0.0
 */
public class RolePermControllerRegister implements IRolePermController {

    @Autowired(required = false)
    protected ISysRolePermissionService sysRolePermissionService;

    public ISysRolePermissionService getSysRolePermissionService() {
        return sysRolePermissionService;
    }

    /**
     * 获取列表
     *
     * @param dto 请求参数
     * @return {@code R<List<SysRolePermission>>}
     * @author xijieyin <br> 2022/8/5 21:22
     * @since 1.0.0
     */
    @Override
    public R<List<SysRolePermission>> list(SysRolePermission dto) {
        return R.data(getSysRolePermissionService().list(MyBatisUtil.genQueryWrapper(dto, null)));
    }

    /**
     * 批量保存对象
     *
     * @param domains   请求参数对象
     * @param primaryBy 根据哪个对象来操作，1是根据角色，其他是根据权限
     * @return {@code R<List<SysRolePermission>>}
     * @author xijieyin <br> 2022/8/5 21:23
     * @since 1.0.0
     */
    @ApiLog(title = "批量保存对象", description = "批量保存对象，并且在新增成功后一起返回", type = OperateType.INSERT
            , isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<List<SysRolePermission>> saveBatch(@Valid @NotNull @RequestBody List<SysRolePermission> domains
            , @RequestParam Integer primaryBy) {
        return getSysRolePermissionService().saveBatch(domains, primaryBy) ? R.data(domains)
                : R.fail(String.format("批量保存%s失败！", "角色关联权限"));
    }

    /**
     * 清理角色权限脏数据
     *
     * @author xijieyin <br> 2022/8/5 21:26
     * @since 1.0.0
     */
    @Override
    public void clearDirtyData() {
        // TODO 初始化角色权限配置后，可以配置角色有的权限，如果不配置权限有哪些角色，默认这个权限是开放的
        getSysRolePermissionService().clearDirtyData();
    }

    /**
     * 初始化角色权限配置
     *
     * @author xijieyin <br> 2022/8/5 21:27
     * @since 1.0.0
     */
    @Override
    public void iniConfig() {
        // TODO 初始化角色权限配置后，可以配置角色有的权限，如果不配置权限有哪些角色，默认这个权限是开放的
        getSysRolePermissionService().iniConfig();
    }

}
