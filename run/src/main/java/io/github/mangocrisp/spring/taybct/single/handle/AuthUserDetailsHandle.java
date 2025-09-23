package io.github.mangocrisp.spring.taybct.single.handle;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserOnline;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.CacheTimeOut;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xijieyin
 * @createTime 2022/4/21 23:06
 * @description
 */
@RequiredArgsConstructor
public class AuthUserDetailsHandle implements IUserDetailsHandle {

    final ISysUserService sysUserService;
    final ISysUserOnlineService sysUserOnlineService;

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 这里设置成 5 分钟的缓存，是因为 code 鉴权模式下的 code 管理时间是 5 分钟
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    @CacheTimeOut(cacheName = CacheConstants.OAuth.USERNAME, key = "#username", timeout = 5L, timeUnit = TimeUnit.MINUTES)
    public OAuth2UserDTO getUserByUsername(String username) {
        Object object = sysUserService.getUserByFiled("username", username);
        return object == null ? null : BeanUtil.copyProperties(object, OAuth2UserDTO.class);
    }

    @Override
    public boolean login(JSONObject dto) {
        return sysUserOnlineService.login(dto, true);
    }

    @Override
    public boolean logoff(JSONObject dto) {
        cachedThreadPool.execute(() -> sysUserOnlineService.logoff(dto, "用户已经登出！"));
        return true;
    }

    @Override
    public String getAccessTokenByJTI(String jti) {
        return Optional.ofNullable(sysUserOnlineService.getOne(Wrappers.<SysUserOnline>lambdaQuery()
                        .eq(SysUserOnline::getJti, jti)
                        .last("limit 1")))
                .map(SysUserOnline::getAccessTokenValue)
                .orElse(null);
    }
}
