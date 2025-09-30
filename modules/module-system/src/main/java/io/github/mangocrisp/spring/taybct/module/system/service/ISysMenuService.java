package io.github.mangocrisp.spring.taybct.module.system.service;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysMenu;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterPerm;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysMenuVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单表操作的 service
 *
 * @author xijieyin <br> 2022/8/5 22:02
 * @see SysMenu
 * @since 1.0.0
 */
public interface ISysMenuService extends IBaseService<SysMenu> {

    /**
     * 获取菜单列表
     *
     * @param sqlQueryParams sql 查询参数
     * @return {@code List<SysMenuVO>}
     * @author xijieyin <br> 2022/8/5 22:03
     * @since 1.0.0
     */
    List<SysMenuVO> list(Map<String, Object> sqlQueryParams);

    /**
     * 根据角色 code 获取权限列表
     *
     * @return {@code LinkedHashSet<RouterVO>}
     * @author xijieyin <br> 2022/12/6 11:23
     * @since 1.0.0
     */
    List<RouterVO> loadRouterByRoleCode();

    /**
     * 获取用户权限
     *
     * @return {@code Set<RouterPerm>}
     * @author xijieyin <br> 2022/12/6 11:24
     * @since 1.0.0
     */
    Set<RouterPerm> loadPermByRoleCode();

}
