package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysParams;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysParamsController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysParamsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统参数相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:31
 * @see SysParams
 * @see ISysParamsService
 * @since 1.0.0
 */
public class SysParamsControllerRegister implements ISysParamsController {

    @Autowired(required = false)
    protected ISysParamsService sysParamsService;

    @Override
    public ISysParamsService getBaseService() {
        return sysParamsService;
    }
}
