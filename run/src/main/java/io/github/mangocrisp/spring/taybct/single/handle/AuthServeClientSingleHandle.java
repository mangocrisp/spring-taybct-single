package io.github.mangocrisp.spring.taybct.single.handle;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.auth.service.IRegisteredService;
import io.github.mangocrisp.spring.taybct.module.system.handle.AuthServeClientHandle;
import lombok.RequiredArgsConstructor;

/**
 * 单机版处理注册客户端
 *
 * @author xijieyin <br> 2023/3/7 下午9:13
 */
@RequiredArgsConstructor
public class AuthServeClientSingleHandle implements AuthServeClientHandle {
    final IRegisteredService registeredService;

    @Override
    public boolean save(SysOauth2Client client) {
        return registeredService.save(client);
    }

}
