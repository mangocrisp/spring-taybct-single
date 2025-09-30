package io.github.mangocrisp.spring.taybct.module.system.auto;

import io.github.mangocrisp.spring.taybct.module.system.service.*;
import io.github.mangocrisp.spring.taybct.module.system.service.impl.*;
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
public class SystemServiceAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public ISysDeptService sysDeptService() {
        return new SysDeptServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysDictService sysDictService() {
        return new SysDictServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysDictTypeService sysDictTypeService() {
        return new SysDictTypeServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysMenuService sysMenuService() {

        return new SysMenuServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysNoticeService sysNoticeService() {

        return new SysNoticeServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysNoticeUserService sysNoticeUserService() {
        return new SysNoticeUserServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysOauth2ClientService sysOauth2ClientService() {
        return new SysOauth2ClientServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysParamsService sysParamsService() {
        return new SysParamsServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysPermissionGroupService sysPermissionGroupService() {
        return new SysPermissionGroupServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysPermissionService sysPermissionService() {
        return new SysPermissionServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysRoleDeptService sysRoleDeptService() {
        return new SysRoleDeptServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysRoleMenuService sysRoleMenuService() {
        return new SysRoleMenuServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysRolePermissionService sysRolePermissionService() {
        return new SysRolePermissionServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysRoleService sysRoleService() {
        return new SysRoleServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysTenantService sysTenantService() {
        return new SysTenantServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysUserDeptService sysUserDeptService() {
        return new SysUserDeptServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysUserOnlineService sysUserOnlineService() {

        return new SysUserOnlineServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysUserRoleService sysUserRoleService() {
        return new SysUserRoleServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysUserService sysUserService() {
        return new SysUserServiceImpl() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ISysUserTenantService sysUserTenantService() {
        return new SysUserTenantServiceImpl() {
        };
    }


}
