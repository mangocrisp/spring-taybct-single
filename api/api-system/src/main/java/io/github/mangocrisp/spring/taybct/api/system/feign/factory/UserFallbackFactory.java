package io.github.mangocrisp.spring.taybct.api.system.feign.factory;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.IUserClient;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 用户接口调用失败回调
 *
 * @author xijieyin <br> 2022/8/5 10:14
 * @since 1.0.0
 */
@AutoConfiguration
@Slf4j
@ConditionalOnClass({FallbackFactory.class})
public class UserFallbackFactory implements FallbackFactory<IUserClient> {
    @Override
    public IUserClient create(Throwable cause) {
        log.error("系统模块调用失败！", cause);
        return new IUserClient() {
            @Override
            public R<OAuth2UserDTO> getUserByUsername(String username) {
                return getR("根据用户名查询用户调用接口失败！");
            }

            @Override
            public R<OAuth2UserDTO> getUserByPhone(String phone) {
                return getR("根据手机号查询用户调用接口失败！");
            }

            @Override
            public R<OAuth2UserDTO> getUserByOpenid(String openid) {
                return getR("根据 openid 查询用户调用接口失败！");
            }

            @Override
            public R<String> login(JSONObject dto) {
                return getR("登录状态记录失败！");
            }

            @Override
            public R<String> logoff(JSONObject dto) {
                return getR("登出状态清空失败！");
            }

            @Override
            public R<OAuth2UserDTO> addWechatUser(JSONObject dto) {
                return getR("创建微信用户失败！");
            }
        };
    }

    private <S> R<S> getR(String message) {
        log.error(message);
        return R.fail(ResultCode.FEIGN_ERROR.getCode(), message);
    }
}
