package io.github.mangocrisp.spring.taybct.module.lf.auto;

import io.github.mangocrisp.spring.taybct.module.lf.service.*;
import io.github.mangocrisp.spring.taybct.module.lf.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Service 自动配置
 *
 * @author XiJieYin <br> 2023/7/24 11:38
 */
@AutoConfiguration
@Slf4j
public class LfServiceAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public IDesignPermissionsService designPermissionsService() {
        return new DesignPermissionsServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IDesignService designService() {
        return new DesignServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IEdgesService edgesService() {
        return new EdgesServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IFormReleaseService formReleaseService() {
        return new FormReleaseServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IFormService formService() {
        return new FormServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ILfHistoryService lfHistoryService() {
        return new LfHistoryServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public INodesService nodesService() {
        return new NodesServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IPresentProcessService presentProcessService() {
        return new PresentProcessServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IProcessService processService() {
        return new ProcessServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IReleasePermissionsService releasePermissionsService() {
        return new ReleasePermissionsServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IReleaseService releaseService() {
        return new ReleaseServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ITodoService todoService() {
        return new TodoServiceImpl() {
        };
    }

}
