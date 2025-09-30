package io.github.mangocrisp.spring.taybct.auth.security.support.authorize;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.auth.security.prop.LoginPageConfig;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import lombok.Getter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * <pre>
 * 鉴权回调地址生成工具
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/6/5 12:37
 */
public class AuthorizeRedirectUrlCreator implements IAuthorizeRedirectUrlCreator {

    @Getter
    private final ConcurrentHashMap<String, Function<JSONObject, String>> fnMap = new ConcurrentHashMap<>();

    private final LoginPageConfig loginPageConfig;

    public AuthorizeRedirectUrlCreator(LoginPageConfig loginPageConfig) {
        this.loginPageConfig = loginPageConfig;
        if (this.loginPageConfig.getRedirect()) {
            if (StringUtil.isBlank(loginPageConfig.getRedirectLoginPage())) {
                throw new RuntimeException("请配置登录页地址");
            }
            if (StringUtil.isBlank(loginPageConfig.getParamsRedirectApi())) {
                throw new RuntimeException("请配置回调地址");
            }
            fnMap.put("base64", params -> String.format("redirect:%s?redirect=%s", loginPageConfig.getRedirectLoginPage(), encodeParamsBase64(params.getString("client_id"), params.getString("scope"), params.getString("redirect_uri"))));
            fnMap.put("uri_component", params -> String.format("redirect:%s?redirect=%s", loginPageConfig.getRedirectLoginPage(), encodeParamsURIComponent(params.getString("client_id"), params.getString("scope"), params.getString("redirect_uri"))));
        }
    }

    @Override
    public String defaultRedirectUrl() {
        return this.loginPageConfig.getLoginPage();
    }

    @Override
    public String create(JSONObject params) {
        if (!this.loginPageConfig.getRedirect() || !this.fnMap.containsKey(loginPageConfig.getParamsRedirectApiEncodeType())) {
            return loginPageConfig.getLoginPage();
        }
        return this.fnMap.get(loginPageConfig.getParamsRedirectApiEncodeType()).apply(params);
    }

    /**
     * 前端获取参数如果 redirect 后面需要接的参数太多就拿不全，这里使用 base64 加密一下
     *
     * @param clientId    客户端 id
     * @param scope       申请域
     * @param redirectUri 重定向地址
     * @return base64 加密后的参数
     */
    public String encodeParamsBase64(String clientId, String scope, String redirectUri) {
        return "base64:" + Base64.encodeUrlSafe(String.format("%s?response_type=code&client_id=%s&scope=%s&redirect_uri=%s", loginPageConfig.getParamsRedirectApi(), clientId, scope, redirectUri));
    }

    /**
     * 前端获取参数如果 redirect 后面需要接的参数太多就拿不全，这里使用 base64 加密一下
     *
     * @param clientId    客户端 id
     * @param scope       申请域
     * @param redirectUri 重定向地址
     * @return base64 加密后的参数
     */
    public String encodeParamsURIComponent(String clientId, String scope, String redirectUri) {
        return "URIComponent:" + URLEncoder.encode(String.format("%s?response_type=code&client_id=%s&scope=%s&redirect_uri=%s", loginPageConfig.getParamsRedirectApi(), clientId, scope, redirectUri), StandardCharsets.UTF_8);
    }
}
