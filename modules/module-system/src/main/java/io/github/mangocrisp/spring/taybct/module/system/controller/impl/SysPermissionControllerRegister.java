package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysPermissionController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单权限相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:19
 * @see SysPermission
 * @see ISysPermissionService
 * @since 1.0.0
 */
public class SysPermissionControllerRegister implements ISysPermissionController {

    @Autowired(required = false)
    protected ISysPermissionService sysPermissionService;

    @Override
    public ISysPermissionService getBaseService() {
        return sysPermissionService;
    }
}
