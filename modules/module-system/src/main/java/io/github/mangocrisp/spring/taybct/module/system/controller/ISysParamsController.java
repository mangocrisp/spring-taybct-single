package io.github.mangocrisp.spring.taybct.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysParams;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysParamsVO;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysParamsService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.SafeConvert;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.enums.EntityType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 系统参数相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:31
 * @see SysParams
 * @see ISysParamsService
 * @since 1.0.0
 */
@Tag(name = "系统参数相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/params")
@ApiVersion
public interface ISysParamsController extends BaseController<SysParams, ISysParamsService> {

    @WebLog
    @Override
    @SafeConvert(safeOut = SysParamsVO.class, resultType = EntityType.Page)
    default R<IPage<? extends SysParams>> page(@RequestParam(required = false) Map<String, Object> params) {
        return BaseController.super.page(params);
    }

    /**
     * 获取系统参数(多)
     *
     * @param paramsKey 参数键
     * @return R
     * @author xijieyin <br> 2022/8/5 21:31
     * @since 1.0.0
     */
    @Operation(summary = "获取系统参数(多)")
    @PostMapping("cache")
    @Parameters({
            @Parameter(name = "paramsKey", description = "参数键 [1,2,3...n]", required = true, in = ParameterIn.DEFAULT, example = "[1,2,3]")
    })
    default R<?> cache(@NotNull @RequestBody String[] paramsKey) {
        Map<String, String> paramsGroup = new LinkedHashMap<>();
        Arrays.stream(paramsKey).forEach(key -> paramsGroup.put(key, getBaseService().cache(key)));
        return R.data(paramsGroup);
    }

    /**
     * 获取系统参数(单)
     *
     * @param paramsKey 参数键
     * @return R
     * @author xijieyin <br> 2022/8/5 21:31
     * @since 1.0.0
     */
    @Operation(summary = "获取系统参数(单)")
    @Parameters({
            @Parameter(name = "paramsKey", description = "参数键", required = true, in = ParameterIn.PATH)
    })
    @GetMapping("cache/{paramsKey}")
    default R<?> cache(@NotNull @PathVariable String paramsKey) {
        return R.data(getBaseService().cache(paramsKey));
    }

    /**
     * 清除缓存
     *
     * @param paramsKeySet 需要删除的参数 key
     * @return 是否删除成功
     */
    @Operation(summary = "清除缓存")
    @DeleteMapping("cache")
    default R<?> cleanCache(@Nullable @RequestBody Set<String> paramsKeySet) {
        return R.data(getBaseService().cleanCache(paramsKeySet));
    }
}
