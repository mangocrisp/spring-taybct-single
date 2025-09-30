package io.github.mangocrisp.spring.taybct.auth.security.handle;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;

/**
 * 客户端处理，你可以实现这个接口，然后去从其他任何地方获取客户端信息，当前是直接从配置文件里获取客户端信息
 *
 * @author xijieyin <br> 2022/8/5 11:53
 * @since 1.0.0
 */
public interface IClientDetailsHandle {

    /**
     * 根据客户端 id 获取 客户端信息
     *
     * @param clientId 客户端 idn
     * @return SysOauth2Client
     * @author xijieyin <br> 2022/8/5 11:53
     * @since 1.0.0
     */
    default SysOauth2Client getClientById(String clientId) {
        return null;
    }

}
