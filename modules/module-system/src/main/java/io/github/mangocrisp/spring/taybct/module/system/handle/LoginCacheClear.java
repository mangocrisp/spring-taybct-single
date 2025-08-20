package io.github.mangocrisp.spring.taybct.module.system.handle;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.config.ILoginCacheClear;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 清除用户缓存
 *
 * @author XiJieYin <br> 2023/6/19 15:19
 */
@AutoConfiguration
@RequiredArgsConstructor
public class LoginCacheClear implements ILoginCacheClear {

    final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void accept(Collection<SysUser> sysUsers) {
        redisTemplate.delete(sysUsers.stream()
                .map(user -> Arrays.asList(
                        //TODO 这里有多少种登录方式就得加多少种，用户名，包含了邮箱
                        String.format("%s::%s", CacheConstants.OAuth.USERNAME, user.getUsername()),
                        String.format("%s::%s", CacheConstants.OAuth.OPENID, user.getUsername()),
                        String.format("%s::%s", CacheConstants.OAuth.PHONE, user.getPhone()),
                        String.format("%s::%s", CacheConstants.OAuth.USERID, user.getId())
                ))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet()));
    }
}
