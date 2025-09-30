package io.github.mangocrisp.spring.taybct.api.system.feign;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.factory.UserFallbackFactory;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AppConstants;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口 Feign 调用
 *
 * @author xijieyin <br> 2022/8/5 10:14
 * @since 1.0.0
 */
@FeignClient(primary = false, contextId = "userClient", value = ServeConstants.SERVE_ID_SYSTEM, fallbackFactory = UserFallbackFactory.class)
public interface IUserClient {

    /**
     * 根据用户名获取登录用户信息
     *
     * @param username 用户名
     * @return 登录用户信息
     */
    @GetMapping("v" + AppConstants.DEFAULT_API_VERSION + "/user/username/{username}")
    R<OAuth2UserDTO> getUserByUsername(@PathVariable(value = "username") String username);

    /**
     * 根据手机号获取登录用户信息
     *
     * @param phone 手机号
     * @return 登录用户信息
     */
    @GetMapping("v" + AppConstants.DEFAULT_API_VERSION + "/user/phone/{phone}")
    R<OAuth2UserDTO> getUserByPhone(@PathVariable(value = "phone") String phone);

    /**
     * 根据 openid 获取登录用户信息
     *
     * @param openid openid
     * @return 登录用户信息
     */
    @GetMapping("v" + AppConstants.DEFAULT_API_VERSION + "/user/openid/{openid}")
    R<OAuth2UserDTO> getUserByOpenid(@PathVariable(value = "openid") String openid);

    /**
     * 登录成功记录用户登录
     *
     * @param dto 用户登录成功后的信息 token 等
     * @return {@code R<String>}
     */
    @PostMapping(value = "v" + AppConstants.DEFAULT_API_VERSION + "/user/login")
    R<String> login(@RequestBody JSONObject dto);

    /**
     * 登出操作，需要清空登录状态记录
     *
     * @param dto 用户登录成功后的信息 token 等
     * @return {@code R<String>}
     */
    @PutMapping(value = "v" + AppConstants.DEFAULT_API_VERSION + "/user/logoff")
    R<String> logoff(@RequestBody JSONObject dto);

    /**
     * 创建微信用户用户，游客
     *
     * @param dto 微信用户信息
     * @return {@code R<OAuth2UserDTO>}
     */
    @PutMapping(value = "v" + AppConstants.DEFAULT_API_VERSION + "/user/addWechatUser")
    R<OAuth2UserDTO> addWechatUser(@RequestBody JSONObject dto);

}
