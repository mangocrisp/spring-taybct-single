package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author xijieyin
 * @see SysRolePermission
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /**
     * 清理脏数据
     *
     * @return int
     */
    int clearDirtyData();

    /**
     * 根据当前登录的用户的角色 code 来删除角色权限关联
     *
     * @param roleId          需要删除的角色的 id
     * @param authorities     当前登录用户的角色 code 集合
     * @param permissionIdSet 添加的权限，要把原来的有的也一起删除，然后再添加进去
     * @return 是否成功
     */
    int removeByLoginRoleCode(@Param("roleId") Long roleId, @Param("authorities") Set<String> authorities, @Param("permissionIdSet") Set<Long> permissionIdSet);
}
