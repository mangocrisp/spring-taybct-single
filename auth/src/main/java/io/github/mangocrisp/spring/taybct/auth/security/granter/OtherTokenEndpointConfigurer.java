package io.github.mangocrisp.spring.taybct.auth.security.granter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 自定义的 token 鉴权配置，配置端点如何鉴权和一些自定义的鉴权方式
 *
 * @author xijieyin
 */
public class OtherTokenEndpointConfigurer implements IOtherTokenEndpointConfigurer {

    public final List<IOtherTokenEndpointConfigurer> otherTokenEndpointConfigurerList;

    public OtherTokenEndpointConfigurer() {
        otherTokenEndpointConfigurerList = Collections.emptyList();
    }

    public OtherTokenEndpointConfigurer(final List<IOtherTokenEndpointConfigurer> otherTokenEndpointConfigurerList) {
        this.otherTokenEndpointConfigurerList = otherTokenEndpointConfigurerList;
    }

    @Override
    public void customize(OAuth2TokenEndpointConfigurer oAuth2TokenEndpointConfigurer) {
        otherTokenEndpointConfigurerList.forEach(e -> e.customize(oAuth2TokenEndpointConfigurer));
    }

    /**
     * 为你的鉴权模式配置刷新 token 如果续期
     *
     * @return way to find user in refresh token mode.
     */
    @Override
    public Function<OAuth2Authorization, UserDetails> way2FindUserInRefreshModel() {
        return oAuth2Authorization -> otherTokenEndpointConfigurerList.stream()
                // 先过滤出来支持的鉴权类型的方法
                .filter(e -> e.refreshSupport(oAuth2Authorization))
                // 这里只要第一个
                .findFirst()
                // 然后找到方法
                .map(IOtherTokenEndpointConfigurer::way2FindUserInRefreshModel)
                // 如果没有找到鉴权模式，那就不能知道要怎么获取用户
                .orElseThrow(() -> new UsernameNotFoundException("没有匹配用户的方法！"))
                // 然后进行刷新鉴权
                .apply(oAuth2Authorization);
    }
}
