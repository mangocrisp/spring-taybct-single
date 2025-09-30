package io.github.mangocrisp.spring.taybct.module.system.service;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysTenant;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

import java.util.List;

/**
 * 针对表【sys_tenant(租户表)】的数据库操作Service
 *
 * @author xijieyin <br> 2022/8/17 10:27
 * @see SysTenant
 * @since 1.0.1
 */
public interface ISysTenantService extends IBaseService<SysTenant> {

    /**
     * 获取用户拥有的关联的租户
     *
     * @return List&lt;SysTenant&gt;
     * @author xijieyin <br> 2022/8/17 10:44
     * @since 1.0.1
     */
    List<SysTenant> listUserTenant();

}
