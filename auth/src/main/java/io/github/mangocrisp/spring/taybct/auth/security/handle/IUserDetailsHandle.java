package io.github.mangocrisp.spring.taybct.auth.security.handle;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;

/**
 * 用户处理，你可以实现这个接口去配置从其他任何地方获取用户信息，当前默认是从配置文件里面获取用户信息
 *
 * @author xijieyin <br> 2022/8/5 11:57
 * @since 1.0.0
 */
public interface IUserDetailsHandle {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return OAuth2UserDTO
     * @author xijieyin <br> 2022/8/5 11:57
     * @since 1.0.0
     */
    default OAuth2UserDTO getUserByUsername(String username) {
        return null;
    }

    /**
     * 根据手机号获取用户
     *
     * @param phone 手机号
     * @return OAuth2UserDTO
     * @author xijieyin <br> 2022/8/5 11:57
     * @since 1.0.0
     */
    default OAuth2UserDTO getUserByPhone(String phone) {
        return null;
    }

    /**
     * 根据 openid 获取用户
     *
     * @param openid openid
     * @return OAuth2UserDTO
     * @author xijieyin <br> 2022/8/5 11:57
     * @since 1.0.0
     */
    default OAuth2UserDTO getUserByOpenid(String openid) {
        return null;
    }

    /**
     * 登录成功后的操作
     *
     * @param dto 登录成功后的登录信息
     * @return boolean
     * @author xijieyin <br> 2022/8/5 11:57
     * @since 1.0.0
     */
    default boolean login(JSONObject dto) {
        return true;
    }

    /**
     * 登出操作
     *
     * @param dto 登录信息
     * @return boolean
     * @author xijieyin <br> 2022/8/5 11:58
     * @since 1.0.0
     */
    default boolean logoff(JSONObject dto) {
        return true;
    }

    /**
     * 创建微信用户用户，游客
     *
     * @param dto 微信用户信息
     * @return OAuth2UserDTO
     * @author xijieyin <br> 2022/8/5 11:58
     * @since 1.0.0
     */
    default OAuth2UserDTO addWechatUser(JSONObject dto) {
        return null;
    }

    /**
     * 根据 jti 获取 token
     *
     * @param jti jti
     * @return access token
     */
    default String getAccessTokenByJTI(String jti) {
        return null;
    }
}
