package io.github.mangocrisp.spring.taybct.common.constants;

import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;

/**
 * jwt token 的一些 key 的常量
 *
 * @author xijieyin <br> 2023/3/31 下午5:39
 */
public interface JwtTokenKeyConstants {
    /**
     * 域
     */
    String SCOPE = TokenConstants.SCOPE_KEY;
    /**
     * 客户端 id
     */
    String CLIENT_ID = TokenConstants.CLIENT_ID_KEY;
    /**
     * 授权认证类型
     */
    String GRANT_TYPE = AuthHeaderConstants.GRANT_TYPE_KEY;
    /**
     * 用户 id
     */
    String USER_ID = TokenConstants.USER_ID_KEY;

    /**
     * 认证方式,比如 username,phone... 这种可以知道是使用什么方式登录的
     */
    String AUTH_M = TokenConstants.AUTHENTICATION_METHOD;

    /**
     * 用户名
     */
    String USERNAME = TokenConstants.USER_NAME_KEY;

    /**
     * 权限(角色)列表
     */
    String AUTHORITIES = TokenConstants.JWT_AUTHORITIES_KEY;

}
