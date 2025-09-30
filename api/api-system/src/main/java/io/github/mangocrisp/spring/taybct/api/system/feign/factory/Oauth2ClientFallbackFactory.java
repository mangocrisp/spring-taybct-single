package io.github.mangocrisp.spring.taybct.api.system.feign.factory;

import io.github.mangocrisp.spring.taybct.api.system.feign.IOauth2Client;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 客户端接口调用失败回调
 *
 * @author xijieyin <br> 2022/8/5 10:09
 * @since 1.0.0
 */
@AutoConfiguration
@Slf4j
@ConditionalOnClass({FallbackFactory.class})
public class Oauth2ClientFallbackFactory implements FallbackFactory<IOauth2Client> {
    @Override
    public IOauth2Client create(Throwable cause) {
        log.error("系统模块 - Oauth2Client 调用失败！", cause);
        return clientId -> {
            log.error("根据客户端id查询客户端失败调用失败！");
            return R.fail(ResultCode.FEIGN_ERROR.getCode(), "根据客户端id查询客户端失败调用失败！");
        };
    }
}
