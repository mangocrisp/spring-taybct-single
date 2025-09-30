package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleMenu;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysRoleMenuVO;
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
 * 角色菜单相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:20
 * @see SysRoleMenu
 * @since 1.0.0
 */
@Tag(name = "角色菜单相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/roleMenu")
@ApiVersion
public interface IRoleMenuController {

    /**
     * 获取列表
     *
     * @param dto 请求参数
     * @return {@code R<List < SysRoleMenu>>}
     * @author xijieyin <br> 2022/8/5 21:21
     * @since 1.0.0
     */
    @Operation(summary = "获取列表")
    @GetMapping("list")
    R<List<SysRoleMenu>> list(SysRoleMenu dto);

    /**
     * 获取列表（带角色菜单详细信息）
     *
     * @param dto 请求参数
     * @return {@code R<List < SysRoleMenuVO>>}
     * @author xijieyin <br> 2022/8/5 21:21
     * @since 1.0.0
     */
    @Operation(summary = "获取列表（带角色菜单详细信息）")
    @GetMapping("listVO")
    R<List<SysRoleMenuVO>> listVO(@NotNull SysRoleMenu dto);

    /**
     * 批量保存对象
     *
     * @param domains   请求参数
     * @param primaryBy 根据哪个对象来操作，1是根据角色，其他是根据菜单
     * @return {@code R<List < SysRoleMenu>>}
     * @author xijieyin <br> 2022/8/5 21:21
     * @since 1.0.0
     */
    @Operation(summary = "批量保存对象")
    @PostMapping("batch")
    @ApiLog(title = "批量保存对象", description = "批量保存对象，并且在新增成功后一起返回", type = OperateType.INSERT
            , isSaveRequestData = false, isSaveResultData = false)
    @Parameter(name = "primaryBy", description = "根据哪个对象来操作，1是根据角色，其他是根据菜单")
    R<List<SysRoleMenu>> saveBatch(@Valid @NotNull @RequestBody List<SysRoleMenu> domains, @RequestParam Integer primaryBy);
}




