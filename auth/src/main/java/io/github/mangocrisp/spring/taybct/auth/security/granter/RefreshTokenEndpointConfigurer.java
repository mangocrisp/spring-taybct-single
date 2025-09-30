package io.github.mangocrisp.spring.taybct.auth.security.granter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import java.util.function.Function;

/**
 * 自定义 token 鉴权，这里要实现的 {@code Function<OAuth2Authorization, UserDetails>} 是实现你自定义的鉴权模式将要如何刷新 token
 *
 * @author XiJieYin <br> 2023/4/11 14:00
 */
public abstract class RefreshTokenEndpointConfigurer implements IOtherTokenEndpointConfigurer, Function<OAuth2Authorization, UserDetails> {

    /**
     * 用于刷新模式下刷新 token,支持的鉴权类型
     *
     * @param authorizationGrantType 鉴权类型，这个可以从 token 管理里面的刷新 token 中获取到
     * @return 是否支持
     */
    public abstract boolean support(AuthorizationGrantType authorizationGrantType);

}
