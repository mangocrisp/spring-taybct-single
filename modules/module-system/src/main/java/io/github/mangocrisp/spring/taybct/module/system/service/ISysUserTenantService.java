package io.github.mangocrisp.spring.taybct.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserTenant;

import java.util.Collection;

/**
 * 针对表【sys_user_tenant(用户_租户关联)】的数据库操作Service
 *
 * @author xijieyin <br> 2022/8/17 10:29
 * @see SysUserTenant
 * @since 1.0.1
 */
public interface ISysUserTenantService extends IService<SysUserTenant> {

    /**
     * 批量保存关联关系
     *
     * @param entityList 用户租户关联列表
     * @param primaryBy  以什么为主去保存 1 用户，其他 租户
     * @return boolean
     * @author xijieyin <br> 2022/8/17 14:05
     * @since 1.0.1
     */
    boolean saveBatch(Collection<SysUserTenant> entityList, Integer primaryBy);

}
