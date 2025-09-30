package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserRole;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 系统用户角色关联相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:42
 * @since 1.0.0
 */
@Tag(name = "系统用户角色关联相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/userRole")
@ApiVersion
public interface IUserRoleController {

    /**
     * 获取列表
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return {@code R<List<SysUserRole>>}
     * @author xijieyin <br> 2022/8/5 21:43
     * @since 1.0.0
     */
    @Operation(summary = "获取列表")
    @GetMapping("list")
    @WebLog
    R<List<SysUserRole>> list(@RequestParam(required = false) Map<String, Object> sqlQueryParams);

    /**
     * 批量保存对象
     *
     * @param domains 请求参数对象
     * @return {@code R<List < SysUserRole>>}
     * @author xijieyin <br> 2022/8/5 21:43
     * @since 1.0.0
     */
    @Operation(summary = "批量保存对象")
    @PostMapping("batch")
    @ApiLog(title = "批量保存对象", description = "批量保存对象，并且在新增成功后一起返回", type = OperateType.INSERT, isSaveRequestData = false, isSaveResultData = false)
    R<List<SysUserRole>> saveBatch(@Valid @NotNull @RequestBody List<SysUserRole> domains);

}
