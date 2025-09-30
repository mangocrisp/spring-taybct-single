package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDictType;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysDictTypeController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统字典类型相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:28
 * @see SysDictType
 * @see ISysDictTypeService
 * @since 1.0.0
 */
public class SysDictTypeControllerRegister implements ISysDictTypeController {

    @Autowired(required = false)
    ISysDictTypeService sysDictTypeService;

    @Override
    public ISysDictTypeService getBaseService() {
        return sysDictTypeService;
    }
}
