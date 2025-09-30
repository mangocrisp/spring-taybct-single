package io.github.mangocrisp.spring.taybct.module.system.auto;

import cn.hutool.core.util.ObjectUtil;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.config.IForceAllClientUserByRole;
import io.github.mangocrisp.spring.taybct.module.system.config.ILoginCacheClear;
import io.github.mangocrisp.spring.taybct.module.system.server.WebSocketServer;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserService;
import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSACoder;
import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSAProperties;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.endpoint.IWebSocketServer;
import jakarta.websocket.Session;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 系统模块自动配置
 *
 * @author XiJieYin <br> 2023/6/19 14:33
 */
@AutoConfiguration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class SystemAutoConfig {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    @ConditionalOnMissingBean
    public ILoginCacheClear loginCacheClear(RedisTemplate<Object, Object> redisTemplate) {
        return entityList -> redisTemplate.delete(entityList.stream()
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

    @Bean
    @ConditionalOnMissingBean
    public IForceAllClientUserByRole forceAllClientUserByRole() {
        // 这里默认不允许，因为角色关联的用户可能太多了
        return (s, longs) -> {
        };
    }

    @Bean
    @ConditionalOnMissingBean(KeyPair.class)
    public KeyPair keyPair(@Nullable RSAProperties properties) {
        if (ObjectUtil.isNotEmpty(properties)) {
            RSACoder.ini(properties);
            if (properties.getType().containsKey("JWT")) {
                return RSACoder.keyPair("JWT");
            }
        }
        properties = new RSAProperties();
        properties.setResource("jwt.jks");
        properties.setAlias("jwt");
        properties.setPassword("taybct");
        properties.setExpireCheck(true);
        return RSACoder.newKeyPair(properties);
    }

    @Bean
    public WebSocketServer webSocketServer(IWebSocketServer<Session> webSocketServer,
                                           ISysUserOnlineService sysUserOnlineService,
                                           ISysUserService sysUserService,
                                           KeyPair keyPair) {
        WebSocketServer.sysUserOnlineService = sysUserOnlineService;
        WebSocketServer.keyPair = keyPair;
        WebSocketServer.sysUserFunction = sysUserService::getById;
        return (WebSocketServer) webSocketServer;
    }

}
