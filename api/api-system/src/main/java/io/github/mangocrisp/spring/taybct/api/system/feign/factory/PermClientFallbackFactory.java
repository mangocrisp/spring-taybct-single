package io.github.mangocrisp.spring.taybct.api.system.feign.factory;

import io.github.mangocrisp.spring.taybct.api.system.feign.IPermClient;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 权限接口调用失败回调
 *
 * @author xijieyin <br> 2022/8/5 10:10
 * @since 1.0.0
 */
@AutoConfiguration
@Slf4j
@ConditionalOnClass({FallbackFactory.class})
public class PermClientFallbackFactory implements FallbackFactory<IPermClient> {

    @Override
    public IPermClient create(Throwable cause) {
        log.error("系统模块 - PermClient 调用失败！", cause);
        return () -> {
            log.error("初始化权限配置接口调用失败！");
            return R.fail(ResultCode.FEIGN_ERROR.getCode(), "初始化权限配置接口调用失败！");
        };
    }
}
