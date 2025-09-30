package io.github.mangocrisp.spring.taybct.module.scheduling.mq;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import io.github.mangocrisp.spring.taybct.common.message.cheduledlog.ScheduledLogMQConfig;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * 接收任务调度日志
 * </pre>
 *
 * @author xijieyin
 * @since 2024/9/1 03:51
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(RabbitTemplate.class)
@RequiredArgsConstructor
public class SchedulingReceiveService {

    private final IScheduledLogService scheduledLogService;

    /**
     * 从 mq 读取消息
     *
     * @param message 消息体
     * @param channel 通道
     * @author xijieyin
     * @since 2024/9/1 03:51
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = ScheduledLogMQConfig.def.EXCHANGE, type = "x-delayed-message"),
            value = @Queue(value = ScheduledLogMQConfig.def.QUEUE)
    ), ackMode = "MANUAL")
    public void apiLogResults(Message message, Channel channel) throws IOException {
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        // 这里存入到数据库去
        try {
            JSONArray jsonArray = JSONArray.parseArray(json);
            for (int i = 0; i < jsonArray.size(); i++) {
                scheduledLogService.logRecorder(jsonArray.getJSONObject(i));
            }
        } catch (Exception e) {
            log.trace("日志保存失败！", e);
            log.trace("接收到的日志不是集合?");
        }
        try {
            scheduledLogService.logRecorder(JSONObject.parseObject(json));
        } catch (Exception e) {
            log.trace("日志保存失败！", e);
            log.trace("接收到的日志不是对象?");
        }
        log.debug("\r\n==== 接收到任务调度日志消息 \r\n==== {}", json);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

}
