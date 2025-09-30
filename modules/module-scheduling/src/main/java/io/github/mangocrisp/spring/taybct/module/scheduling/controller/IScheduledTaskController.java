package io.github.mangocrisp.spring.taybct.module.scheduling.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledLog;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledTask;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledTaskService;
import io.github.mangocrisp.spring.taybct.module.scheduling.vo.ScheduledTaskVO;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 任务调度控制器
 *
 * @author xijieyin <br> 2022/11/1 17:48
 * @since 1.1.0
 */
@Tag(name = "任务调度相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SCHEDULING + "{version}/scheduling")
@ApiVersion
public interface IScheduledTaskController extends BaseController<ScheduledTask, IScheduledTaskService> {

    @Operation(summary = "获取分页(带启动状态)")
    @GetMapping("task/page")
    @WebLog
    default R<IPage<ScheduledTaskVO>> taskPage(@RequestParam(required = false) Map<String, Object> sqlQueryParams) {
        return R.data(getBaseService().taskPage(sqlQueryParams));
    }

    @Operation(summary = "尝试执行一次")
    @PostMapping("tryOnce/{key}")
    @WebLog
    R<?> tryOnce(@PathVariable String key, @RequestBody Map<String, Object> params);

    /**
     * 根据任务 key 启动任务
     */
    @Operation(summary = "根据任务 key 启动任务")
    @PutMapping("/start")
    @Parameters({
            @Parameter(name = "keySet", description = "任务 key", required = true, in = ParameterIn.QUERY)
    })
    R<?> start(@RequestParam Set<String> keySet);

    /**
     * 根据任务 key 停止任务
     */
    @Operation(summary = "根据任务 key 停止任务")
    @PutMapping("/stop")
    @Parameters({
            @Parameter(name = "keySet", description = "任务 key", required = true, in = ParameterIn.QUERY)
    })
    R<?> stop(@RequestParam Set<String> keySet);

    /**
     * 根据任务 key 重启任务
     */
    @Operation(summary = "根据任务 key 重启任务")
    @PutMapping("/restart")
    @Parameters({
            @Parameter(name = "keySet", description = "任务 key", required = true, in = ParameterIn.QUERY)
    })
    R<?> restart(@RequestParam Set<String> keySet);

    @Operation(summary = "获取日志分页")
    @GetMapping("log/page")
    @WebLog
    R<IPage<ScheduledLog>> logPage(@RequestParam(required = false) Map<String, Object> sqlQueryParams);

    @Operation(summary = "清空日志记录")
    @DeleteMapping("log/clear")
    @WebLog
    @ApiLog(title = "清空日志记录", description = "清空日志记录", type = OperateType.DELETE)
    R<?> logClear();

}
