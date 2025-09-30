package io.github.mangocrisp.spring.taybct.module.system.job;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.common.message.cheduledlog.ScheduledLogDTO;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.Scheduler;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.scheduling.job.RedisScheduledTaskJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 清理超时用户在线状态任务
 *
 * @author xijieyin <br> 2022/11/1 11:31
 * @since 1.1.0
 */
@Slf4j
@Scheduler("clearExpires")
public class ClearExpiresTask extends RedisScheduledTaskJob {

    final ISysUserOnlineService sysUserOnlineService;

    @Resource
    private IMessageSendService messageSendService;

    @Override
    protected Consumer<JSONObject> getLogRecorder() {
        return json -> messageSendService.send(new ScheduledLogDTO(json));
    }

    public ClearExpiresTask(RedisTemplate<String, String> redisTemplate
            , Environment env
            , ISysUserOnlineService sysUserOnlineService) {
        super(redisTemplate, env);
        this.sysUserOnlineService = sysUserOnlineService;
    }

    @Override
    public void run(Map<String, Object> params) {
        log.debug("clearExpires => 当前线程名称 {} ", Thread.currentThread().getName());
        log.debug(">>>>>> 清理检查在线用户状态开始 >>>>>> ");
        sysUserOnlineService.clearExpires();
        log.debug(">>>>>> 清理检查在线用户状态结束 >>>>>> ");
    }

}
