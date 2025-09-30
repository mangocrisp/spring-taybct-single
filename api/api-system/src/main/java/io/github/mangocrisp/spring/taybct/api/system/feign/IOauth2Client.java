package io.github.mangocrisp.spring.taybct.api.system.feign;

import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2ClientDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.factory.Oauth2ClientFallbackFactory;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AppConstants;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 客户端 Feign 调用
 *
 * @author xijieyin <br> 2022/8/5 10:08
 * @since 1.0.0
 */
@FeignClient(primary = false, contextId = "oauth2Client", value = ServeConstants.SERVE_ID_SYSTEM, fallbackFactory = Oauth2ClientFallbackFactory.class)
public interface IOauth2Client {

    /**
     * 根据客户端 id 获取到客户端的信息
     *
     * @param clientId 客户商 id
     * @return {@code R<OAuth2ClientDTO>}
     * @author xijieyin <br> 2022/8/5 10:09
     * @since 1.0.0
     */
    @RequestMapping(value = "v" + AppConstants.DEFAULT_API_VERSION + "/oauth2Client/clientId/{clientId}", method = {RequestMethod.POST})
    R<OAuth2ClientDTO> getOauth2ClientById(@PathVariable(value = "clientId") String clientId);
}
