package io.github.mangocrisp.spring.taybct.module.scheduling.auto;

import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledLogService;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledTaskService;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.impl.ScheduledLogServiceImpl;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.impl.ScheduledTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Service 自动配置
 *
 * @author XiJieYin <br> 2023/7/24 11:38
 */
@Configuration
@Slf4j
@AutoConfiguration
public class SchedulingServiceAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public IScheduledTaskService scheduledTaskService() {
        return new ScheduledTaskServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IScheduledLogService scheduledLogService() {
        return new ScheduledLogServiceImpl() {
        };
    }

}
