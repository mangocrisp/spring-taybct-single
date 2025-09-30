package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRoleService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 系统角色相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:20
 * @see SysRole
 * @see ISysRoleService
 * @since 1.0.0
 */
@Tag(name = "系统角色相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/role")
@ApiVersion
public interface ISysRoleController extends BaseController<SysRole, ISysRoleService> {

    /**
     * 获取角色列表（过滤出只有当前登录角色拥有的）
     *
     * @param dto {@literal 请求参数}
     * @return {@code R<List<SysUserRole>>}
     * @author xijieyin
     */
    @Operation(summary = "获取角色列表（过滤出只有当前登录角色拥有的）")
    @GetMapping("listFilterRole")
    @WebLog
    default R<List<SysRole>> listFilterRole(@NotNull SysRole dto) {
        return R.data(getBaseService().listFilterRole(dto));
    }
}
