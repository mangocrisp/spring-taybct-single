package io.github.mangocrisp.spring.taybct.module.system.dubbo;

import io.github.mangocrisp.spring.taybct.api.system.feign.IPermClient;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRolePermissionService;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * @author xijieyin <br> 2023/1/6 11:27
 */
@DubboService(protocol = "dubbo")
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnClass(ConfigCenterConfig.class)
public class PermClientImpl implements IPermClient {

    final ISysRolePermissionService sysRolePermissionService;

    @Override
    public R<?> iniConfig() {
        // TODO 初始化角色权限配置后，可以配置角色有的权限，如果不配置权限有哪些角色，默认这个权限是开放的
        sysRolePermissionService.iniConfig();
        return R.ok();
    }
}
