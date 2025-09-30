package io.github.mangocrisp.spring.taybct.admin.log.controller.impl;

import io.github.mangocrisp.spring.taybct.admin.log.controller.IApiLogController;
import io.github.mangocrisp.spring.taybct.admin.log.service.IApiLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author XiJieYin <br> 2023/7/25 16:21
 */
public class ApiLogControllerRegister implements IApiLogController {


    @Autowired(required = false)
    protected IApiLogService apiLogService;

    @Override
    public IApiLogService getBaseService() {
        return apiLogService;
    }
}
