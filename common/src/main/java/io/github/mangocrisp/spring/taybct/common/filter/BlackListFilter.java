package io.github.mangocrisp.spring.taybct.common.filter;

import cn.hutool.core.net.NetUtil;
import io.github.mangocrisp.spring.taybct.common.prop.SecureProp;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;

/**
 * 黑名单过滤器
 *
 * @author xijieyin
 * @since 2.4.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfiguration
@RequiredArgsConstructor
public class BlackListFilter implements Filter {

    final SecureProp secureProp;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        PathMatcher pathMatcher = new AntPathMatcher();
        String requestUri = request.getRequestURI();
        if (secureProp.getBlackList().getUris().stream()
                .anyMatch(url -> pathMatcher.match(url, requestUri))) {
            // 地址在黑名单里面，就直接拦截掉
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        if (StringUtil.isNotBlank(request.getRemoteHost())) {
            String remoteHost = request.getRemoteHost();
            for (SecureProp.UriIP uriIP : secureProp.getBlackList().getUriIpSet()) {
                String path = uriIP.getUri().getPath();
                if (pathMatcher.match(path, requestUri)) {
                    // 如果配置上的 url 包含的 ip 是需要被限制的 ip 如果和请求的 ip 匹配上了就要限制访问
                    if (uriIP.getIpSet().stream().anyMatch(ip -> isIpMatch(ip, remoteHost))) {
                        // 地址在黑名单里面，就直接拦截掉
                        response.setStatus(HttpStatus.NOT_FOUND.value());
                        return;
                    }
                }
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private static boolean isIpMatch(String cidr, String hostAddress) {
        if (!cidr.contains("/")) {
            return cidr.equals(hostAddress);
        }
        return NetUtil.isInRange(hostAddress, cidr);
    }

}
