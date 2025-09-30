package io.github.mangocrisp.spring.taybct.auth.security.service;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.auth.security.util.RegisteredClientUtil;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.function.Function;

/**
 * The RegisteredClientRepository is the central component where new clients can be registered and existing clients can be queried.
 * It is used by other components when following a specific protocol flow, such as client authentication, authorization grant
 * processing, token introspection, dynamic client registration, and others.<br>
 * <p>
 * 可以注册新客户端和查询现有客户端的中心组件，
 *
 * @author xijieyin <br> 2022/12/28 9:07
 */
@AllArgsConstructor
@NoArgsConstructor
public class CustomizeRegisteredClientRepository implements RegisteredClientRepository {

    private Function<String, SysOauth2Client> clientDetailHandle;

    private Function<String, String> encoder = (password) -> password;

    @Override
    public void save(RegisteredClient registeredClient) {
        // 这个是注册客户端会用到
    }

    @Override
    public RegisteredClient findById(String id) {
        // 一般不会用到
        return null;
    }


    @Override
    @Cacheable(cacheNames = CacheConstants.OAuth.CLIENT, key = "#clientId")
    public RegisteredClient findByClientId(String clientId) {
        SysOauth2Client sysOauth2Client = clientDetailHandle.apply(clientId);
        if (sysOauth2Client == null) {
            return null;
        }
        // 这里要使用加密器加密一下密钥
        sysOauth2Client.setClientSecret(encoder.apply(sysOauth2Client.getClientSecret()));
        return RegisteredClientUtil.generateCommonClient(sysOauth2Client);
    }
}
