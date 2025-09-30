package io.github.mangocrisp.spring.taybct.admin.log.auto;

import io.github.mangocrisp.spring.taybct.admin.log.service.IApiLogService;
import io.github.mangocrisp.spring.taybct.admin.log.service.impl.ApiLogServiceImpl;
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
public class ServiceAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public IApiLogService apiLogService() {
        return new ApiLogServiceImpl() {
        };
    }

}
