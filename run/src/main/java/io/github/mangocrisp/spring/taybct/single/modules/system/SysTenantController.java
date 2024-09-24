package io.github.mangocrisp.spring.taybct.single.modules.system;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysTenant;
import io.github.mangocrisp.spring.taybct.module.system.controller.impl.SysTenantControllerRegister;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysTenantService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户相关接口
 *
 * @author xijieyin <br> 2022/8/17 10:29
 * @see SysTenant
 * @see io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController
 * @see ISysTenantService
 * @since 1.0.1
 */
@RestController
public class SysTenantController extends SysTenantControllerRegister {
}
