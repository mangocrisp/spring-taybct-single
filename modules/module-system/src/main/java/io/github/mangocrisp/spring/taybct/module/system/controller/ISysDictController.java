package io.github.mangocrisp.spring.taybct.module.system.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统字典相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:26
 * @see SysDict
 * @see ISysDictService
 * @since 1.0.0
 */
@Tag(name = "系统字典相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/dict")
@ApiVersion
public interface ISysDictController extends BaseController<SysDict, ISysDictService> {

    /**
     * 获取字典(多)
     *
     * @param dictCode 字段代码
     * @return R
     * @author xijieyin <br> 2022/8/5 21:26
     * @since 1.0.0
     */
    @Operation(summary = "获取字典(多)")
    @PostMapping("cache")
    @Parameters({
            @Parameter(name = "dictCode", description = "字典类型 [1,2,3...n]", required = true, in = ParameterIn.DEFAULT, example = "[1,2,3]")
    })
    default R<?> cache(@NotNull @RequestBody String[] dictCode) {
        Map<String, List<SysDict>> dictGroup = new LinkedHashMap<>();
        Arrays.stream(dictCode).forEach(code -> dictGroup.put(code, getBaseService().cache(code)));
        return R.data(dictGroup);
    }

    /**
     * 获取字典(单)
     *
     * @param dictCode 字典代码
     * @return R
     * @author xijieyin <br> 2022/8/5 21:27
     * @since 1.0.0
     */
    @Operation(summary = "获取字典(单)")
    @Parameters({
            @Parameter(name = "dictCode", description = "字典类型", required = true, in = ParameterIn.PATH)
    })
    @GetMapping("cache/{dictCode}")
    default R<?> cache(@NotNull @PathVariable String dictCode) {
        return R.data(getBaseService().cache(dictCode));
    }

    /**
     * 清除缓存
     *
     * @param dictCodes 需要删除的字典类型
     * @return 是否删除成功
     */
    @Operation(summary = "清除缓存")
    @DeleteMapping("cache")
    default R<?> cleanCache(@Nullable @RequestBody Set<String> dictCodes) {
        return R.data(getBaseService().cleanCache(dictCodes));
    }

}
