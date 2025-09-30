package io.github.mangocrisp.spring.taybct.auth.security.filter;

import cn.hutool.json.JSONUtil;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.CaptchaConstants;
import io.github.mangocrisp.spring.taybct.common.prop.SecureProp;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.util.ServletUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

/**
 * 验证码过滤器，校验验证码的有效性
 *
 * @author xijieyin <br> 2022/8/4 20:18
 * @since 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
@RequiredArgsConstructor
public class CaptchaFilter extends OncePerRequestFilter {

    final StringRedisTemplate redisTemplate;

    final ISysParamsObtainService sysParamsObtainService;

    final SecureProp prop;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        PathMatcher pathMatcher = new AntPathMatcher();
        if (prop.getCaptchaUrls().stream().noneMatch(url -> pathMatcher.match(url, request.getRequestURI()))) {
            // 如果请求地址不是需要验证码的地址就直接返回
            chain.doFilter(request, response);
            return;
        }
        if (!Boolean.parseBoolean(sysParamsObtainService.get(CacheConstants.Params.ENABLE_CAPTCHA))) {
            chain.doFilter(request, response);
            return;
        }

        // 找到客户端
        String authorization = request.getHeader(AuthHeaderConstants.AUTHORIZATION_KEY);
        if (authorization != null && authorization.startsWith(AuthHeaderConstants.BASIC_PREFIX)) {
            Set<String> captchaAuthorizations = prop.getCaptchaAuthorizations();
            // 如果客户端不是指定的需要验证码的客户端，就可以直接忽略
            if (!captchaAuthorizations.contains(authorization)) {
                chain.doFilter(request, response);
                return;
            }
        }
        // 找到授权模式
        String grantType = request.getParameter(AuthHeaderConstants.GRANT_TYPE_KEY);
        if (grantType != null) {
            Set<String> captchaGrantType = prop.getIgnore().getCaptchaGrantType();
            // 如果授权模式是被忽略验证码的就直接过
            if (captchaGrantType.contains(grantType)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 校验需要验证码请求的 url
        for (String url : prop.getCaptchaUrls()) {
            if (pathMatcher.match(url, request.getRequestURI())) {

                String code = request.getParameter(CaptchaConstants.CAPTCHA_CODE_KEY);
                String uuid = request.getParameter(CaptchaConstants.CAPTCHA_UUID_KEY);
                // 验证码不能为空
                if (StringUtils.isEmpty(code)) {
                    ServletUtil.genResponse(response, HttpStatus.BAD_REQUEST, JSONUtil.toJsonStr(R.fail(ResultCode.CAPTCHA_VALIDATE_INVALID)));
                    return;
                }
                // 为了获取验证码的 uuid 也不能为空
                if (StringUtils.isEmpty(uuid)) {
                    ServletUtil.genResponse(response, HttpStatus.UNAUTHORIZED, JSONUtil.toJsonStr(R.fail(ResultCode.CAPTCHA_VALIDATE_INVALID)));
                    return;
                }
                uuid = String.format("%s%s", CacheConstants.Captcha.KEY, uuid);
                if (Boolean.FALSE.equals(redisTemplate.hasKey(uuid))) {
                    ServletUtil.genResponse(response, HttpStatus.UNAUTHORIZED, JSONUtil.toJsonStr(R.fail(ResultCode.CAPTCHA_VALIDATE_INVALID)));
                    return;
                }
                String captcha = redisTemplate.opsForValue().get(uuid);
                // 验证完删掉 key
                redisTemplate.delete(uuid);
                if (!code.equalsIgnoreCase(captcha)) {
                    ServletUtil.genResponse(response, HttpStatus.NOT_FOUND, JSONUtil.toJsonStr(R.fail(ResultCode.CAPTCHA_VALIDATE_INVALID)));
                    return;
                }
                break;
            }
        }
        chain.doFilter(request, response);
    }

}
