package io.github.mangocrisp.spring.taybct.common.prop;

import io.github.mangocrisp.spring.taybct.common.constants.OAuthClientType;
import io.github.mangocrisp.spring.taybct.tool.core.constant.PropertiesPrefixConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 安全参数配置
 *
 * @author xijieyin <br> 2022/8/5 20:52
 * @since 1.0.0
 */
@Data
@AutoConfiguration
@ConfigurationProperties(PropertiesPrefixConstants.SECURE)
@RefreshScope
public class SecureProp {

    /**
     * 当没有配置资源访问规则的时候，默认是允许访问，即，如果不配置某个 method:url 只能某些角色访问时，默认是可以允许访问的
     */
    private Boolean allowWhenNoMatch = true;

    /**
     * 忽略规则
     */
    private Ignore ignore = new Ignore();

    /**
     * 黑名单
     */
    private Black blackList = new Black();

    /**
     * 白名单
     */
    private White whiteList = new White();

    /**
     * 需要验证码的请求
     */
    private Set<String> captchaUrls = new LinkedHashSet<>();

    /**
     * 指定需要验证码的客户端
     */
    private Set<String> captchaAuthorizations = new LinkedHashSet<>();

    /**
     * 忽略
     */
    @Data
    @NoArgsConstructor
    public static class Ignore {

        /**
         * 鉴权忽略的 url 地址（已经弃用）
         */
        @Deprecated(
                since = "3.2.1",
                forRemoval = true
        )
        private Set<String> urls = new LinkedHashSet<>();
        /**
         * 鉴权忽略的 url 地址
         */
        private Set<String> uris = new LinkedHashSet<>();
        /**
         * 验证码忽略的授权模式
         */
        private Set<String> captchaGrantType = new LinkedHashSet<>();

        /**
         * 忽略授权验证的客户端,这个忽略的是不再校验有没有接口权限,但是还是会校验有没有 token
         *
         * @see OAuthClientType
         */
        private Set<String> client = new LinkedHashSet<>(Arrays.asList(OAuthClientType.H5_APP
                , OAuthClientType.WECHAT_APP));

    }

    /**
     * 黑名单
     */
    @Data
    @NoArgsConstructor
    public static class Black {

        /**
         * 黑名单 url 优先度比白名单高，只有黑染白，少见白染黑（已经弃用）
         */
        @Deprecated(
                since = "3.2.1",
                forRemoval = true
        )
        private Set<String> urls = new LinkedHashSet<>();
        /**
         * 黑名单 url 优先度比白名单高，只有黑染白，少见白染黑
         */
        private Set<String> uris = new LinkedHashSet<>();

        /**
         * 指定限制 ip 访问 url
         */
        private Set<UriIP> uriIpSet = new LinkedHashSet<>();

    }

    /**
     * 白名单，如果配置了白名单，只有在白名单里面的 ip 才能访问配置的 url
     */
    @Data
    @NoArgsConstructor
    public static class White {

        /**
         * 指定限制 ip 访问 url，只允许配置的 ip 访问 url
         */
        private Set<UriIP> uriIpSet = new LinkedHashSet<>();

    }

    /**
     * URI 匹配 IP
     */
    @Data
    @NoArgsConstructor
    public static class UriIP {

        /**
         * 匹配的 URI
         */
        private URI uri;
        /**
         * 匹配的 ip 集合
         */
        private Set<String> ipSet = new LinkedHashSet<>();

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            UriIP that = (UriIP) obj;
            return this.getUri().equals(that.getUri());
        }

        @Override
        public int hashCode() {
            return this.getUri().hashCode();
        }

    }
}
