package io.github.mangocrisp.spring.taybct.auth.controller;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.support.authorize.IAuthorizeRedirectUrlCreator;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * <pre>
 *     如果需要使用授权码模式，登录页面默认为 base-login，如果需要自定义登录页面，
 *     浏览器访问：
 *     {@code http://[ip]/api/auth/oauth/authorize?response_type=code&client_id=[client_id]&scope=[scope]&redirect_uri=[redirect_uri]}
 *     鉴权服务器会跳转到登录页面，登录成功后，会返回一个授权码，然后跳转到 redirect_uri 中指定的页面，
 * </pre>
 *
 * @author xijieyin <br> 2022/12/30 1:18
 */
@AutoConfiguration
@Tag(name = "认证服务器")
@Controller
@RequiredArgsConstructor
class LoginController {

    final IUserDetailsHandle userDetailsHandle;

    final ISecurityUtil securityUtil;

    final IAuthorizeRedirectUrlCreator authorizeRedirectUrlCreator;

    @GetMapping("/login")
    String login(HttpServletRequest request) {
        return authorizeRedirectUrlCreator.create(request);
    }

    /**
     * 登出接口<br>
     *
     * <p>
     * 新增了返回登出成功的 token 解密后的对象，方便调用登出接口的程序处理限制再登录
     * </p>
     *
     * <p>
     * 携带 access token 来登出，会把 access token 加入到黑名单，然后在网关过滤的时候检查 access token
     * 是否在黑名单里面来确定用户 access token 是否已经登出了
     * </p>
     *
     * @return R
     * @author xijieyin <br> 2022/8/5 10:26
     * @since 1.0.0
     */
    @Operation(summary = "OAuth2认证-登出")
    @RequestMapping(value = ServeConstants.CONTEXT_PATH_AUTH + "oauth/logout", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R<?> logout() {
        JSONObject jwtPayload = null;
        try {
            jwtPayload = securityUtil.getLoginUser().getPayload();
            // 登出成功，返回被放入黑名单的 jwt token id
            return R.ok("登出成功！", jwtPayload);
        } finally {
            if (jwtPayload != null) {
                userDetailsHandle.logoff(jwtPayload);
            }
        }
    }

}