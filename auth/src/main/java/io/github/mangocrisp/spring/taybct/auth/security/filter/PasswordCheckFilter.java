package io.github.mangocrisp.spring.taybct.auth.security.filter;

import cn.hutool.json.JSONUtil;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.OAuth2GrantType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 密码验证过滤器
 *
 * @author xijieyin <br> 2023/3/9 上午10:58
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
@RequiredArgsConstructor
public class PasswordCheckFilter extends OncePerRequestFilter {

    final RedisTemplate<String, Integer> redisTemplate;
    /**
     * 失败次数
     */
    @Setter
    private int passwordFailCount = 5;
    /**
     * 需要验证的鉴权类型
     */
    @Setter
    private String verifyGrantType = OAuth2GrantType.TAYBCT;
    /**
     * 需要验证的字段
     */
    @Setter
    private String principal = "username";
    /**
     * 被锁定超时时间,单位秒
     */
    @Setter
    private long lockTimeOut = 300L;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String grantType = request.getParameter("grant_type");
        if (StringUtil.isNotBlank(grantType) && grantType.equalsIgnoreCase(verifyGrantType)) {
            String principalField = request.getParameter(principal);
            String key = CacheConstants.OAuth.COUNT_CACHE_KEY + principal + ":" + principalField;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                if (Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElse(0) >= passwordFailCount) {
                    Long expire = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(-1L);
                    if (expire > lockTimeOut) {
                        // 如果他是没有超时的,就设置一下超时时间
                        synchronized (this) {
                            redisTemplate.opsForValue().set(key, passwordFailCount + 1, lockTimeOut, TimeUnit.SECONDS);
                        }
                        expire = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(-1L);
                    }
                    response.setStatus(HttpStatus.LOCKED.value());
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Cache-Control", "no-cache");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    response.getWriter().println(JSONUtil.parse(R.status("PASSWORD_LOGIN_LOCKED"
                            , String.format("密码验证失败超过%s次,账号被锁定,剩余[%s]秒", passwordFailCount, expire)
                            , expire)));
                    response.getWriter().flush();
                    return;
                }
            }
            filterChain.doFilter(request, response);
            int status = response.getStatus();
            if (status == HttpStatus.OK.value()) {
                redisTemplate.delete(key);
            }
            return;
        }
        filterChain.doFilter(request, response);
    }
}
