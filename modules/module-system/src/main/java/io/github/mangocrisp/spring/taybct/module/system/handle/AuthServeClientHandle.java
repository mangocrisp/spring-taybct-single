package io.github.mangocrisp.spring.taybct.module.system.handle;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;

/**
 * 鉴权客户端处理类
 *
 * @author xijieyin <br> 2023/3/7 下午8:51
 */
public interface AuthServeClientHandle {

    /**
     * 保存客户端信息
     *
     * @param client 客户端信息
     * @return 是否保存成功
     */
    boolean save(SysOauth2Client client);

}
