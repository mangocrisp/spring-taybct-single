package io.github.mangocrisp.spring.taybct.auth.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.function.Function;

/**
 * 定义自己的用户查询接口，继承的 oauth2 的查询接口已经有按用户名查询了
 * 这里再定义几个，例如，使用 open id ，或者 手机号码，或者用户 id 等方式
 *
 * @author xijieyin <br> 2022/8/5 12:36
 * @since 1.0.0
 */
public interface ICustomizeUserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 微信登录，根据 open id 获取用户信息
     *
     * @param openId open id
     * @return {@code UserDetails}
     * @throws UsernameNotFoundException 用户名找不到
     */
    default UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        return null;
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param phone 手机号码
     * @return {@code UserDetails}
     * @throws UsernameNotFoundException 用户名找不到
     */
    default UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        return null;
    }

    /**
     * 根据用户 id 获取用户信息
     *
     * @param userId 手机号码
     * @return {@code UserDetails}
     * @throws UsernameNotFoundException 用户找不到
     */
    default UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return null;
    }

    /**
     * 设置加密器
     *
     * @param encoder 加密处理
     */
    void setEncoder(Function<String, String> encoder);

}
