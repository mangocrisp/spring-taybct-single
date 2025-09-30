package io.github.mangocrisp.spring.taybct.common.message.sysfile;

import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.core.message.MessageProperties;
import io.github.mangocrisp.spring.taybct.tool.core.mq.BindingEQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbit mq 自动创建队列配置
 *
 * @author xijieyin <br> 2022/8/5 20:17
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnClass(RabbitTemplate.class)
@Slf4j
public class SysFileMQConfig {

    /**
     * exchange 和 queue 的 key
     */
    public interface def {
        /**
         * 键
         */
        String KEY = "SYS_FILE_LINK";
        /**
         * 交换机
         */
        String EXCHANGE = BindingEQ.def.prefix + "." + KEY + "." + BindingEQ.def.exchange;
        /**
         * 队列
         */
        String QUEUE = BindingEQ.def.prefix + "." + KEY + "." + BindingEQ.def.queue;
    }

    /**
     * <pre>
     * 配置队列
     * </pre>
     *
     * @return Queue
     * @author xijieyin
     * @since 2024/9/1 23:46
     */
    @Bean("sysFileLinkQueue")
    public Queue sysFileLinkQueue() {
        //属性参数 队列名称 是否持久化
        return new Queue(def.QUEUE, true);
    }

    /**
     * <pre>
     * 配置队列交换机
     * </pre>
     *
     * @return CustomExchange
     * @author xijieyin
     * @since 2024/9/1 23:47
     */
    @Bean("sysFileLinkCustomExchange")
    public CustomExchange sysFileLinkCustomExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //属性参数 交换机名称 交换机类型 是否持久化 是否自动删除 配置参数
        return new CustomExchange(def.EXCHANGE, "x-delayed-message", true, false, args);
    }

    /**
     * <pre>
     * 队列绑定交换机
     * </pre>
     *
     * @param sysFileLinkCustomExchange 交换机
     * @param sysFileLinkQueue          队列
     * @return Binding
     * @author xijieyin
     * @since 2024/9/1 23:47
     */
    @Bean("sysFileLinkBinding")
    public Binding sysFileLinkBinding(@Qualifier("sysFileLinkCustomExchange") CustomExchange sysFileLinkCustomExchange
            , @Qualifier("sysFileLinkQueue") Queue sysFileLinkQueue) {
        return BindingBuilder.bind(sysFileLinkQueue)
                .to(sysFileLinkCustomExchange)
                .with(BindingEQ.getRoutingKey(def.KEY)).noargs();
    }

    /**
     * <pre>
     * 配置消息发送处理器
     * </pre>
     *
     * @param messageProperties 配置
     * @param rabbitTemplate    rabbit mq
     * @return SysFileLinkSendMQHandler
     * @author xijieyin
     * @since 2024/9/1 23:57
     */
    @Bean("sysFileLinkSendMQHandler")
    @ConditionalOnMissingBean(SysFileLinkSendMQHandler.class)
    public SysFileLinkSendMQHandler sysFileLinkSendMQHandler(MessageProperties messageProperties
            , RabbitTemplate rabbitTemplate
            , @Qualifier("sysFileLinkCustomExchange") CustomExchange sysFileLinkCustomExchange
            , @Qualifier("sysFileLinkQueue") Queue sysFileLinkQueue
            , @Qualifier("sysFileLinkBinding") Binding sysFileLinkBinding
            , @Nullable IMessageSendService messageSendService
            , @Nullable AmqpAdmin amqpAdmin) {
        log.info("\r\n==== 文件关联配置将通过 Rabbit MQ 发送到消费服务器！ \r\n====");
        SysFileLinkSendMQHandler sysFileLinkSendMQHandler = new SysFileLinkSendMQHandler(messageProperties, rabbitTemplate);
        if (messageSendService != null) {
            messageSendService.addHandler(sysFileLinkSendMQHandler);
        }
        if (amqpAdmin != null) {
            amqpAdmin.declareExchange(sysFileLinkCustomExchange);
            amqpAdmin.declareQueue(sysFileLinkQueue);
            amqpAdmin.declareBinding(sysFileLinkBinding);
        }
        return sysFileLinkSendMQHandler;
    }
}
