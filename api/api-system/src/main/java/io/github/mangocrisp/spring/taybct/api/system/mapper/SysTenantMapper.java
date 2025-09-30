package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysTenant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 24154
 * @see SysTenant
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {
    /**
     * 根据用户 id 查询用户关联的租户
     *
     * @param userId          用户 id
     * @param defaultTenantId 默认租户 id ，如果不是超级管理员就不查询默认租户供选择，只有超级用户才能查询出默认租户来
     * @param isRoot          是否是超级管理员
     * @return {@code List<SysTenant>}
     * @author xijieyin <br> 2022/8/17 10:51
     * @since 1.0.1
     */
    List<SysTenant> listUserTenant(@Param("userId") Long userId, @Param("defaultTenantId") String defaultTenantId, @Param("isRoot") Integer isRoot);
}




