package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import io.github.mangocrisp.spring.taybct.auth.exception.*;
import io.github.mangocrisp.spring.taybct.auth.security.config.AuthorizationServerConfig;
import io.github.mangocrisp.spring.taybct.auth.security.granter.ICustomizeTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.util.OAuth2Util;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalExceptionReporter;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalPrinter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.UUID;
import java.util.function.*;

/**
 * 鉴权处理，用来比对这密码是否正确
 *
 * @author xijieyin <br> 2022/12/29 9:59
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 */
@Slf4j
public class CustomizeAuthenticationProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    /**
     * 用户查找器
     */
    @NonNull
    private final Function<CustomizeAuthenticationToken, UserDetails> userDetailsFinder;

    @NonNull
    private final OAuth2AuthorizationService authorizationService;

    /**
     * 密码加密器
     */
    @NonNull
    private final PasswordEncoder passwordEncoder;
    /**
     * 映射接口，该接口可以被注入到身份验证层，以将从存储加载的权限转换为将在身份验证对象中使用的权限。
     */
    @Setter
    @NonNull
    private UnaryOperator<Collection<? extends GrantedAuthority>> authoritiesMapper = authorities -> authorities;

    /**
     * 验证获取到的用户的有效性
     * <br>
     * 比如用户的状态是锁的，就算能查询出来了，也应该要验证一下，然后报错什么的
     */
    @Setter
    @NonNull
    private Consumer<UserDetails> preAuthenticationChecks = this::defaultPreAuthenticationChecks;
    /**
     * 添加自定义的验证规则，可以和前端传过来的参数做比对
     * <br>
     * 比如 这里默认只比对密码是否匹配，你还可以自定义，但是注意的是
     * <br>
     * 这里抛出的异常只能是 继承了{@link AuthenticationException} 和 实现了我自定义的这个接口的类 {@link JsonResponseException}，这个可以看一下
     * {@link org.springframework.security.authentication.ProviderManager#authenticate}，这个类的名字就可以看出来 ，
     * 他是可以管理所有的 Provider，现在是在 Provider 里面报的错都会被抓到，然后往上抛，然后我们看一下在
     * {@link AuthorizationServerConfig#authorizationServerSecurityFilterChain(HttpSecurity, ICustomizeTokenEndpointConfigurer, IGlobalExceptionReporter, IGlobalPrinter)}
     * 里面定义的 exceptionHandling 异常端点，他默认/示例给的是使用
     * {@link org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint}
     * 这个端点，但是这个端点只会跳转页面，或者是转发，这个不符合我们想要打印报错信息给前端，好再这个是可以继承的，可以看我新写的
     * {@link JsonExceptionAuthenticationEntryPoint}
     * 这个就可以判断，如果是符合要求，可以打印到前端的我们自定义的异常，就可以去打印了。所以说，异常还是得自己自定义，如果是自定义鉴权方式的话，
     * 异常都会被这个 Filter 拦截
     * {@link org.springframework.security.web.access.ExceptionTranslationFilter}
     * 然后在这个 Filter 的方法 {@link org.springframework.security.web.access.ExceptionTranslationFilter#handleSpringSecurityException}
     * 就可以看到这段代码
     * <pre>
     * if (exception instanceof AuthenticationException) {
     *     handleAuthenticationException(request, response, chain, (AuthenticationException) exception);
     * } else if (exception instanceof AccessDeniedException) {
     *     ...
     * }
     * </pre>
     * 一个是鉴权异常，一个是访问异常，我们要做的就是鉴权异常
     */
    @Setter
    @NonNull
    private BiConsumer<UserDetails, Authentication> additionalAuthenticationChecks = this::defaultAdditionalAuthenticationChecks;

    /**
     * 其他操作
     */
    @Setter
    @NonNull
    private BiConsumer<UserDetails, Authentication> additionalAuthenticationOperation = (u, a) -> {
    };

    /**
     * token 生成器
     */
    @NonNull
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * 授权类型
     */
    @NonNull
    private final AuthorizationGrantType authorizationGrantType;
    /**
     * token 支持，必须设置这个，不然不知道该处理哪个 token
     */
    @NonNull
    private final Supplier<Class<? extends CustomizeAuthenticationToken>> supportsClass;

    /**
     * The plaintext password used to perform PasswordEncoder#matches(CharSequence,
     * String)} on when the user is not found to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    /**
     * The password used to perform {@link PasswordEncoder#matches(CharSequence, String)}
     * on when the user is not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the password is not
     * in a valid format.
     */
    private volatile String userNotFoundEncodedPassword;

    @Setter
    private BiConsumer<Authentication, OAuth2Authorization.Builder> preSaveOAuth2Authorization = (authentication, builder) -> {
    };

    public CustomizeAuthenticationProvider(
            @NonNull AuthorizationGrantType authorizationGrantType
            , @NonNull Function<CustomizeAuthenticationToken, UserDetails> userDetailsFinder
            , @NonNull PasswordEncoder passwordEncoder
            , @NonNull OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator
            , @NonNull Supplier<Class<? extends CustomizeAuthenticationToken>> supportsClass
            , @NonNull OAuth2AuthorizationService authorizationService) {
        this.authorizationGrantType = authorizationGrantType;
        this.userDetailsFinder = userDetailsFinder;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
        this.supportsClass = supportsClass;
        this.authorizationService = authorizationService;
    }

    private String determinePrincipal(CustomizeAuthenticationToken authentication) {
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getPrincipal().toString();
    }


    /**
     * 这里就是主要的鉴权方法了
     * <br>
     * 在 {@linkplain org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#authenticate(Authentication)} 里面
     * 是使用了一个叫 {@linkplain org.springframework.security.core.userdetails.UserCache} userCache 的缓存，我这里直接可以使用 Redis，所以就有一些改动
     * ，大致的逻辑都差不多
     *
     * @param authentication the authentication request object.
     * @return 成功后的身份验证
     * @throws AuthenticationException 验证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(CustomizeAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("CustomizeAuthenticationProvider.onlySupports",
                        "Only CustomizeAuthenticationToken is supported"));

        CustomizeAuthenticationToken customizeAuthenticationToken = (CustomizeAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2Util.getAuthenticatedClientElseThrowInvalidClient(customizeAuthenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        assert registeredClient != null;
        // 如果客户端不正确，也是不能登录的
        if (!registeredClient.getAuthorizationGrantTypes().contains(authorizationGrantType)) {
            throw new AccountException("当前客户端不支持的鉴权类型！");
        }
        log.trace("找到客户端 client");

        String principal = determinePrincipal(customizeAuthenticationToken);
        UserDetails user = retrieveUser(principal, customizeAuthenticationToken);

        Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");

        log.trace("找到用户信息");

        // 检查查询到的用户信息
        this.preAuthenticationChecks.accept(user);
        // 检查和前端传过来的信息比对
        this.additionalAuthenticationChecks.accept(user, customizeAuthenticationToken);
        // 检查验证完了，还可以进行其他操作
        this.additionalAuthenticationOperation.accept(user, customizeAuthenticationToken);

        CustomizeAuthenticatedToken userPrincipal = new CustomizeAuthenticatedToken(
                this.authoritiesMapper.apply(user.getAuthorities())
                , registeredClient.getClientId()
                , user
        );

        OAuth2Authorization authorization = OAuth2Authorization.withRegisteredClient(registeredClient)
                // 设置授权的标识符。
                .id(UUID.randomUUID().toString())
                .principalName(user.getUsername())
                .authorizationGrantType(authorizationGrantType)
                .authorizedScopes(registeredClient.getScopes())
                .attributes(attrs -> {
                })
                .build();


        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                // 客户端信息
                .registeredClient(registeredClient)
                // 设置表示主体资源所有者（或客户端）的身份验证。
                .principal(userPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorization(authorization)
                .authorizedScopes(authorization.getAuthorizedScopes())
                .authorizationGrantType(authorizationGrantType)
                .authorizationGrant(customizeAuthenticationToken);

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization);

        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            throw new AuthenticationServerException("The token generator failed to generate the access token.");
        }

        log.trace("Generated access token");

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        String jti = null;
        if (generatedAccessToken instanceof Jwt jwt) {
            jti = jwt.getHeaders().get(TokenConstants.JWT_JTI).toString();
        }

        if (generatedAccessToken instanceof ClaimAccessor claimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, claimAccessor.getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }


        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                throw new AuthenticationServerException("The token generator failed to generate the refresh token.");
            }

            log.trace("Generated refresh token");

            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        // 保存前操作
        preSaveOAuth2Authorization.accept(customizeAuthenticationToken, authorizationBuilder);

        // 这个地方需要保存一下，才能刷新
        OAuth2Authorization saveAuthorization = authorizationBuilder.build();
        // Invalidate the authorization code as it can only be used once
        // saveAuthorization = OAuth2AuthenticationProviderUtils.invalidate(authorization, authorizationCode.getToken());
        this.authorizationService.save(saveAuthorization);

        return new CustomizeTokenAuthenticationToken(registeredClient
                , clientPrincipal
                , jti
                , accessToken
                , refreshToken
                , customizeAuthenticationToken.getAdditionalParameters()
                , userPrincipal.getAuthorities()
                , user
                , customizeAuthenticationToken.getDetails());
    }

    /**
     * 先设置一下密码加密
     */
    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        }
    }

    private void mitigateAgainstTimingAttack(Authentication authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }
    }

    protected final UserDetails retrieveUser(String principal, CustomizeAuthenticationToken customizeAuthenticationToken)
            throws AuthenticationException {
        prepareTimingAttackProtection();
        try {
            UserDetails loadedUser = userDetailsFinder.apply(customizeAuthenticationToken);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("接口返回 null，这违反了约定，找不到用户你应该报错！-> UsernameNotFoundException");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            log.error("", ex);
            mitigateAgainstTimingAttack(customizeAuthenticationToken);
            throw new AccountException(String.format("用户[%s]不存在", principal), ex);
        } catch (InternalAuthenticationServiceException | AccountStatusException ex) {
            log.error("", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    /**
     * 设置 token 支持<br>
     * 授权之前会进来判断 传进来的 token 类型是否是我们要的类型 也就是 Authentication authenticate(Authentication authentication)
     * 方法里面的 authentication
     *
     * @param authentication 支持的 token 的类型
     * @return boolean
     * @author xijieyin <br> 2022/8/5 11:22
     * @since 1.0.0
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return supportsClass.get().isAssignableFrom(authentication);
    }

    /**
     * 默认的查询到的用户信息校验
     *
     * @param user 查询到的用户信息
     */
    public void defaultPreAuthenticationChecks(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            throw new AccountException("用户已锁定");
        }
        if (!user.isEnabled()) {
            throw new AccountException("用户被禁用");
        }
        if (!user.isAccountNonExpired()) {
            throw new AccountException("用户账号过期");
        }
    }

    /**
     * 默认的用户信息匹配校验
     *
     * @param userDetails    用户信息
     * @param authentication 请求信息
     * @throws AuthenticationException 鉴权异常
     */
    public void defaultAdditionalAuthenticationChecks(UserDetails userDetails, Authentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new PasswordException("请输入密码");
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new PasswordMismatchException("用户名密码不匹配");
        }
    }

}
