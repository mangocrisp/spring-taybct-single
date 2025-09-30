package io.github.mangocrisp.spring.taybct.admin.file.job;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.admin.file.service.ISysFileService;
import io.github.mangocrisp.spring.taybct.common.message.cheduledlog.ScheduledLogDTO;
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
 * 清理无效的文件
 */
@Slf4j
@Scheduler("cleanNotLinkedFile")
public class CleanNotLinkedFile extends RedisScheduledTaskJob {

    ISysFileService sysFileService;

    public CleanNotLinkedFile(RedisTemplate<String, String> redisTemplate, Environment env) {
        super(redisTemplate, env);
    }

    @Resource
    private IMessageSendService messageSendService;

    @Override
    protected Consumer<JSONObject> getLogRecorder() {
        return json -> messageSendService.send(new ScheduledLogDTO(json));
    }

    @Override
    public void run(Map<String, Object> params) throws Exception {
        log.debug("cleanNotLinkedFile => 当前线程名称 {} ", Thread.currentThread().getName());
        log.debug(">>>>>> 清理无效的文件 开始 >>>>>> ");
        sysFileService.cleanNotLinkedFile(params);
        log.debug(">>>>>> 清理无效的文件 结束 >>>>>> ");
    }

}
