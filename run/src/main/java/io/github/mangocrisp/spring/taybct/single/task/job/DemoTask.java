package io.github.mangocrisp.spring.taybct.single.task.job;

import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledLogService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.Scheduler;
import io.github.mangocrisp.spring.taybct.tool.scheduling.job.AbstractScheduledTaskJob;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 测试任务
 *
 * @author xijieyin <br> 2022/11/1 11:37
 * @since 1.1.0
 */
@AutoConfiguration
@RequiredArgsConstructor
@Slf4j
@Scheduler("demo")
public class DemoTask extends AbstractScheduledTaskJob {

    final IScheduledLogService scheduledLogService;

    @Override
    protected Consumer<JSONObject> getLogRecorder() {
        return scheduledLogService::logRecorder;
    }

    @Override
    public void run(Map<String, Object> params) throws Exception {
        log.info("demo task => 当前线程名称 {} ", Thread.currentThread().getName());
        params.forEach((k, v) -> log.info("{} >>>> {}", k, v));
        log.info(">>>>>> 测试任务开始 >>>>>> ");
//        stopRecord(OperateStatus.SUCCESS.getCode(), "我自己记录一个消息");
//        throw new RuntimeException("最后个报错");
    }

}
