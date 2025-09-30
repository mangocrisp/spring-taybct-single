package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.github.mangocrisp.spring.taybct.api.system.vo.PermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xijieyin
 * @see SysPermission
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 查询列表 VO
     *
     * @param params 请求参数
     * @return {@code List<PermissionVO>}
     * @author xijieyin <br> 2022/9/21 9:58
     * @since 1.0.4
     */
    List<PermissionVO> selectListVO(@Param("params") Map<String, Object> params);

    /**
     * 分页查询权限
     *
     * @param page   分页信息
     * @param params 请求参数
     * @return {@code IPage<PermissionVO>}
     */
    IPage<PermissionVO> selectPageVo(IPage<?> page, @Param("params") Map<String, Object> params);

    /**
     * 获取所有的权限及该权限下的所有的角色
     *
     * @return {@code List<PermissionVO>}
     */
    @InterceptorIgnore(tenantLine = "true")
    List<PermissionVO> loadPermissionRoles();

}
