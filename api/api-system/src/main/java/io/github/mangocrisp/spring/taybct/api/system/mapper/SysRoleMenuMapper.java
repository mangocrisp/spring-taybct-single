package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleMenu;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysRoleMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xijieyin
 * @see SysRoleMenu
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 获取角色菜单关联关系
     *
     * @param params 请求参数
     * @return {@code List<SysRoleMenuVO>}
     */
    List<SysRoleMenuVO> list(@Param("params") Map<String, Object> params, @Param("authorities") Set<String> authorities, @Param("isRoot") Integer isRoot);

    /**
     * 根据当前登录的用户的角色 code 来删除角色菜单关联
     *
     * @param roleId      需要删除的角色的 id
     * @param authorities 当前登录用户的角色 code 集合
     * @return 是否成功
     */
    int removeByLoginRoleCode(@Param("roleId") Long roleId, @Param("authorities") Set<String> authorities);

}
