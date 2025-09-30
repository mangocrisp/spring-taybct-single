package io.github.mangocrisp.spring.taybct.auth.security.service;

import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.common.enums.OAuthenticationMethodType;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.function.Function;

/**
 * 授权码模式(第三方用户登录)的用户获取
 *
 * @author xijieyin <br> 2022/12/30 19:01
 */
@AllArgsConstructor
@NoArgsConstructor
public class ThirdUserDetailsService implements UserDetailsService {

    @Setter
    @NonNull
    private IUserDetailsHandle userDetailsHandle;

    @Setter
    @NonNull
    private Function<String, String> encoder = (password) -> password;


    // TODO 这个到时候记得加上去
    //@Cacheable(cacheNames = CacheConstants.OAuth.USERNAME, key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userDetailsHandle.getUserByUsername(username))
                .map(user -> {
                    // 这里需要把从数据库拿出来的 RSA 加密的密码解密然后再去与前端的密码做比较
//                    return new User(user.getUsername(), encoder.apply(user.getPassword()),
//                            user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                    setPassword(user);
                    OAuth2UserDetails userDetails = new OAuth2UserDetails(user);
                    // 当前使用的认证方式是用户名+密码
                    userDetails.setAuthenticationMethod(OAuthenticationMethodType.USERNAME.value());
                    return userDetails;
                }).orElseThrow(() -> new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMessage()));
    }

    /**
     * 设置登录用户的密码<br>
     * 这里需要把从数据库拿出来的 RSA 加密的密码解密然后再去与前端的密码做比较<br>
     * 最后对称加密一下用于 OAuth2 做鉴权比对
     *
     * @param user 从数据库，或者是哪里获取到的用户信息
     */
    private void setPassword(OAuth2UserDTO user) {
        user.setPassword(encoder.apply(user.getPassword()));
    }
}
