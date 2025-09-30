package io.github.mangocrisp.spring.taybct.auth.security.granter;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;

import java.util.List;
import java.util.function.Function;

/**
 * 可以支持刷新 token 的其他的端点配置
 *
 * @author XiJieYin <br> 2023/12/25 17:12
 */
@Builder
@Data
public class RefreshOtherTokenEndpointConfigurer implements IOtherTokenEndpointConfigurer {

    @Singular("addGranter")
    List<RefreshTokenEndpointConfigurer> granterList;

    @Override
    public void customize(OAuth2TokenEndpointConfigurer tokenEndpoint) {
        granterList.forEach(g -> g.customize(tokenEndpoint));
    }

    @Override
    public Function<OAuth2Authorization, UserDetails> way2FindUserInRefreshModel() {
        return oAuth2Authorization -> granterList.stream()
                // 之前登录的时候使用的登录类型
                .filter(g -> g.support(oAuth2Authorization.getAuthorizationGrantType()))
                .findFirst()
                // 如果没有找到鉴权模式，那就不能知道要怎么获取用户
                .orElseThrow(() -> new UsernameNotFoundException("没有匹配用户的方法！"))
                .apply(oAuth2Authorization);
    }

}
