package io.github.mangocrisp.spring.taybct.admin.log.mq;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.github.mangocrisp.spring.taybct.admin.log.service.IApiLogService;
import io.github.mangocrisp.spring.taybct.tool.core.message.apilog.ApiLogSendMQConfig;
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
 * 接收写日志
 *
 * @author xijieyin
 * @since 2024/9/1 21:36
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(RabbitTemplate.class)
@RequiredArgsConstructor
public class ApiLogReceiveService {

    private final IApiLogService apiLogService;

    /**
     * 从 mq 读取消息
     *
     * @param message 消息体
     * @param channel 通道
     * @author xijieyin
     * @since 2024/9/1 21:36
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = ApiLogSendMQConfig.def.EXCHANGE, type = "x-delayed-message"),
            value = @Queue(value = ApiLogSendMQConfig.def.QUEUE)
    ), ackMode = "MANUAL")
    public void apiLogResults(Message message, Channel channel) throws IOException {

        String json = new String(message.getBody(), StandardCharsets.UTF_8);

        // 这里存入到数据库去
        try {
            JSONArray jsonArray = JSONArray.parseArray(json);
            apiLogService.saveBatch(jsonArray.toJavaList(ApiLog.class));
        } catch (Exception e) {
            log.trace("保存失败！", e);
            log.trace("接收到的不是集合?");
        }
        try {
            ApiLog apiLog = JSONObject.parseObject(json, ApiLog.class);
            apiLogService.save(apiLog);
        } catch (Exception e) {
            log.trace("保存失败！", e);
            log.trace("接收到的不是对象?");
        }

        log.debug("\r\n==== 接收到MQ消息 \r\n==== {}", json);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

}
