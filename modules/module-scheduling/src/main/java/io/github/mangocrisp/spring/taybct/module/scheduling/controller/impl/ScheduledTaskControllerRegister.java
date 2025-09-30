package io.github.mangocrisp.spring.taybct.module.scheduling.controller.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.module.scheduling.controller.IScheduledTaskController;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledLog;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledLogService;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledTaskService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.scheduling.service.ISchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * @author XiJieYin <br> 2023/7/26 11:14
 */
public class ScheduledTaskControllerRegister implements IScheduledTaskController {

    @Autowired(required = false)
    protected IScheduledLogService scheduledLogService;

    @Autowired(required = false)
    protected ISchedulingService schedulingService;

    @Autowired(required = false)
    protected IScheduledTaskService scheduledTaskService;

    public IScheduledLogService getScheduledLogService() {
        return scheduledLogService;
    }

    public ISchedulingService getSchedulingService() {
        return schedulingService;
    }

    @Override
    public IScheduledTaskService getBaseService() {
        return scheduledTaskService;
    }


    @Override
    public R<?> tryOnce(String key, Map<String, Object> params) {
        return R.data(schedulingService.tryOnce(key, params));
    }

    /**
     * 根据任务 key 启动任务
     */
    @Override
    public R<?> start(@RequestParam Set<String> keySet) {
        keySet.forEach(getSchedulingService()::start);
        return R.ok("任务启动指令已发送");
    }

    /**
     * 根据任务 key 停止任务
     */
    @Override
    public R<?> stop(@RequestParam Set<String> keySet) {
        keySet.forEach(getSchedulingService()::stop);
        return R.ok("任务停止指令已发送");
    }

    /**
     * 根据任务 key 重启任务
     */
    @Override
    public R<?> restart(@RequestParam Set<String> keySet) {
        keySet.forEach(getSchedulingService()::restart);
        return R.ok("任务重启指令已发送");
    }

    @WebLog
    @Override
    public R<IPage<ScheduledLog>> logPage(@RequestParam(required = false) Map<String, Object> sqlQueryParams) {
        return R.data(getScheduledLogService().page(sqlQueryParams));
    }

    @WebLog
    @ApiLog(title = "清空日志记录", description = "清空日志记录", type = OperateType.DELETE)
    @Override
    public R<?> logClear() {
        return getScheduledLogService().remove(Wrappers.lambdaQuery()) ? R.ok("清空日志记录成功") : R.fail("清空日志记录失败");
    }
}
