package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysOauth2ClientController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysOauth2ClientService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 客户端管理
 *
 * @author xijieyin <br> 2022/8/5 21:28
 * @see SysOauth2Client
 * @see ISysOauth2ClientService
 * @since 1.0.0
 */
public class SysOauth2ClientControllerRegister implements ISysOauth2ClientController {

    @Autowired(required = false)
    ISysOauth2ClientService sysOauth2ClientService;

    @Override
    public ISysOauth2ClientService getBaseService() {
        return sysOauth2ClientService;
    }
}
