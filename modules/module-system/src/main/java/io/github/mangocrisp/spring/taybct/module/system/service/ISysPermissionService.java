package io.github.mangocrisp.spring.taybct.module.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionGroupVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 权限关联表操作的 service
 *
 * @author xijieyin <br> 2022/8/5 22:04
 * @see SysPermission
 * @since 1.0.0
 */
public interface ISysPermissionService extends IBaseService<SysPermission> {

    /**
     * 带菜单信息的权限列表
     *
     * @param dto {@literal 请求参数}
     * @return {@code List<PermissionVO>}
     * @author xijieyin <br> 2022/9/21 10:01
     * @since 1.0.4
     */
    List<PermissionVO> listWithMenu(SysPermission dto);

    /**
     * 带菜单信息的权限分组（按菜单分组）
     *
     * @param dto {@literal 请求参数}
     * @return {@code List<PermissionGroupVO>}
     * @author xijieyin <br> 2022/9/21 10:13
     * @since 1.0.4
     */
    List<PermissionGroupVO> listGroupWithMenu(SysPermission dto);

    /**
     * 带菜单信息的权限分页
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return {@code IPage<PermissionVO>}
     */
    IPage<PermissionVO> pageWithMenu(Map<String, Object> sqlQueryParams);

}
