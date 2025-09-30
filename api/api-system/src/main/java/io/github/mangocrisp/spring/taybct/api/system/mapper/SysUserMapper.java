package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysUserQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.vo.UserInfoVO;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceMethod;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xijieyin
 * @see SysUser
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据字段/值获取登录用户信息
     *
     * @param field 字段名
     * @param value 字段值
     * @return OAuth2UserDTO
     * @author xijieyin <br> 2022/8/5 21:54
     * @since 1.0.0
     */
    @InterceptorIgnore(tenantLine = "true")
    @EnhanceMethod
    OAuth2UserDTO getUserByFiled(@Param("field") String field, @Param("value") String value);

    /**
     * 根据用户 id 获取用户信息
     *
     * @param userId 用户 id
     * @return UserInfoVO
     */
    @EnhanceMethod
    UserInfoVO getUserInfoByUserId(@Param("userId") Long userId);

    /**
     * 添加微信用户
     *
     * @param user 用户信息
     * @return int
     */
    @InterceptorIgnore(tenantLine = "true")
    int addWechatUser(@Param("user") Map<String, Object> user);

    /**
     * 先查询个数，按条件查询个数，如果有结果，再查询列表，或者分页
     *
     * @param tenantId 租户id
     * @param operator 操作者的 id 为了过滤当前用户
     * @param root     超级管理员的用户 id ，这个是默认的
     * @param params   查询参数
     * @return int 个数
     * @author xijieyin <br> 2022/9/28 11:47
     * @since 1.0.4
     */
    long countQuery(@Param("tenantId") String tenantId
            , @Param("operator") Long operator
            , @Param("root") Long root
            , @Param("params") Map<String, Object> params
            , @Param("dto") SysUserQueryDTO dto
            , @Param("authorities") Set<String> authorities
            , @Param("isRoot") Integer isRoot);

    /**
     * 条件查询
     *
     * @param tenantId  租户id
     * @param operator  操作者的 id 为了过滤当前用户
     * @param root      超级管理员的用户 id ，这个是默认的
     * @param params    查询参数
     * @param offset    查询起始位置
     * @param size      大小
     * @param pageOrder 排序字段
     * @return {@code List<SysUser>}
     * @author xijieyin <br> 2022/9/28 11:49
     * @since 1.0.4
     */
    List<SysUser> listQuery(@Param("tenantId") String tenantId
            , @Param("operator") Long operator
            , @Param("root") Long root
            , @Param("params") Map<String, Object> params
            , @Param("offset") Long offset
            , @Param("size") Long size
            , @Param("pageOrder") String pageOrder
            , @Param("dto") SysUserQueryDTO dto
            , @Param("authorities") Set<String> authorities
            , @Param("isRoot") Integer isRoot);

    @Override
    @EnhanceMethod
    SysUser selectById(Serializable id);

    @Override
    @EnhanceMethod
    int insert(SysUser entity);

    @Override
    @EnhanceMethod
    int updateById(@Param(Constants.ENTITY) SysUser entity);

    @Override
    @EnhanceMethod
    int update(@Param(Constants.ENTITY) SysUser entity, @Param(Constants.WRAPPER) Wrapper<SysUser> updateWrapper);

    @Override
    @EnhanceMethod
    default int update(@Param(Constants.WRAPPER) Wrapper<SysUser> updateWrapper) {
        return BaseMapper.super.update(updateWrapper);
    }
}
