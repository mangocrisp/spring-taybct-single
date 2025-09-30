package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysTenant;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysTenantMapper;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysTenantService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * @author xijieyin
 */
public class SysTenantServiceImpl extends BaseServiceImpl<SysTenantMapper, SysTenant>
        implements ISysTenantService {

    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;

    @Override
    public boolean save(SysTenant entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<SysTenant> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public List<SysTenant> listUserTenant() {
        ILoginUser loginUser = securityUtil.getLoginUser();
        Long userId = loginUser.getUserId();
        return getBaseMapper().listUserTenant(userId
                , sysParamsObtainService.get(CacheConstants.Params.TENANT_ID)
                , checkRoot());
    }

}




