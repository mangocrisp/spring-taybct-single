package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysDictController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统字典相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:26
 * @see SysDict
 * @see ISysDictService
 * @since 1.0.0
 */
public class SysDictControllerRegister implements ISysDictController {

    @Autowired(required = false)
    ISysDictService sysDictService;

    @Override
    public ISysDictService getBaseService() {
        return sysDictService;
    }
}
