package io.github.mangocrisp.spring.taybct.auth.security.granter;

import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;

import java.util.function.Function;

/**
 * 其他的 token 端点配置器
 *
 * @author XiJieYin <br> 2023/2/15 17:36
 */
public interface IOtherTokenEndpointConfigurer extends Customizer<OAuth2TokenEndpointConfigurer> {

    /**
     * way to find user in refresh mode, provide an oauth2 authorization info that include your principal name
     * ,refresh token expires time,grant type ...
     * whatever,you can find the user by those params.
     *
     * @return this is a customized function
     */
    default Function<OAuth2Authorization, UserDetails> way2FindUserInRefreshModel() {
        // 如果没有找到鉴权模式，那就不能知道要怎么获取用户
        throw new UsernameNotFoundException("没有匹配用户的方法！");
    }

    /**
     * 是否支持刷新模式
     *
     * @param oAuth2Authorization token 授权信息，鉴权之后的结果之类的，存储了用户鉴权相关的信息
     * @return boolean
     */
    default boolean refreshSupport(OAuth2Authorization oAuth2Authorization) {
        return false;
    }

}
