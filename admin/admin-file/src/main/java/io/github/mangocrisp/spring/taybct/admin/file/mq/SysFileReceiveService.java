package io.github.mangocrisp.spring.taybct.admin.file.mq;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.admin.file.service.ISysFileService;
import io.github.mangocrisp.spring.taybct.common.message.sysfile.SysFileMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 接收消息
 *
 * @author xijieyin
 * @since 2024/9/1 21:36
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(RabbitTemplate.class)
@RequiredArgsConstructor
public class SysFileReceiveService {

    private final ISysFileService sysFileService;

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
            exchange = @Exchange(value = SysFileMQConfig.def.EXCHANGE, type = "x-delayed-message"),
            value = @Queue(value = SysFileMQConfig.def.QUEUE)
    ), ackMode = "MANUAL")
    public void apiLogResults(Message message, Channel channel) throws IOException {

        String json = new String(message.getBody(), StandardCharsets.UTF_8);

        // 这里存入到数据库去
        try {
            sysFileService.link(JSONArray.parseArray(json).toJavaList(SysFile.class));
        } catch (Exception e) {
            log.trace("日志保存失败！", e);
            log.trace("接收到的日志不是集合?");
        }
        try {
            sysFileService.link(Collections.singletonList(JSONObject.parseObject(json, SysFile.class)));
        } catch (Exception e) {
            log.trace("日志保存失败！", e);
            log.trace("接收到的日志不是对象?");
        }

        log.debug("\r\n==== 接收到 api 日志消息 \r\n==== {}", json);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

}
