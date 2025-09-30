package io.github.mangocrisp.spring.taybct.auth.service;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;

/**
 * 注册客户端 Service
 *
 * @author xijieyin <br> 2023/3/7 下午8:29
 */
public interface IRegisteredService {
    /**
     * 保存客户端
     *
     * @param sysOauth2Client 客户端
     * @return 是否保存成功
     */
    boolean save(SysOauth2Client sysOauth2Client);

}
