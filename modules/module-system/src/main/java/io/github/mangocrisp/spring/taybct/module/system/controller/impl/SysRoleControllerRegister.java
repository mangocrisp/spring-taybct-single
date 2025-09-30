package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysRoleController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统角色相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:20
 * @see SysRole
 * @see ISysRoleService
 * @since 1.0.0
 */
public class SysRoleControllerRegister implements ISysRoleController {

    @Autowired(required = false)
    protected ISysRoleService sysRoleService;

    @Override
    public ISysRoleService getBaseService() {
        return sysRoleService;
    }

}
