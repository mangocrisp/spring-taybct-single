package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDictType;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictTypeService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 系统字典类型相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:28
 * @see SysDictType
 * @see ISysDictTypeService
 * @since 1.0.0
 */
@Tag(name = "系统字典类型相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/dictType")
@ApiVersion
public interface ISysDictTypeController extends BaseController<SysDictType, ISysDictTypeService> {
}
