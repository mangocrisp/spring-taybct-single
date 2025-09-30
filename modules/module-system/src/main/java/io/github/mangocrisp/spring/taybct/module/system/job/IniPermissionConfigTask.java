package io.github.mangocrisp.spring.taybct.module.system.job;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.common.message.cheduledlog.ScheduledLogDTO;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRolePermissionService;
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
 * 初始化权限配置任务
 *
 * @author xijieyin <br> 2022/11/1 11:39
 * @since 1.1.0
 */
@Slf4j
@Scheduler("iniPermissionConfig")
public class IniPermissionConfigTask extends RedisScheduledTaskJob {

    final ISysRolePermissionService sysRolePermissionService;

    @Resource
    private IMessageSendService messageSendService;

    @Override
    protected Consumer<JSONObject> getLogRecorder() {
        return json -> messageSendService.send(new ScheduledLogDTO(json));
    }

    public IniPermissionConfigTask(RedisTemplate<String, String> redisTemplate
            , Environment env
            , ISysRolePermissionService sysRolePermissionService) {
        super(redisTemplate, env);
        this.sysRolePermissionService = sysRolePermissionService;
    }

    @Override
    public void run(Map<String, Object> params) {
        log.debug("iniPermissionConfig => 当前线程名称 {} ", Thread.currentThread().getName());
        log.debug(">>>>>> 初始化权限配置任务开始 >>>>>> ");
        sysRolePermissionService.iniConfig();
        log.debug(">>>>>>  初始化权限配置任务结束 >>>>>> ");
    }

}
