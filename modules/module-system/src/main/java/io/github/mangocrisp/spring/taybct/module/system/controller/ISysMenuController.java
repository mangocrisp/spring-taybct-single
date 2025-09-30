package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysMenu;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysMenuQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterPerm;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterVO;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysMenuService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.QueryBaseController;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.CollectionSortUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.tree.TreeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统菜单相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:03
 * @see SysMenu
 * @see ISysMenuService
 * @since 1.0.0
 */
@Tag(name = "系统菜单相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/menu")
@ApiVersion
public interface ISysMenuController extends QueryBaseController<SysMenu, ISysMenuService, SysMenuQueryDTO> {

    /**
     * {@inheritDoc}<br>
     * 这个类获取的菜单列表会与登录的用户有关，和这个用户的角色有关，返回这个登录的
     * 用户的角色有被分配的菜单的列表
     *
     * @param sqlQueryParams sql 查询参数
     * @return {@code R<List<SysMenu>>}
     * @author xijieyin <br> 2022/8/5 21:04
     * @since 1.0.0
     */
    @Override
    default R<List<? extends SysMenu>> list(@RequestParam(required = false) Map<String, Object> sqlQueryParams) {
        return R.data(getBaseService().list(sqlQueryParams));
    }

    /**
     * 获取路由（树状）
     *
     * @return {@code R<RouterVO>}
     * @author xijieyin <br> 2022/8/5 21:07
     * @since 1.0.0
     */
    @Operation(summary = "获取路由")
    @GetMapping("router")
    @WebLog
    default R<List<RouterVO>> router() {
        return R.data(TreeUtil.tree(getBaseService().loadRouterByRoleCode(), CollectionSortUtil.comparingAny(RouterVO::getSort), 0L));
    }

    /**
     * 获取用户权限
     *
     * @return {@code R<Set<RouterPerm>>}
     * @author xijieyin <br> 2022/8/5 21:07
     * @since 1.0.0
     */
    @Operation(summary = "获取用户权限")
    @GetMapping("perm")
    @WebLog
    default R<Set<RouterPerm>> perm() {
        return R.data(getBaseService().loadPermByRoleCode());
    }
}
