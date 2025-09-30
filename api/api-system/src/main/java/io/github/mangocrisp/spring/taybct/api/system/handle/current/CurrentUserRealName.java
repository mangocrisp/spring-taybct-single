package io.github.mangocrisp.spring.taybct.api.system.handle.current;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.config.TableFieldDefaultHandler;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * <pre>
 * 当前登录用户的真实姓名
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 00:27
 */
@RequiredArgsConstructor
public class CurrentUserRealName implements TableFieldDefaultHandler<String> {

    final SysUserMapper sysUserMapper;

    final ISecurityUtil securityUtil;

    @Override
    public String get(Object entity) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getId, SysUser::getRealName, SysUser::getNickname)
                .eq(SysUser::getId, loginUser.getUserId()));
        return Optional.ofNullable(sysUser.getRealName()).orElse(sysUser.getNickname());
    }

}
