package io.github.mangocrisp.spring.taybct.single.modules.system;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermissionGroup;
import io.github.mangocrisp.spring.taybct.module.system.controller.impl.SysPermissionGroupControllerRegister;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysPermissionGroupService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限分组接口
 *
 * @author xijieyin <br> 2022/9/23 9:35
 * @see SysPermissionGroup
 * @see ISysPermissionGroupService
 * @since 1.0.4
 */
@RestController
public class SysPermissionGroupController extends SysPermissionGroupControllerRegister {
}