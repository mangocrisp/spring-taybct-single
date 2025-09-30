package io.github.mangocrisp.spring.taybct.auth.security.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.ROLE;
import io.github.mangocrisp.spring.taybct.common.prop.SecureProp;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.util.MutableHttpServletRequest;
import io.github.mangocrisp.spring.taybct.tool.core.util.ServletUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 自定义过滤器，过滤登录 token 信息，如果有黑名单，即登出后的 token 也视作不通过
 *
 * @author xijieyin <br> 2022/8/4 20:18
 * @since 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    final RedisTemplate<Object, Object> redisTemplate;

    final ISysParamsObtainService sysParamsObtainService;

    final KeyPair keyPair;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Resource
    SecureProp secureProp;


    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader(AuthHeaderConstants.AUTHORIZATION_KEY);
        // 不是正确的的JWT不做解析处理
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, AuthHeaderConstants.JWT_PREFIX)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
        token = StrUtil.replaceIgnoreCase(token, AuthHeaderConstants.JWT_PREFIX, Strings.EMPTY);
        String payload;
        String jti;
        try {
            JWSObject parse = JWSObject.parse(token);
            if (!parse.verify(new RSASSAVerifier((RSAPublicKey) keyPair.getPublic()))) {
                // 鉴权失败！
                ServletUtil.genResponse(response, HttpStatus.UNAUTHORIZED, JSONUtil.toJsonStr(R.fail(ResultCode.TOKEN_INVALID_OR_EXPIRED)));
                return;
            }
            jti = parse.getHeader().getCustomParam(TokenConstants.JWT_JTI).toString();
            payload = StrUtil.toString(parse.getPayload());
        } catch (Exception e) {
            // 如果是在黑名单里面就掉线 401
            ServletUtil.genResponse(response, HttpStatus.UNAUTHORIZED, JSONUtil.toJsonStr(R.fail(ResultCode.TOKEN_INVALID_OR_EXPIRED)));
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(payload);
        // 超时时间
        Long exp = jsonObject.getLong(TokenConstants.JWT_EXP);
        if (exp - System.currentTimeMillis() / 1000 < 0) {
            // 如果是在黑名单里面就掉线 401
            ServletUtil.genResponse(response, HttpStatus.UNAUTHORIZED, JSONUtil.toJsonStr(R.fail(ResultCode.TOKEN_INVALID_OR_EXPIRED)));
            return;
        }
        jsonObject.put(TokenConstants.JWT_JTI, jti);
        Boolean isBlack = redisTemplate.hasKey(CacheConstants.OAuth.BLACKLIST + jti);
        if (Boolean.TRUE.equals(isBlack)) {
            Object message = Optional.ofNullable(redisTemplate.opsForValue().get(CacheConstants.OAuth.BLACKLIST + jti)).orElse("当前用户被强制登出！");
            // 如果是在黑名单里面就掉线 401
            ServletUtil.genResponse(response, HttpStatus.UNAUTHORIZED, JSONUtil.toJsonStr(R.fail(ResultCode.TOKEN_INVALID_OR_EXPIRED.getCode(), message.toString())));
        } else {

            /*
             * 登录成功之后，需要选择租户才给登录进入首页，
             * 选择了租户之后，就需要再调用一下这个接口来记录是使用租户登录了。<br>
             * 选择租户的时候，会把 jti 和 tenant id 作为 kv 存入 redis，在网关
             * 做过滤的时候使用 jti 查询到 tenant id ,然后存入到 Payload 里面，然后就可以使用
             * {@link SecurityUtil#getTenantId()} 去获取这个 tenant id 了，如果没有在 redis
             * 里面获取到 tenant id，就使用默认的租户 id
             */
            jsonObject.put(TokenConstants.TENANT_ID_KEY
                    , Optional.ofNullable(redisTemplate.opsForValue().get(CacheConstants.OAuth.TENANT_RELATION + jti))
                            .map(Object::toString).orElse(sysParamsObtainService.get(CacheConstants.Params.TENANT_ID))
            );

            if (jsonObject.containsKey(TokenConstants.JWT_AUTHORITIES_KEY)) {
                String tenantId = jsonObject.getString(TokenConstants.TENANT_ID_KEY);
                // 这里过滤一下，这个用户在当前租户下面有哪些角色
                List<String> roles = jsonObject.getJSONArray(TokenConstants.JWT_AUTHORITIES_KEY).toJavaList(String.class)
                        // 这里要判断一下，如果是超级管理员，就不需要过滤
                        .stream().filter(role -> String.format("%s:%s"
                                , sysParamsObtainService.get(CacheConstants.Params.TENANT_ID)
                                , ROLE.ROOT).equals(role) ||
                                role.startsWith(tenantId))
                        // 这里因为查询出来的角色是 tenantId:roleCode 这样的角色组合
                        .map(role -> role.substring(role.indexOf(":") + 1))
                        .collect(Collectors.toList());
                jsonObject.put(TokenConstants.JWT_AUTHORITIES_KEY, roles);
            }

            MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
            mutableRequest.putHeader(TokenConstants.JWT_PAYLOAD_KEY, SM4Coder.getSM4().encryptBase64(jsonObject.toJSONString(), StandardCharsets.UTF_8));
            // 在请求头把登录后的客户端 id 添加进去，方便后面验证
            mutableRequest.putHeader(AuthHeaderConstants.CLIENT_ID_KEY, jsonObject.getString(AuthHeaderConstants.CLIENT_ID_KEY));
            chain.doFilter(mutableRequest, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
