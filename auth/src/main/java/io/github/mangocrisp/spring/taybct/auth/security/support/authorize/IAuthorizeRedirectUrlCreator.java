package io.github.mangocrisp.spring.taybct.auth.security.support.authorize;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 鉴权回调地址生成工具
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/6/5 12:59
 */
public interface IAuthorizeRedirectUrlCreator {

    String defaultRedirectUrl();

    /**
     * 创建创建回调地址
     *
     * @param request 请求
     * @return 回调地址
     */
    default String create(HttpServletRequest request) {
        String clientId = request.getParameter("client_id");
        String redirectUri = request.getParameter("redirect_uri");
        if (StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(redirectUri)) {
            String scope = request.getParameter("scope");
            if (StringUtil.isBlank(scope)) {
                scope = "all";
            }
            JSONObject params = new JSONObject();
            request.getParameterMap().forEach((key, value) -> {
                if (ArrayUtil.length(value) == 1) {
                    params.put(key, value[0]);
                } else {
                    params.put(key, value);
                }
            });
            params.put("client_id", clientId);
            params.put("redirect_uri", redirectUri);
            params.put("scope", scope);
            return create(params);
        }
        return defaultRedirectUrl();
    }

    /**
     * 创建创建回调地址
     *
     * @param params 请求参数
     * @return 回调地址
     */
    String create(JSONObject params);
}
