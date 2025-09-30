package io.github.mangocrisp.spring.taybct.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionGroupVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionVO;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysPermissionService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 菜单权限相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:19
 * @see SysPermission
 * @see ISysPermissionService
 * @since 1.0.0
 */
@Tag(name = "菜单权限相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/permission")
@ApiVersion
public interface ISysPermissionController extends BaseController<SysPermission, ISysPermissionService> {

    /**
     * 获取列表带菜单名
     *
     * @param dto {@literal 请求参数}
     * @return {@code R<List < PermissionVO>>}
     * @author xijieyin <br> 2022/9/21 10:04
     * @since 1.0.4
     */
    @Operation(summary = "获取列表带菜单名")
    @GetMapping("listWithMenu")
    @WebLog
    default R<List<PermissionVO>> listWithMenu(SysPermission dto) {
        return R.data(getBaseService().listWithMenu(dto));
    }

    /**
     * 获取列表按菜单分组
     *
     * @param dto {@literal 请求参数}
     * @return {@code R<List < PermissionVO>>}
     * @author xijieyin <br> 2022/9/21 10:05
     * @since 1.0.4
     */
    @Operation(summary = "获取列表按菜单分组")
    @GetMapping("listGroupWithMenu")
    @WebLog
    default R<List<PermissionGroupVO>> listGroupWithMenu(SysPermission dto) {
        return R.data(getBaseService().listGroupWithMenu(dto));
    }

    /**
     * 获取分页(返回菜单信息)
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return {@code R<IPage < PermissionVO>>}
     * @author xijieyin <br> 2022/8/5 21:19
     * @since 1.0.0
     */
    @Operation(summary = "获取分页(返回菜单信息)")
    @GetMapping("pageWithMenu")
    @WebLog
    default R<IPage<PermissionVO>> pageWithMenu(@RequestParam(required = false) Map<String, Object> sqlQueryParams) {
        return R.data(getBaseService().pageWithMenu(sqlQueryParams));
    }

}
