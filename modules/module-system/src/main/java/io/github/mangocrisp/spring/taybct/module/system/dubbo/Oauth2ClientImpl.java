package io.github.mangocrisp.spring.taybct.module.system.dubbo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2ClientDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.IOauth2Client;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysOauth2ClientService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.Optional;

/**
 * Dubbo 实现
 *
 * @author xijieyin <br> 2023/1/6 10:07
 */
@DubboService(protocol = "dubbo")
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnClass(ConfigCenterConfig.class)
public class Oauth2ClientImpl implements IOauth2Client {

    final ISysOauth2ClientService sysOauth2ClientService;

    @Override
    public R<OAuth2ClientDTO> getOauth2ClientById(String clientId) {
        return Optional.ofNullable(sysOauth2ClientService.getOne(
                        Wrappers.<SysOauth2Client>lambdaQuery().eq(SysOauth2Client::getClientId, clientId)))
                .map(client -> R.data(BeanUtil.copyProperties(client, OAuth2ClientDTO.class)))
                .orElseThrow(() -> new BaseException(String.format("未查询到客户端[%s]", clientId)));
    }

}
