package io.github.mangocrisp.spring.taybct.api.system.feign;

import io.github.mangocrisp.spring.taybct.api.system.feign.factory.PermClientFallbackFactory;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AppConstants;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 权限接口 Feign 调用
 *
 * @author xijieyin <br> 2022/8/5 10:10
 * @since 1.0.0
 */
@FeignClient(primary = false, contextId = "permClient", value = ServeConstants.SERVE_ID_SYSTEM, fallbackFactory = PermClientFallbackFactory.class)
public interface IPermClient {

    /**
     * 初始化角色权限配置
     *
     * @return {@code R<OAuth2ClientDTO>}
     * @author xijieyin <br> 2022/8/5 10:10
     * @since 1.0.0
     */
    @RequestMapping(value = "v" + AppConstants.DEFAULT_API_VERSION + "/rolePerm/iniConfig", method = {RequestMethod.POST})
    R<?> iniConfig();

}
