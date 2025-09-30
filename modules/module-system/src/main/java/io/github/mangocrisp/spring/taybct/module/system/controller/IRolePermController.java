package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRolePermission;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@Tag(name = "角色权限相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/rolePerm")
@ApiVersion
public interface IRolePermController {

    /**
     * 获取列表
     *
     * @param dto 请求参数
     * @return {@code R<List<SysRolePermission>>}
     * @author xijieyin <br> 2022/8/5 21:22
     * @since 1.0.0
     */
    @Operation(summary = "获取列表")
    @GetMapping("list")
    R<List<SysRolePermission>> list(SysRolePermission dto);

    /**
     * 批量保存对象
     *
     * @param domains   请求参数对象
     * @param primaryBy 根据哪个对象来操作，1是根据角色，其他是根据权限
     * @return {@code R<List<SysRolePermission>>}
     * @author xijieyin <br> 2022/8/5 21:23
     * @since 1.0.0
     */
    @Operation(summary = "批量保存对象")
    @PostMapping("batch")
    @ApiLog(title = "批量保存对象", description = "批量保存对象，并且在新增成功后一起返回", type = OperateType.INSERT
            , isSaveRequestData = false, isSaveResultData = false)
    @Parameter(name = "primaryBy", description = "根据哪个对象来操作，1是根据角色，其他是根据权限")
    R<List<SysRolePermission>> saveBatch(@Valid @NotNull @RequestBody List<SysRolePermission> domains
            , @RequestParam Integer primaryBy);

    /**
     * 清理角色权限脏数据
     *
     * @author xijieyin <br> 2022/8/5 21:26
     * @since 1.0.0
     */
    @Operation(summary = "清理角色权限脏数据")
    @PostMapping(value = "clearDirtyData")
    void clearDirtyData();

    /**
     * 初始化角色权限配置
     *
     * @author xijieyin <br> 2022/8/5 21:27
     * @since 1.0.0
     */
    @Operation(summary = "初始化角色权限配置")
    @PostMapping(value = "iniConfig")
    void iniConfig();
}
