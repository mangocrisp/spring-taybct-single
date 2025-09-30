package io.github.mangocrisp.spring.taybct.module.system.job;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.message.cheduledlog.ScheduledLogDTO;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysParamsService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.Scheduler;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import io.github.mangocrisp.spring.taybct.tool.scheduling.job.RedisScheduledTaskJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 初始化参数配置任务
 *
 * @author xijieyin <br> 2022/11/1 11:39
 * @since 1.1.0
 */
@Slf4j
@Scheduler("iniParams")
public class IniParamsTask extends RedisScheduledTaskJob {

    final ISysParamsService sysParamsService;
    final RedisTemplate<String, String> redisTemplate;
    final RedisTemplate<String, Object> objectRedisTemplate;

    @Resource
    private IMessageSendService messageSendService;

    @Override
    protected Consumer<JSONObject> getLogRecorder() {
        return json -> messageSendService.send(new ScheduledLogDTO(json));
    }

    public IniParamsTask(RedisTemplate<String, String> redisTemplate
            , Environment env
            , ISysParamsService sysParamsService
            , RedisTemplate<String, String> redisTemplate1, RedisTemplate<String, Object> objectRedisTemplate) {
        super(redisTemplate, env);
        this.sysParamsService = sysParamsService;
        this.redisTemplate = redisTemplate1;
        this.objectRedisTemplate = objectRedisTemplate;
    }

    @Override
    public void run(Map<String, Object> params) {
        log.debug("iniParams => 当前线程名称 {} ", Thread.currentThread().getName());
        log.debug(">>>>>> 初始化参数配置任务开始 >>>>>> ");
        sysParamsService.list().stream()
                .filter(sysParams -> StringUtil.isNotEmpty(sysParams.getParamsKey()))
                .forEach(sysParams -> objectRedisTemplate.opsForValue()
                        .set(String.format("%s::%s", CacheConstants.Params.PREFIX, sysParams.getParamsKey())
                                , sysParams.getParamsVal()));
        log.debug(">>>>>>  初始化参数配置任务结束 >>>>>> ");
    }

}
