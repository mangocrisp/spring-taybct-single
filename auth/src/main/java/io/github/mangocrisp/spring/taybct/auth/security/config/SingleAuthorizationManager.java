package io.github.mangocrisp.spring.taybct.auth.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.nimbusds.jose.JWSObject;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.ROLE;
import io.github.mangocrisp.spring.taybct.common.prop.SecureProp;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.text.ParseException;
import java.util.*;
import java.util.function.Supplier;

/**
 * 单机的鉴权
 *
 * @author xijieyin <br> 2023/1/4 11:51
 */
@RequiredArgsConstructor
public class SingleAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    final RedisTemplate<Object, Object> redisTemplate;

    final ISysParamsObtainService sysParamsObtainService;

    final SecureProp secureProp;

    PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        HttpServletRequest request = object.getRequest();
        // 预检请求放行
        if (Objects.equals(request.getMethod(), HttpMethod.OPTIONS.name())) {
            return new AuthorizationDecision(true);
        }
        // 先拿到请求地址，拼接成 “请求方法:请求地址”
        // RESTFul接口权限设计
        // 例如 /user/name/1 光从地址看是不知道他是做什么操作的，他有可能是更新，有可能是查询，这要根据他的具体请求方法来
        String method = request.getMethod();
        String path = request.getRequestURI();
        String restfulPath = method + ":" + path;

        // 如果token以"bearer "为前缀，到此方法里说明JWT有效即已认证，其他前缀的token则拦截
        String token = request.getHeader(AuthHeaderConstants.AUTHORIZATION_KEY);
        if (StrUtil.isNotBlank(token) && StrUtil.startWithIgnoreCase(token, AuthHeaderConstants.JWT_PREFIX)) {
            try {
                JWSObject parse = JWSObject.parse(token.replace(AuthHeaderConstants.JWT_PREFIX, "").trim());
                JSONObject jsonObject = JSONObject.parseObject(StrUtil.toString(parse.getPayload()));
                String clientId = jsonObject.getString(AuthHeaderConstants.CLIENT_ID_KEY);
                // 配置了无需鉴权的客户端就只需认证，无需后续鉴权
                if (secureProp.getIgnore().getClient().stream()
                        .anyMatch(client -> client.equalsIgnoreCase(clientId))) {
                    return new AuthorizationDecision(true);
                }
            } catch (ParseException e) {
                // 鉴权失败！
                return new AuthorizationDecision(false);
            }
        } else {
            // 鉴权失败！
            return new AuthorizationDecision(false);
        }

        /*
         * 缓存取 [URL权限-角色集合] 规则数据
         * urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
         */
        Map<Object, Object> urlPermRolesRules = redisTemplate.opsForHash().entries(CacheConstants.Perm.URL);

        // 根据请求路径判断有访问权限的角色列表
        // 拥有访问权限的角色
        Set<String> authorizedRoles = new LinkedHashSet<>();
        // 如果没有配置 url 只能特定角色才能访问，就直接放行
        boolean allowWhenNoMatch = secureProp.getAllowWhenNoMatch();
        for (Map.Entry<Object, Object> permRoles : urlPermRolesRules.entrySet()) {
            // 权限 请求路径
            String perm = permRoles.getKey().toString();
            // 判断是否和路径匹配
            if (pathMatcher.match(perm, restfulPath)) {
                // 如果匹配就把对应的角色加入
                List<String> roles = Convert.toList(String.class, permRoles.getValue());
                authorizedRoles.addAll(Convert.toList(String.class, roles));
                if (allowWhenNoMatch) {
                    allowWhenNoMatch = false;
                }
            }
        }
        if (allowWhenNoMatch) {
            // 如果路径没有做配置哪些角色可以访问，就默认是放行
            return new AuthorizationDecision(true);
        }
        Authentication authenticated = authentication.get();
        if (authenticated.isAuthenticated()) {
            return authenticated.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .anyMatch(authority -> {
                        String roleCode = authority.substring(TokenConstants.AUTHORITY_PREFIX.length()); // 用户的角色
                        // 这里判断只有 000000:root 也就是默认租户的超级管理员才给直接放行
                        if (String.format("%s:%s"
                                , sysParamsObtainService.get(CacheConstants.Params.TENANT_ID)
                                , ROLE.ROOT).equals(roleCode)) {
                            return true; // 如果是超级管理员则放行
                        }
                        return CollectionUtil.isNotEmpty(authorizedRoles) && authorizedRoles.contains(roleCode);
                    }) ? new AuthorizationDecision(true) : new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(false);
    }

}
