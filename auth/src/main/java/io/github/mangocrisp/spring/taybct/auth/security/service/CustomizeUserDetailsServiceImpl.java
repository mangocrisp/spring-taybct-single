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
 * 自定义登录逻辑
 *
 * @author xijieyin <br> 2022/8/5 12:41
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class CustomizeUserDetailsServiceImpl implements ICustomizeUserDetailsService, UserDetailsService {

    @Setter
    @NonNull
    private IUserDetailsHandle userDetailsHandle;

    @Setter
    @NonNull
    private Function<String, String> encoder = (password) -> password;

    /**
     * 从数据库根据用户名获取到用户信息
     *
     * @param username 用户名
     * @return UserDetails
     * @author xijieyin <br> 2022/8/5 12:41
     * @since 1.0.0
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(userDetailsHandle.getUserByUsername(username))
                .map(user -> {
                    // 这里需要把从数据库拿出来的 RSA 加密的密码解密然后再去与前端的密码做比较
                    setPassword(user);
                    OAuth2UserDetails userDetails = new OAuth2UserDetails(user);
                    // 当前使用的认证方式是用户名+密码
                    userDetails.setAuthenticationMethod(OAuthenticationMethodType.USERNAME.value());
                    return userDetails;
                }).orElseThrow(() -> new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMessage()));
    }

    @Override
    public UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        return Optional.ofNullable(userDetailsHandle.getUserByOpenid(openId))
                .map(user -> {
                    // 这里需要把从数据库拿出来的 RSA 加密的密码解密然后再去与前端的密码做比较
                    setPassword(user);
                    OAuth2UserDetails userDetails = new OAuth2UserDetails(user);
                    userDetails.setPrincipal(openId);
                    // 当前获取认证的方式是使用 open id
                    userDetails.setAuthenticationMethod(OAuthenticationMethodType.OPENID.value());
                    return userDetails;
                })
                // 如果是用微信 openid 去查询的用户，他就有可能是 null 的，这里就需要直接返回 null
                .orElse(null);
    }

    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        return Optional.ofNullable(userDetailsHandle.getUserByPhone(phone))
                .map(user -> {
                    setPassword(user);
                    OAuth2UserDetails userDetails = new OAuth2UserDetails(user);
                    // 当前获取认证的方式是使用 手机号
                    userDetails.setAuthenticationMethod(OAuthenticationMethodType.PHONE.value());
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
