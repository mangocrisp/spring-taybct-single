package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysRoleQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xijieyin
 * @see SysRole
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户的角色数量
     *
     * @param userId   用户 id
     * @param tenantId 租户 id
     * @return int
     * @author xijieyin <br> 2022/10/28 13:51
     * @since 1.1.0
     */
    @InterceptorIgnore(tenantLine = "true")
    int countByUserIdTenantId(@Param("userId") Long userId, @Param("tenantId") String tenantId);

    /**
     * 查询指定租户下的用户的角色
     *
     * @param userId   用户 id
     * @param tenantId 租户 id
     * @return {@code List<SysRole>}
     * @author xijieyin <br> 2022/10/28 13:52
     * @since 1.1.0
     */
    @InterceptorIgnore(tenantLine = "true")
    List<SysRole> listUserTenantRoles(@Param("userId") Long userId, @Param("tenantId") String tenantId);

    /**
     * 先查询个数，按条件查询个数，如果有结果，再查询列表，或者分页
     *
     * @param tenantId    租户id
     * @param operator    操作者的 id
     * @param root        超级管理员的角色 code ，这个是默认的
     * @param admin       管理员的角色 code ，这个是默认的
     * @param params      查询参数
     * @param dto         查询参数
     * @param authorities 用户权限
     * @param isRoot      是否是 ROOT 角色
     * @param isAdmin     是否是 ADMIN 角色
     * @return int 个数
     * @author xijieyin <br> 2022/9/28 11:47
     * @since 1.0.4
     */
    long countQuery(@Param("tenantId") String tenantId
            , @Param("operator") Long operator
            , @Param("root") String root
            , @Param("admin") String admin
            , @Param("params") Map<String, Object> params
            , @Param("dto") SysRoleQueryDTO dto
            , @Param("authorities") Set<String> authorities
            , @Param("isRoot") Integer isRoot
            , @Param("isAdmin") Integer isAdmin);

    /**
     * 条件查询
     *
     * @param tenantId    租户id
     * @param operator    操作者的 id
     * @param root        超级管理员的角色 code ，这个是默认的
     * @param admin       管理员的角色 code ，这个是默认的
     * @param params      查询参数
     * @param offset      查询起始位置
     * @param size        大小
     * @param pageOrder   排序字段
     * @param dto         查询参数
     * @param authorities 用户权限
     * @param isRoot      是否是 ROOT 角色
     * @param isAdmin     是否是 ADMIN 角色
     * @return {@code List<SysUser>}
     * @author xijieyin <br> 2022/9/28 11:49
     * @since 1.0.4
     */
    List<SysRole> listQuery(@Param("tenantId") String tenantId
            , @Param("operator") Long operator
            , @Param("root") String root
            , @Param("admin") String admin
            , @Param("params") Map<String, Object> params
            , @Param("offset") Long offset
            , @Param("size") Long size
            , @Param("pageOrder") String pageOrder
            , @Param("dto") SysRoleQueryDTO dto
            , @Param("authorities") Set<String> authorities
            , @Param("isRoot") Integer isRoot
            , @Param("isAdmin") Integer isAdmin);
}
