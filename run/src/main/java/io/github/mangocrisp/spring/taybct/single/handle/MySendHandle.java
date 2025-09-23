package io.github.mangocrisp.spring.taybct.single.handle;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.admin.file.service.ISysFileService;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.github.mangocrisp.spring.taybct.admin.log.service.IApiLogService;
import io.github.mangocrisp.spring.taybct.common.message.cheduledlog.ScheduledLogDTO;
import io.github.mangocrisp.spring.taybct.common.message.sysfile.FileSendDTO;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledLogService;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.core.message.Message;
import io.github.mangocrisp.spring.taybct.tool.core.message.apilog.ApiLogDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xijieyin
 * @createTime 2022/7/27 11:06
 * @description
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnMissingClass({"org.springframework.amqp.rabbit.core.RabbitTemplate"})
public class MySendHandle implements IMessageSendService {

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    final IApiLogService apiLogService;
    final IScheduledLogService scheduledLogService;
    final ISysFileService sysFileService;

    @Override
    public void send(Message message) {
        cachedThreadPool.execute(() -> {
            // TODO，这里根据消息的类型来做对应的处理
            // 日志管理模块，自己就是管理模块，所以这里直接就写库了
            if (message instanceof ApiLogDTO) {
                ApiLog apiLog = JSONObject.parseObject(message.getPayload(), ApiLog.class);
                apiLogService.save(apiLog);
            }
            if (message instanceof ScheduledLogDTO) {
                scheduledLogService.logRecorder(JSONObject.parseObject(message.getPayload()));
            }
            if (message instanceof FileSendDTO) {
                sysFileService.link(JSONArray.parseArray(message.getPayload()).toJavaList(SysFile.class));
            }
        });
    }

}
