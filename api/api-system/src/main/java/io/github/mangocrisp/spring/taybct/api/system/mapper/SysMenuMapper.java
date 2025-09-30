package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysMenu;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterPerm;
import io.github.mangocrisp.spring.taybct.api.system.vo.RouterVO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xijieyin
 * @see SysMenu
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据角色 code 来获取这个角色所有的菜单
     *
     * @param params      请求参数
     * @param pageOrder   排序
     * @param authorities 权限，角色列表
     * @param isRoot      是否是 root 用户
     * @return {@code List<SysMenuVO>}
     * @author xijieyin <br> 2022/8/5 21:46
     * @since 1.0.0
     */
    List<SysMenuVO> listByRoleCode(@Param("params") Map<String, Object> params, @Param("pageOrder") String pageOrder, @Param("authorities") Set<String> authorities, @Param("isRoot") Integer isRoot);

    /**
     * 根据角色 code 获取路由列表
     *
     * @param authorities 角色 code 列表
     * @param isRoot      是否是超级管理员
     * @return {@code LinkedHashSet<RouterVO>}
     */
    List<RouterVO> loadRouterByRoleCode(@Param("authorities") Set<String> authorities, @Param("isRoot") Integer isRoot);

    /**
     * 根据角色 code 获取权限列表
     *
     * @param authorities 角色 code 列表
     * @param isRoot      是否是超级管理员
     * @return {@code LinkedHashSet<RouterPerm>}
     */
    LinkedHashSet<RouterPerm> loadPermByRoleCode(@Param("authorities") Set<String> authorities, @Param("isRoot") Integer isRoot);
}
