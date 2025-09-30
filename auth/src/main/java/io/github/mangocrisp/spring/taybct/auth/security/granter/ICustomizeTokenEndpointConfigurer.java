package io.github.mangocrisp.spring.taybct.auth.security.granter;

import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;

/**
 * 自定义 Token 端点配置器
 *
 * @author xijieyin <br> 2022/12/29 12:46
 */
public interface ICustomizeTokenEndpointConfigurer extends Customizer<OAuth2TokenEndpointConfigurer> {
}
