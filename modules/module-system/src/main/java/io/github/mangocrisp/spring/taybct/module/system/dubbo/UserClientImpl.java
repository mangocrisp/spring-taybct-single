package io.github.mangocrisp.spring.taybct.module.system.dubbo;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.IUserClient;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserService;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.Optional;

/**
 * @author xijieyin <br> 2023/1/6 11:31
 */
@DubboService(protocol = "dubbo")
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnClass(ConfigCenterConfig.class)
public class UserClientImpl implements IUserClient {

    final ISysUserService sysUserService;
    final ISysUserOnlineService sysUserOnlineService;

    @Override
    public R<OAuth2UserDTO> getUserByUsername(String username) {
        return Optional.ofNullable(sysUserService.getUserByFiled("username", username))
                .map(R::data).orElseGet(() ->
                        Optional.ofNullable(sysUserService.getUserByFiled("email", username))
                                .map(R::data).orElse(R.fail("根据用户名获取用户，未找到用户！")));
    }

    @Override
    public R<OAuth2UserDTO> getUserByPhone(String phone) {
        return R.data(sysUserService.getUserByFiled("phone", phone));
    }

    @Override
    public R<OAuth2UserDTO> getUserByOpenid(String openid) {
        return R.data(sysUserService.getUserByFiled("openid", openid));
    }

    @Override
    public R<String> login(JSONObject dto) {
        return sysUserOnlineService.login(dto, true) ? R.ok("操作成功！") : R.fail("操作失败！");
    }

    @Override
    public R<String> logoff(JSONObject dto) {
        return sysUserOnlineService.logoff(dto, "账号已登出！") ? R.ok() : R.fail();
    }

    @Override
    public R<OAuth2UserDTO> addWechatUser(JSONObject dto) {
        return R.data(sysUserService.addWechatUser(dto));
    }
}
