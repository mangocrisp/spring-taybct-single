package io.github.mangocrisp.spring.taybct.common.message.cheduledlog;

import io.github.mangocrisp.spring.taybct.common.constants.TypeConstant;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendHandler;
import io.github.mangocrisp.spring.taybct.tool.core.message.MessageProperties;
import io.github.mangocrisp.spring.taybct.tool.core.message.MessageType;
import io.github.mangocrisp.spring.taybct.tool.core.mq.BindingEQ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * <pre>
 * 任务调度日志用 mq 发送配置
 * </pre>
 *
 * @author xijieyin
 * @since 2024/9/1 01:34
 */
@Slf4j
@RequiredArgsConstructor
public class ScheduledLogSendMQHandler implements IMessageSendHandler {

    final MessageProperties messageProperties;

    final RabbitTemplate rabbitTemplate;

    @Override
    public MessageType getMessageType() {
        return TypeConstant.SCHEDULED_LOG;
    }

    @Override
    public boolean send(String message) {
        log.debug("\r\n==== 任务调度日志发送延迟队列 \r\n==== {}", message);
        rabbitTemplate.convertAndSend(ScheduledLogMQConfig.def.EXCHANGE,
                BindingEQ.getRoutingKey(ScheduledLogMQConfig.def.KEY), message, m -> {
                    m.getMessageProperties().setHeader("x-delay", messageProperties.getDelay());
                    return m;
                });
        return true;
    }
}
