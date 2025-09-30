package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysTenant;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserTenant;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysTenantController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysTenantService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserTenantService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 租户相关接口
 *
 * @author xijieyin <br> 2022/8/17 10:29
 * @see SysTenant
 * @see io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController
 * @see ISysTenantService
 * @since 1.0.1
 */
public class SysTenantControllerRegister implements ISysTenantController {

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;

    @Autowired(required = false)
    protected ISysUserTenantService sysUserTenantService;

    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;

    @Autowired(required = false)
    protected ISecurityUtil securityUtil;

    @Autowired(required = false)
    protected ISysTenantService sysTenantService;

    public ISysUserOnlineService getSysUserOnlineService() {
        return sysUserOnlineService;
    }

    public ISysUserTenantService getSysUserTenantService() {
        return sysUserTenantService;
    }

    public ISysParamsObtainService getSysParamsObtainService() {
        return sysParamsObtainService;
    }

    public ISecurityUtil getSecurityUtil() {
        return securityUtil;
    }

    @Override
    public ISysTenantService getBaseService() {
        return sysTenantService;
    }

    @WebLog
    @ApiLog(title = "为用户分配租户", description = "为用户分配租户，并且在新增成功后一起返回", type = OperateType.INSERT
            , isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<Collection<SysUserTenant>> addUserTenant(@Valid @NotNull @RequestBody Collection<SysUserTenant> domains
            , @RequestParam Integer primaryBy) {
        return getSysUserTenantService().saveBatch(domains, primaryBy) ? R.data(domains) : R.fail(String.format("为用户分配租户%s失败！", getResource()));
    }

    @Override
    public R<List<SysTenant>> listUserTenant() {
        return Optional.ofNullable(getBaseService()
                        .listUserTenant()).map(R::data)
                .orElseGet(() -> R.data(Collections.singletonList(SysTenant
                        .defaultTenant(getSysParamsObtainService().get(CacheConstants.Params.TENANT_ID)))));
    }

    @Override
    public R<List<SysUserTenant>> listUserTenant(@PathVariable Long userId) {
        return R.data(getSysUserTenantService().list(Wrappers.<SysUserTenant>lambdaQuery().eq(SysUserTenant::getUserId, userId)));
    }

    @Override
    public R<?> chooseTenant(@NotNull @PathVariable String tenantId) {
        return getSysUserOnlineService().chooseTenant(tenantId) ? R.ok("操作成功！") : R.fail("操作失败！");
    }

    @Override
    public R<SysTenant> currentTenant() {
        return R.data(getBaseService().getOne(Wrappers.<SysTenant>lambdaQuery().eq(SysTenant::getTenantId, getSecurityUtil().getLoginUser().getTenantId())));
    }

}
