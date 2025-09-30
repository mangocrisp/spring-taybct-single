package io.github.mangocrisp.spring.taybct.auth.security.prop;

import io.github.mangocrisp.spring.taybct.tool.core.constant.PropertiesPrefixConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 鉴权登录页面配置
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/6/4 15:18
 */
@Data
@ConfigurationProperties(prefix = PropertiesPrefixConstants.AUTH + ".login-page")
public class LoginPageConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 5650818298806538650L;

    /**
     * url 加密类型
     */
    public interface EncodeType {
        /**
         * base64
         */
        String base64 = "base64";
        /**
         * url 编码
         */
        String uri_component = "uri_component";
    }

    /**
     * 是否需要重定向
     */
    Boolean redirect = false;
    /**
     * 如果不重定向，则直接返回页面名称
     */
    String loginPage = "base-login";
    /**
     * 重定向的地址
     */
    String redirectLoginPage;
    /**
     * 登录页面登录成功之后用于获取 code 的接口地址
     */
    String paramsRedirectApi;
    /**
     * 前端获取参数需要加密的 url 加密类型
     */
    String paramsRedirectApiEncodeType = EncodeType.uri_component;

}
