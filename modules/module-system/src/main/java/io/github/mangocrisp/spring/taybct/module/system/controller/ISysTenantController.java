package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysTenant;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserTenant;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysTenantService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.*;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * 租户相关接口
 *
 * @author xijieyin <br> 2022/8/17 10:29
 * @see SysTenant
 * @see BaseController
 * @see ISysTenantService
 * @since 1.0.1
 */
@Tag(name = "租户相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/tenant")
@ApiVersion
public interface ISysTenantController extends BaseController<SysTenant, ISysTenantService> {

    @WebLog
    @SafeConvert(key = "domain", safeIn = SysTenant.class, ignoreIn = {"tenantId"})
    @Override
    default R<? extends SysTenant> updateAllField(@Valid @NotNull @RequestBody SysTenant domain) {
        return BaseController.super.updateAllField(domain);
    }

    @Operation(summary = "为用户分配租户")
    @PostMapping("user")
    @WebLog
    @ApiLog(title = "为用户分配租户", description = "为用户分配租户，并且在新增成功后一起返回", type = OperateType.INSERT
            , isSaveRequestData = false, isSaveResultData = false)
    @Parameter(name = "primaryBy", description = "根据哪个对象来操作，1是根据用户，其他是根据租户")
    R<Collection<SysUserTenant>> addUserTenant(@Valid @NotNull @RequestBody Collection<SysUserTenant> domains, @RequestParam Integer primaryBy);

    @Operation(summary = "获取当前登录用户拥有的租户")
    @GetMapping(value = "user")
    R<List<SysTenant>> listUserTenant();

    @Operation(summary = "获取指定用户拥有的租户")
    @GetMapping(value = "user/{userId}")
    R<List<SysUserTenant>> listUserTenant(@PathVariable Long userId);

    @Operation(summary = "用户选择租户")
    @Parameters({
            @Parameter(name = "tenantId", description = "租户id", required = true, in = ParameterIn.PATH, example = "000000")
    })
    @PostMapping(value = "user/{tenantId}")
    R<?> chooseTenant(@NotNull @PathVariable String tenantId);

    @Operation(summary = "获取当前用户的租户")
    @GetMapping(value = "current")
    R<SysTenant> currentTenant();

}
