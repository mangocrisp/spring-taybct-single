package io.github.mangocrisp.spring.taybct.single.security.granter.demo;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserRole;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserRoleMapper;
import io.github.mangocrisp.spring.taybct.auth.exception.AccountException;
import io.github.mangocrisp.spring.taybct.auth.security.granter.IOtherTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticationConverter;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticationProvider;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.dict.SysDict;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class DemoTokenEndpointConfigurer implements IOtherTokenEndpointConfigurer {

    final PasswordEncoder passwordEncoder;
    final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    /**
     * 鉴权管理，可以从这里根据刷新 token 获取新的 token 什么的
     */
    final OAuth2AuthorizationService authorizationService;

    final SysUserMapper sysUserMapper;
    final SysUserRoleMapper sysUserRoleMapper;
    final ISysParamsObtainService sysParamsObtainService;
    final RedisTemplate<String, String> redisTemplate;

    public static final AuthorizationGrantType GRANT_TYPE = new AuthorizationGrantType("demo");
    public static final String cacheKey = CacheConstants.OAuth.PREFIX + "openid:";

    @Override
    public void customize(OAuth2TokenEndpointConfigurer oAuth2TokenEndpointConfigurer) {

        // 因为和用户名差不多，是使用微信 code 来登录
        CustomizeAuthenticationConverter demoConverter = new CustomizeAuthenticationConverter(
                GRANT_TYPE
                , DemoAuthenticationToken::new
                , "code"
                , "vi"
                , map -> {
            map.put("code", "[code]不能为空，且只能有一个值");
            map.put("vi", "[vi]不能为空，且只能有一个值");
        },
                request -> {
                });
        oAuth2TokenEndpointConfigurer.accessTokenRequestConverter(demoConverter);

        CustomizeAuthenticationProvider demoAuthenticationProvider = new CustomizeAuthenticationProvider(
                GRANT_TYPE
                , customizeAuthenticationToken -> this.findUserByWechatCode((String) customizeAuthenticationToken.getPrincipal())
                , passwordEncoder
                , tokenGenerator
                , () -> DemoAuthenticationToken.class
                , authorizationService);

        demoAuthenticationProvider.setAdditionalAuthenticationChecks((UserDetails userDetails, Authentication authentication) -> {
            // 因为微信登录是使用的微信 code 去获取到 openid,然后我们系统再使用 openid 生成一个游客用户(给一些默认的字段的用户),然后他就可以登录了,不需要再做其他验证
        });
        oAuth2TokenEndpointConfigurer.authenticationProvider(demoAuthenticationProvider);
    }

    /**
     * 根据 微信 code 获取用户信息
     *
     * @param code 微信 code
     * @return 用户信息
     */
    private UserDetails findUserByWechatCode(String code) {
        if (code == null) {
            throw new AccountException("微信 code 不能为空");
        }
        String openid = getOpenid(code);
        if (ObjectUtil.isEmpty(openid)) {
            throw new AccountException("获取到的微信用户信息不正确【openid 为空】");
        }
        return findUserByOpenid(openid);
    }


    /**
     * 获取 openid
     *
     * @param code 微信 code
     * @return 返回 openid
     */
    private String getOpenid(String code) {
        // 这里就模拟获取到 open id 了实际需要自己写调用微信接口去获取 openid
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取到 openid 之后,这里需要再根据 openid 获取用户信息
     *
     * @param openid openid
     * @return 用户信息
     */
    private UserDetails findUserByOpenid(String openid) {
        return Optional.ofNullable(sysUserMapper.getUserByFiled("openid", openid))
                .map(user -> {
                    OAuth2UserDetails userDetails = new OAuth2UserDetails(user);
                    userDetails.setPrincipal(openid);
                    // 当前获取认证的方式是使用 open id
                    userDetails.setAuthenticationMethod(GRANT_TYPE.getValue());
                    return userDetails;
                })
                // 如果是用微信 openid 去查询的用户，他就有可能是 null 的，这里就需要直接返回 null
                .orElseGet(() -> {
                    SysUser sysUser = new SysUser();
                    sysUser.setId(IdWorker.getId());
                    sysUser.setUsername(cn.hutool.core.lang.UUID.fastUUID().toString(true));
                    // 这里给一个随机的密码,因为是微信端的用户嘛,不是系统后台用户,他是不需要登录后端的,如果需要他登录,你可以在用户管理里面给他重围密码,并且分配有可以登录之后的操作的角色就行
                    sysUser.setPassword(MD5.create().digestHex(cn.hutool.core.lang.UUID.randomUUID().toString()));
                    sysUser.setCreateTime(LocalDateTime.now());
                    sysUser.setNickname("昵称");
                    // 性别，微信这里没有使用国家标准
                    String sex;
                    if (new Random().nextInt(2) == 0) {
                        sex = SysDict.Gender.MALE.getKey();
                    } else {
                        sex = SysDict.Gender.FEMALE.getKey();
                    }
                    sysUser.setGender(sex);
                    // 头像地址
                    sysUser.setAvatar("");
                    // openid
                    sysUser.setOpenid(openid.toString());
                    // 邮箱
                    sysUser.setEmail("demo@test.com");
                    // 手机号
                    sysUser.setPhone(String.format("%d", new Random().nextInt(999999999)));
                    // 直接姓名
                    sysUser.setRealName("真实姓名");
                    // 是否删除,这个默认是0
                    sysUser.setIsDeleted((byte) 0);
                    // 用户类型
                    sysUser.setUserType(SysDict.UserType.TEMP.getKey());
                    // 状态1默认可用
                    sysUser.setStatus((byte) 1);
                    // 组合唯一键,这个默认给0
                    sysUser.setUniqueKey(0L);
                    int r = sysUserMapper.insert(sysUser);
                    if (r == 0) {
                        throw new BadCredentialsException("创建用户失败！");
                    }
                    // 用户和角色关系
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROLE_ID)));
                    int i = sysUserRoleMapper.insert(sysUserRole);
                    if (i == 0) {
                        throw new BaseException("用户分配默认角色【" + sysParamsObtainService.get(CacheConstants.Params.USER_ROLE) + "】失败！");
                    }
                    // 再根据 openid 获取一次用户信息
                    OAuth2UserDTO user = sysUserMapper.getUserByFiled("openid", openid);
                    OAuth2UserDetails userDetails = new OAuth2UserDetails(user);
                    // TODO openid 是比较敏感的信息，不应该放出来，这里应该是用个 cacheKey 把他缓存起来，然后在下面刷新模式的时候从缓存里面拿出来出来再去获取用户信息，所以下面的这个时间需要确定好，比 access_token 超时时间长一点才行
                    String key = UUID.randomUUID().toString(true);
                    redisTemplate.opsForValue().set(key, openid, 7200 * 2, TimeUnit.SECONDS);
                    userDetails.setPrincipal(key);
                    // 当前获取认证的方式是使用 open id
                    userDetails.setAuthenticationMethod(GRANT_TYPE.getValue());
                    return userDetails;
                });
    }

    @Override
    public Function<OAuth2Authorization, UserDetails> way2FindUserInRefreshModel() {
        return oAuth2Authorization -> {
            // 之前登录的时候使用的登录主体,可以是用户名,可以是手机号,whatever
            OAuth2Authorization.Token<OAuth2AccessToken> accessToken = oAuth2Authorization.getAccessToken();
            Map<String, Object> metadata = accessToken.getMetadata("metadata.token.claims");
            if (metadata == null) {
                throw new BadCredentialsException("未找到登录的用户信息！");
            }
            String principal = (String) metadata.get(TokenConstants.PRINCIPAL);
            if (principal == null) {
                throw new BadCredentialsException("未找到登录的用户信息！");
            }
            // 如果是 demo grant type 你要如何获取用户
            UserDetails userDetails = findUserByOpenid(principal);
            ((OAuth2UserDetails) userDetails).setGrantType(GRANT_TYPE.getValue());
            return userDetails;
        };
    }

    @Override
    public boolean refreshSupport(OAuth2Authorization oAuth2Authorization) {
        // 之前登录的时候使用的登录类型
        return oAuth2Authorization.getAuthorizationGrantType().equals(GRANT_TYPE);
    }
}
