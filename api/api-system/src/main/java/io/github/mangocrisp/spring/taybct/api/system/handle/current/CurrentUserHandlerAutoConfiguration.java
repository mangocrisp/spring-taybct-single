package io.github.mangocrisp.spring.taybct.api.system.handle.current;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserDept;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.handle.MyBatisExtraParamsHandle;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.interceptor.MyBatisExtraParamsInterceptor;
import io.github.mangocrisp.spring.taybct.tool.security.config.TokenSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * <pre>
 * 当前用户处理器自动注册
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/20 00:56
 */
@Slf4j
@ConditionalOnClass({TokenSecurityConfig.class, MybatisPlusAutoConfiguration.class})
@AutoConfiguration(after = {MybatisPlusAutoConfiguration.class, TokenSecurityConfig.class})
public class CurrentUserHandlerAutoConfiguration {

    @Bean
    @ConditionalOnBean(ISecurityUtil.class)
    @ConditionalOnMissingBean(CurrentUserId.class)
    public CurrentUserId currentUserId(@Nullable ISecurityUtil securityUtil) {
        return new CurrentUserId(securityUtil);
    }

    @Bean
    @ConditionalOnBean({ISecurityUtil.class})
    @ConditionalOnMissingBean(CurrentUserRealName.class)
    public CurrentUserRealName currentUserRealName(SysUserMapper sysUserMapper, ISecurityUtil securityUtil) {
        return new CurrentUserRealName(sysUserMapper, securityUtil);
    }

    @Bean
    @ConditionalOnBean({ISecurityUtil.class})
    @ConditionalOnMissingBean(CurrentUserDeptId.class)
    public CurrentUserDeptId currentUserDeptId(SysUserDeptMapper sysUserDeptMapper, ISecurityUtil securityUtil) {
        return new CurrentUserDeptId(sysUserDeptMapper, securityUtil);
    }

    @Bean
    @ConditionalOnBean({ISecurityUtil.class})
    @ConditionalOnMissingBean(CurrentUserDeptName.class)
    public CurrentUserDeptName currentUserDeptName(SysUserDeptMapper sysUserDeptMapper, SysDeptMapper sysDeptMapper, ISecurityUtil securityUtil) {
        return new CurrentUserDeptName(sysUserDeptMapper, sysDeptMapper, securityUtil);
    }

    @Bean
    @ConditionalOnBean({ISecurityUtil.class})
    @ConditionalOnMissingBean(CurrentUserRealPhone.class)
    public CurrentUserRealPhone currentUserRealPhone(SysUserMapper sysUserMapper, ISecurityUtil securityUtil) {
        return new CurrentUserRealPhone(sysUserMapper, securityUtil);
    }

    @Bean
    @ConditionalOnBean({ISecurityUtil.class})
    @ConditionalOnMissingBean(LoginUserDeptHandler.class)
    public MyBatisExtraParamsHandle currentDeptMyBatisExtraParamsHandle(SysUserDeptMapper sysUserDeptMapper, SysDeptMapper sysDeptMapper, ISecurityUtil securityUtil, MyBatisExtraParamsInterceptor myBatisExtraParamsInterceptor) {
        MyBatisExtraParamsHandle loginUserDeptHandler = new LoginUserDeptHandler((() -> {
            SysUserDept sysUserDept = null;
            try {
                sysUserDept = sysUserDeptMapper.selectOne(Wrappers.<SysUserDept>lambdaQuery()
                        .eq(SysUserDept::getUserId, securityUtil.getLoginUser().getUserId())
                        // id 是雪花id，理论上是按照创建时间排序的，但是可能存在多个部门，所以这里取第一个，如果这样的方式不满足业务需求，请自行将实体类的字段填充不为空就行了
                        .orderByAsc(SysUserDept::getId)
                        .last("limit 1"));
            } catch (Exception e) {
                log.trace("获取当前用户部门信息失败");
                return null;
            }
            if (Objects.isNull(sysUserDept)) {
                return null;
            }
            SysDept sysDept = sysDeptMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                    .select(SysDept::getName, SysDept::getId)
                    .eq(SysDept::getId, sysUserDept.getDeptId()));
            if (Objects.isNull(sysDept)) {
                return null;
            }
            return sysDept;
        }));
        myBatisExtraParamsInterceptor.addHandler(loginUserDeptHandler);
        return loginUserDeptHandler;
    }
}
