package io.github.mangocrisp.spring.taybct.auth.security.handle.deft;

import cn.hutool.core.bean.BeanUtil;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IClientDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.prop.ClientConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 配置文件获取客户端
 *
 * @author xijieyin
 */
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesClientDetailsHandle implements IClientDetailsHandle {

    private ClientConfig clientConfig;

    @Override
    public SysOauth2Client getClientById(String clientId) {
        if (clientConfig.getClientId() != null && clientConfig.getClientId().equalsIgnoreCase(clientId)) {
            return BeanUtil.copyProperties(clientConfig, SysOauth2Client.class);
        } else {
            for (SysOauth2Client client : clientConfig.getClients()) {
                if (client.getClientId().equalsIgnoreCase(clientId)) {
                    return BeanUtil.copyProperties(client, SysOauth2Client.class);
                }
            }
        }
        return null;
    }

}
