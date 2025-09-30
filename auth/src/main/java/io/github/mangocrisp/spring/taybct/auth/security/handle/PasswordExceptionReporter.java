package io.github.mangocrisp.spring.taybct.auth.security.handle;

import io.github.mangocrisp.spring.taybct.auth.exception.PasswordException;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.OAuth2GrantType;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.DefaultExceptionReporter;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 密码验证失败异常记录,这里可以记录密码验证失败次数
 *
 * @author xijieyin <br> 2023/3/9 上午11:45
 */
@RequiredArgsConstructor
public class PasswordExceptionReporter extends DefaultExceptionReporter {

    final RedisTemplate<String, Integer> redisTemplate;
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

    @Override
    public void recording(ServletRequest servletRequest, Throwable e) {
        super.recording(servletRequest, e);
        if (e instanceof PasswordException) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String grantType = request.getParameter("grant_type");
            if (StringUtil.isNotBlank(grantType) && grantType.equalsIgnoreCase(verifyGrantType)) {
                String principalField = request.getParameter(principal);
                String key = CacheConstants.OAuth.COUNT_CACHE_KEY + principal + ":" + principalField;
                // 记录次数,每天都更新
                redisTemplate.opsForValue().setIfAbsent(key, 0, 1L, TimeUnit.DAYS);
                redisTemplate.opsForValue().increment(key, 1);
            }
        }
    }
}
