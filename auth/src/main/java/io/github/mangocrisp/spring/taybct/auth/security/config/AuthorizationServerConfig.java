package io.github.mangocrisp.spring.taybct.auth.security.config;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSONObject;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.github.mangocrisp.spring.taybct.auth.exception.JsonExceptionAuthenticationEntryPoint;
import io.github.mangocrisp.spring.taybct.auth.security.granter.DefaultCustomizeTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.ICustomizeTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.IOtherTokenEndpointConfigurer;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeAuthenticatedToken;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.OAuth2AuthorizationCodeRequestJTIAuthenticationConverter;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IClientDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.handle.deft.PropertiesClientDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.auth.security.prop.ClientConfig;
import io.github.mangocrisp.spring.taybct.auth.security.service.CustomizeRegisteredClientRepository;
import io.github.mangocrisp.spring.taybct.auth.security.service.ICustomizeUserDetailsService;
import io.github.mangocrisp.spring.taybct.auth.security.util.ResponseHandler;
import io.github.mangocrisp.spring.taybct.common.constants.JwtTokenKeyConstants;
import io.github.mangocrisp.spring.taybct.common.constants.LoginConstants;
import io.github.mangocrisp.spring.taybct.common.constants.OAuth2GrantType;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.common.enums.OAuthenticationMethodType;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalExceptionReporter;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalPrinter;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.support.IEncryptedPassable;
import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSACoder;
import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSAProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.PublicClientAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.oauth2.server.authorization.web.authentication.ClientSecretBasicAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author xijieyin <br> 2022/12/27 11:14
 */
@AutoConfiguration
@Import({OAuth2AuthorizationServerConfiguration.class, JdbcAuthConfig.class})
public class AuthorizationServerConfig {
    /**
     * 请求前缀
     */
    @Value(ServeConstants.CONTEXT_PATH_AUTH)
    private String authContextPath = "/";
    @Value("${server.servlet.context-path:/}")
    private String contextPath;
    @Value("${spring.application.name:api}")
    private String applicationName;

    /**
     * A Spring Security filter chain for the Protocol Endpoints.<br>
     * 协议端点的Spring Security过滤器链。<br>
     * 这些协议端点，只有配置了他才能够访问的到接口地址（类似mvc的controller）。
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     * @see ClientSecretBasicAuthenticationConverter#convert(HttpServletRequest)
     * @see OAuth2AuthorizationCodeAuthenticationProvider#authenticate(Authentication)
     * @see PublicClientAuthenticationProvider#authenticate(Authentication)
     * @see AuthenticationSuccessHandler
     * @see AuthenticationFailureHandler
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http
            , ICustomizeTokenEndpointConfigurer customizeTokenEndpointConfigurer
            , IGlobalExceptionReporter globalExceptionReporter
            , IGlobalPrinter globalPrinter
            , IUserDetailsHandle userDetailsHandle
            , IEncryptedPassable encryptedPassable
            , CorsConfigurationSource corsConfigurationSource)
            throws Exception {

        // 应用默认授权服务器配置
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        AtomicReference<AuthenticationManager> authenticationManagerAtomicReference = new AtomicReference<>();

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        // 禁用 csrf
        http.csrf(AbstractHttpConfigurer::disable)
                // 配置 CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource));
//                .csrf((csrf) -> {
//                    csrf.ignoringRequestMatchers(new RequestMatcher[]{endpointsMatcher});
//                })
//                .apply(authorizationServerConfigurer);
        http.securityMatcher(endpointsMatcher);
        // 授权服务器配置
//        http.authorizeHttpRequests((authorize) -> (authorize.anyRequest()).authenticated());
        // Customizing the configuration
        /*
        OAuth2AuthorizationServerConfigurer provides the ability to fully customize the security configuration for an
        OAuth2 authorization server. It lets you specify the core components to use - for example, RegisteredClientRepository,
        OAuth2AuthorizationService, OAuth2TokenGenerator, and others. Furthermore, it lets you customize the request
        processing logic for the protocol endpoints – for example, authorization endpoint, token endpoint, token introspection
        endpoint, and others.

        机翻：OAuth2AuthorizationServerConfigurer提供了完全自定义OAuth2授权服务器的安全配置的能力。它允许您指定要使用的核心组件。。
         */
        http.with(authorizationServerConfigurer, configurer -> configurer
//                // 1 for managing new and existing clients. 用于管理新客户和现有客户。这个我这里默认的实现是只做查询客户端，客户端，我自己写或者我自己可以定义
                        // 其他的方式，这个，看是什么样的需求喽
//                .registeredClientRepository(registeredClientRepository)
//                // 2 for managing new and existing authorizations. 用于管理新的和现有的授权。同上，同下，都是可以使用 JDBC 实现管理的 spring boot 提供了这
                        // 样的实现，但是我这里就只选择用默认的 InMemory 内存管理了
//                .authorizationService(authorizationService)
//                // 3 for managing new and existing authorization consents. 用于管理新的和现有的授权同意。
                        /*
                          上3个都可以使用 JDBC 来管理
                          Spring Security 的建表语句在 org/springframework/security/core/userdetails/jdbc/users.ddl
                          可以用 UserDetailsManager 添加用户信息
                          Spring authorization Server 的建表文件在
                          org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql
                          org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
                          org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
                         */
//                .authorizationConsentService(authorizationConsentService)
//                // 4 for customizing configuration settings for the OAuth2 authorization server. 用于自定义OAuth2授权服务器的配置设置。
                        // 也就是配置一些请求地址啊什么的，这里直接用默认的就好了，除非是有特别需求
//                .authorizationServerSettings(authorizationServerSettings)
//                // 5 for generating tokens supported by the OAuth2 authorization server. 用于生成OAuth2授权服务器支持的令牌。
                        // 可以自定义 token 的一些信息
//                .tokenGenerator(tokenGenerator)
//                // 6 提供了自定义OAuth2客户端身份验证的能力。它定义了扩展点，允许您自定义客户端身份验证请求的预处理、主处理和后处理逻辑。
                        .clientAuthentication(clientAuthentication -> {
                            // 说白了就是比如前端请求传的放在请求头的客户端 id 和 密钥 怎么转换成 Authentication （身份验证，数据载体，叫什么都好，最后验证成功返回的结果也是这个）
                            // 我默认只使用 ClientAuthenticationMethod.CLIENT_SECRET_BASIC，那这个设置不设置都无所谓啦
//                    clientAuthentication.authenticationConverter()
                            // 大概是用来做判断，哪些客户端能不能使用哪些方式，我都默认了，这个也可以不设置了
//                    clientAuthentication.authenticationConverters();
                            // 这个就是主要鉴定客户端的方法啦，拿到了转换之后的 Authentication，然后去和对应的方式获取的 client 去做比对 ？然后返回正确的 client 或者报错
//                    clientAuthentication.authenticationProvider();
                            // 也是限制不同的客户端有不同的方法
//                    clientAuthentication.authenticationProviders();
                            // The AuthenticationSuccessHandler (post-processor) used for handling a successful client authentication and associating the
                            // OAuth2ClientAuthenticationToken to the SecurityContext.
                            // 用于处理成功用户身份验证的策略。 实现可以为所欲为，但典型的行为是控制到后续目的地的导航（使用重定向或转发）。
                            // 例如，在用户通过提交登录表单登录后，应用程序需要决定之后应该将其重定向到何处
//                    clientAuthentication.authenticationSuccessHandler();
                            // 有成功就有失败啦
//                    clientAuthentication.errorResponseHandler();
                        })
//                // 7 提供了自定义OAuth2授权端点的功能。它定义了扩展点，允许您自定义OAuth2授权请求的预处理、主处理和后处理逻辑。
                        .authorizationEndpoint(authorizationEndpoint -> {
                            // 将请求转换成身份证验证
                            OAuth2AuthorizationCodeRequestJTIAuthenticationConverter authorizationRequestConverter = new OAuth2AuthorizationCodeRequestJTIAuthenticationConverter();
                            authorizationRequestConverter.setUserDetailsHandle(userDetailsHandle);
                            authorizationRequestConverter.setJtiCookieKeyFN(s -> applicationName.toUpperCase() + "-" + s);
                            authorizationRequestConverter.setAuthenticationManagerAtomicReference(authenticationManagerAtomicReference);
                            authorizationEndpoint.authorizationRequestConverter(authorizationRequestConverter);
                            // 对身份进行验证
//                    authorizationEndpoint.authenticationProvider();
                            // 验证身份转换方法访问限制
//                    authorizationEndpoint.authorizationRequestConverters();
                            // 是用于验证授权代码授予中使用的特定OAuth2授权请求参数的默认验证器 默认实现验证redirect_uri和scope参数
//                    authorizationEndpoint.authenticationProviders();
                            // 成功回调
//                    authorizationEndpoint.authorizationResponseHandler();
                            // 失败回调
//                    authorizationEndpoint.errorResponseHandler();
                        })
//                // 8 提供了自定义OAuth2令牌端点的能力。它定义了扩展点，允许您自定义OAuth2访问令牌请求的预处理、主处理和后处理逻辑。
                        // TODO 这个会比较麻烦一点，但是，如果要做自定义的鉴权，主要就是通过这个来做
                        .tokenEndpoint(customizeTokenEndpointConfigurer)
//                // 9 提供了自定义OAuth2令牌自检端点的能力。它定义了扩展点，允许您自定义OAuth2内省请求的预处理、主处理和后处理逻辑。
                // 自检的这个，我们就不需要做了，原来的框架里面有写黑名单这样的功能，和他这个应该也是差不太多的
//                .tokenIntrospectionEndpoint(tokenIntrospectionEndpoint -> { })
//                // 10 提供了自定义OAuth2令牌吊销端点的功能。它定义了扩展点，允许您自定义OAuth2内省请求的预处理、主处理和后处理逻辑。
//                .tokenRevocationEndpoint(tokenRevocationEndpoint -> { })
//                // 11 提供了自定义OAuth2授权服务器元数据终结点的功能。它定义了一个扩展点，允许您自定义OAuth2授权服务器元数据响应。
                // 这个属于是对鉴权服务器里面的一些元数据修改，怎么处理一些逻辑什么的，他已经有默认的了，想自定义的话，就需要更高级的操作了
//                .authorizationServerMetadataEndpoint(authorizationServerMetadataEndpoint -> { })

                // 这个暂时不想搞 openid 的东西
//                .oidc(oidc -> oidc
//                        // 12
//                        .providerConfigurationEndpoint(providerConfigurationEndpoint -> { })
//                        // 13
//                        .userInfoEndpoint(userInfoEndpoint -> { })
//                        // 14
//                        .clientRegistrationEndpoint(clientRegistrationEndpoint -> { })
        );

        // 配置异常处理 // Redirect to the login page when not authenticated from the authorization endpoint
        http.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(
                new JsonExceptionAuthenticationEntryPoint("/login"
                        , globalExceptionReporter, globalPrinter))
        );


        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());    // Enable OpenID Connect 1.0

        DefaultSecurityFilterChain build = http.build();

        // 在这里设置是因为需要 http build 之后才能获取到 configures
        authenticationManagerAtomicReference.set(http.getSharedObject(AuthenticationManager.class));

        return build;
    }


    /**
     * 配置获取客户端的处理类，这里默认配置了一个使用 properties 获取客户端的处理类，也就是从 yaml 里面获取到客户端的配置信息
     * <br>
     * 这里，就可以自己去配置一个获取客户端的 bean 了比如从 Feign 远程调用？
     *
     * @param clientConfig 配置
     * @return 处理类
     */
    @Bean
    @ConditionalOnMissingBean(IClientDetailsHandle.class)
    public IClientDetailsHandle clientDetailsHandle(ClientConfig clientConfig) {
        return new PropertiesClientDetailsHandle(clientConfig);
    }

    /**
     * An instance of RegisteredClientRepository for managing clients.<br>
     * oauth2 用于第三方认证，RegisteredClientRepository 主要用于管理第三方（每个第三方就是一个客户端）
     * <br>
     * 如果没有配置 IClientDetailsHandle，就不会配置这个
     *
     * @return RegisteredClientRepository
     */
    @Bean
    @Order(1)
    @ConditionalOnMissingBean(RegisteredClientRepository.class)
    @ConditionalOnBean(IClientDetailsHandle.class)
    public RegisteredClientRepository customizeClientRepository(PasswordEncoder passwordEncoder
            , IClientDetailsHandle clientDetailsHandle) {
        return new CustomizeRegisteredClientRepository(clientDetailsHandle::getClientById, passwordEncoder::encode);
    }


    /**
     * An instance of java.security.KeyPair with keys generated on startup used to create the JWKSource above.<br>
     * 生成秘钥对，为jwkSource提供服务。
     *
     * @return KeyPair
     */
    @Bean
    @ConditionalOnMissingBean(KeyPair.class)
    public KeyPair keyPair(@Nullable RSAProperties properties) {
        if (ObjectUtil.isNotEmpty(properties)) {
            RSACoder.ini(properties);
            if (properties.getType().containsKey("JWT")) {
                return RSACoder.keyPair("JWT");
            }
        }
        properties = new RSAProperties();
        properties.setResource("jwt.jks");
        properties.setAlias("jwt");
        properties.setPassword("taybct");
        properties.setExpireCheck(true);
        return RSACoder.newKeyPair(properties);
    }

    @Bean
    public JWKSet jwkSet(KeyPair keyPair) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString(true))
                .build();
        return new JWKSet(rsaKey);
    }

    /**
     * An instance of com.nimbusds.jose.jwk.source.JWKSource for signing access tokens.<br>
     *
     * @return JWKSource
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return new ImmutableJWKSet<>(jwkSet);
    }


    /**
     * An instance of JwtDecoder for decoding signed access tokens.<br>
     * JwtDecoder的一个实例，用于解码签名的访问令牌。
     *
     * @param jwkSource JWKSource
     * @return JwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * An instance of AuthorizationServerSettings to configure Spring Authorization Server.<br>
     * 用于配置Spring Authorization Server的AuthorizationServerSettings实例。<br>
     * 就是可以配置鉴权服务器
     *
     * @return AuthorizationServerSettings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("https://www.taybctinfo.com")
                .authorizationEndpoint(authContextPath + "oauth/authorize")
                .tokenEndpoint(authContextPath + "oauth/login")
                .jwkSetEndpoint(authContextPath + "oauth/jwks")
                .tokenRevocationEndpoint(authContextPath + "oauth/revoke")
                .tokenIntrospectionEndpoint(authContextPath + "oauth/introspect")
                .oidcClientRegistrationEndpoint(authContextPath + "connect/register")
                .oidcUserInfoEndpoint(authContextPath + "userinfo")
                .build();
    }

    /**
     * jwt 加密
     *
     * @param jwkSource 签名
     * @return 加密器
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * 自定义添加修改 token 的内容，不过 headers 好像只能加，不能删？？？还是我打开的方式不对
     *
     * @return 自定义
     */
    @Bean
    @ConditionalOnMissingBean(OAuth2TokenCustomizer.class)
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(IUserDetailsHandle userDetailsHandle) {
        return context -> {
            // 头信息
            JwsHeader.Builder headers = context.getJwsHeader();
            // 载体信息
            JwtClaimsSet.Builder claims = context.getClaims();
            // 只有 access token 才添加
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                AuthorizationGrantType authorizationGrantType = context.getAuthorizationGrantType();
                String grant = authorizationGrantType.getValue();
                String clientId = context.getRegisteredClient().getClientId();
                Set<String> authorizedScopes = context.getAuthorizedScopes();
                UserDetails userDetails;
                // 授权码模式的用户
                User codeUser;
                if (context.getPrincipal() instanceof CustomizeAuthenticatedToken customizeAuthenticationToken) {
                    userDetails = customizeAuthenticationToken.getUserDetails();
                    codeUser = null;
                } else if (context.getPrincipal() instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
                    codeUser = (User) usernamePasswordAuthenticationToken.getPrincipal();
                    userDetails = null;
                } else {
                    userDetails = null;
                    codeUser = null;
                }
                if (userDetails != null) {
                    if (userDetails instanceof OAuth2UserDetails oAuth2UserDetails) {
                        if (oAuth2UserDetails.getGrantType() != null) {
                            grant = oAuth2UserDetails.getGrantType();
                        }
                    }
                }
                headers.type("JWT");
                // kid 替代不了 jti，这里主要是为了作一个唯一，知道每次登录都是新的登录
                headers.header(TokenConstants.JWT_JTI, UUID.randomUUID().toString(true));
                String finalGrant = grant;
                claims.claims(map -> {
                    // String sub = map.get("sub").toString();
                    map.remove("sub");
                    map.remove("aud");
                    // map.remove("nbf");
                    map.remove("iss");
                    // map.remove("iat");
                    map.put(JwtTokenKeyConstants.SCOPE, authorizedScopes);
                    map.put(JwtTokenKeyConstants.CLIENT_ID, clientId);
                    map.put(JwtTokenKeyConstants.GRANT_TYPE, finalGrant);
                    // 授权码模式，刷新 token 模式
                    if (finalGrant.equals(OAuth2GrantType.AUTHORIZATION_CODE)
                            || finalGrant.equals(AuthorizationGrantType.REFRESH_TOKEN.getValue())) {
                        if (codeUser != null) {
                            if (userDetailsHandle != null) {
                                // 这里直接再根据用户名获取一遍用户信息，所以这里建议 userDetailsHandle.getUserByUsername 使用缓存
                                Optional.ofNullable(userDetailsHandle.getUserByUsername(codeUser.getUsername()))
                                        .ifPresent(dto -> map.put(JwtTokenKeyConstants.USER_ID, dto.getUserId().toString()));
                            }
                            // 当然也是通过用户名认证的
                            map.put(JwtTokenKeyConstants.AUTH_M, OAuthenticationMethodType.USERNAME.value());
                            map.put(JwtTokenKeyConstants.USERNAME, codeUser.getUsername());
                            map.put(JwtTokenKeyConstants.AUTHORITIES, codeUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                        }
                    }
                    // 用户名密码登录模式
                    if (finalGrant.equals(OAuth2GrantType.TAYBCT)
                            || finalGrant.equals(OAuth2GrantType.SMS)) {
                        if (userDetails != null) {
                            if (userDetails instanceof OAuth2UserDetails oAuth2UserDetails) {
                                map.put(JwtTokenKeyConstants.USER_ID, oAuth2UserDetails.getUserId().toString());
                                map.put(JwtTokenKeyConstants.AUTH_M, oAuth2UserDetails.getAuthenticationMethod());
                            }
                            map.put(JwtTokenKeyConstants.USERNAME, userDetails.getUsername());
                            map.put(JwtTokenKeyConstants.AUTHORITIES, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
                        }
                    }
                    // 如果是其他方式，比如微信？
                    //
                });
            }
//            else if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
//                // Customize headers/claims for id_token
//
//            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(JwtAuthenticationConverter.class)
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(TokenConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(TokenConstants.JWT_AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        jwtAuthenticationConverter.setPrincipalClaimName(TokenConstants.USER_NAME_KEY);
        return jwtAuthenticationConverter;
    }

    /**
     * token 生成器
     *
     * @param jwtEncoder jwt 加密器
     * @return token 生成器
     */
    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JwtEncoder jwtEncoder, OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer) {
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(jwtCustomizer);
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }

    /**
     * 我在下面配置了默认的端点配置，主要是加上了用户名密码的鉴权模式，所以，如果还有其他的要添加的模式，你可以尝试去实现一个
     * {@linkplain IOtherTokenEndpointConfigurer IOtherTokenEndpointConfigurer}，然后可以参考
     * {@linkplain ICustomizeTokenEndpointConfigurer ICustomizeTokenEndpointConfigurer} 里面的写法自己配置这些端点
     *
     * @return IOtherTokenEndpointConfigurer
     */
    @Bean
    @ConditionalOnMissingBean(IOtherTokenEndpointConfigurer.class)
    public IOtherTokenEndpointConfigurer otherTokenEndpointConfigurer() {
        return tokenEndpoint -> {
        };
    }

    /**
     * 自定义 token 端点配置，这里注意一下：
     * <br>
     * {@link RestControllerAdvice } 在这里不好使
     * 也就是说，只要你不是抛出的 {@link OAuth2AuthenticationException}
     * ，就不会被 {@link AuthenticationFailureHandler} 拦截
     * ，也不会被 {@link RestControllerAdvice } 拦截，那，意思就是说
     * ，根本就不会返回给客户端任何提示了。。。属实是有点难搞，所以我这里还是老老实实按他的要求，配置好了异常返回。
     *
     * @param passwordEncoder             加密器
     * @param customizeUserDetailsService 自定义用户查询 Service
     * @return ICustomizeTokenEndpointConfigurer
     */
    @Bean
    @ConditionalOnMissingBean(ICustomizeTokenEndpointConfigurer.class)
    public ICustomizeTokenEndpointConfigurer customizeTokenEndpointConfigurer(PasswordEncoder passwordEncoder
            , ICustomizeUserDetailsService customizeUserDetailsService
            , OAuth2TokenGenerator<?> tokenGenerator
            , IUserDetailsHandle userDetailsHandle
            , RedisTemplate<Object, Object> redisTemplate
            , @Qualifier("otherTokenEndpointConfigurer") IOtherTokenEndpointConfigurer otherTokenEndpointConfigurer
            , OAuth2AuthorizationService authorizationService
            , IEncryptedPassable encryptedPassable
            , ResponseHandler responseHandler) {
        responseHandler.setSuccessResultConverter(this::printResponse);
        responseHandler.setOperationsBeforeOutput(authentication -> resultHandle(userDetailsHandle, authentication));
        return DefaultCustomizeTokenEndpointConfigurer.builder()
                .passwordEncoder(passwordEncoder)
                .customizeUserDetailsService(customizeUserDetailsService)
                .tokenGenerator(tokenGenerator)
                .errorResponseHandler(responseHandler)
                .accessTokenResponseHandler(responseHandler)
                .redisTemplate(redisTemplate)
                .authorizationService(authorizationService)
                .otherTokenEndpointConfigurer(otherTokenEndpointConfigurer)
                .encryptedPassable(encryptedPassable)
                .build();
    }

    /**
     * 打印结果给前端
     *
     * @param authentication 鉴权信息
     * @param response       response
     * @return 返回结果对象
     */
    private R<?> printResponse(Authentication authentication, HttpServletResponse response) {
        // TODO 这里可以返回自定义的 token 鉴权类型，但是这个和授权码模式返回的差太多了，不好搞，后面做统一鉴权的时候又麻烦，所以就都用他默认的吧
                    /*if (authentication instanceof CustomizeTokenAuthenticationToken token) {
                        JSONObject data = new JSONObject();
                        data.put("access_token", token.getAccessTokenValue());
                        data.put("authenticationMethod", token.getAuthenticationMethod());
                        data.put("expires_in", token.getExpiresIn());
                        data.put(AuthHeaderConstants.REFRESH_TOKEN_KEY, token.getRefreshTokenValue());
                        data.put("scope", token.getScope());
                        data.put("token_type", token.getTokenType());
                        return R.data(data);
                    }*/

        if (authentication instanceof OAuth2AccessTokenAuthenticationToken token) {
            JSONObject data = new JSONObject();

            OAuth2AccessToken accessToken = token.getAccessToken();
            JWT jwt = JWTUtil.parseToken(accessToken.getTokenValue());
            String grantType = (String) jwt.getPayload(JwtTokenKeyConstants.GRANT_TYPE);
            String jti = (String) jwt.getHeader(TokenConstants.JWT_JTI);

            data.put("access_token", accessToken.getTokenValue());
            data.put("access_token_exp", Optional.ofNullable(accessToken.getExpiresAt())
                    .map(i -> i.getEpochSecond() - System.currentTimeMillis() / 1000).orElse(0L));
            data.put("token_type", accessToken.getTokenType().getValue());
            data.put("scope", accessToken.getScopes());
            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken != null) {
                data.put(AuthHeaderConstants.REFRESH_TOKEN_KEY, refreshToken.getTokenValue());
                data.put("refresh_token_exp", Optional.ofNullable(refreshToken.getExpiresAt())
                        .map(i -> i.getEpochSecond() - System.currentTimeMillis() / 1000).orElse(0L));
            }
            data.put(TokenConstants.JWT_JTI, jti);
            data.put(TokenConstants.AUTHENTICATION_METHOD, Optional.ofNullable(jwt.getPayload(JwtTokenKeyConstants.AUTH_M)).orElse(grantType));
            // 帮忙给前端设置好 jti
            Cookie jtiCookie = new Cookie(applicationName.toUpperCase() + "-" + TokenConstants.JWT_JTI.toUpperCase(), jti);
            jtiCookie.setPath(contextPath);
            jtiCookie.setMaxAge(data.getInteger("access_token_exp"));
            response.addCookie(jtiCookie);
            return R.data(data);
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return R.fail();
    }

    /**
     * 鉴权结果处理
     *
     * @param userDetailsHandle 用户结果处理器
     * @param authentication    鉴权信息
     */
    private void resultHandle(IUserDetailsHandle userDetailsHandle, Authentication authentication) {
        if (authentication instanceof OAuth2AccessTokenAuthenticationToken token) {
            String clientId = token.getRegisteredClient().getClientId();
            OAuth2AccessToken accessToken = token.getAccessToken();
            JWT jwt = JWTUtil.parseToken(accessToken.getTokenValue());
            String grantType = (String) jwt.getPayload(JwtTokenKeyConstants.GRANT_TYPE);
            if (grantType.equals(OAuth2GrantType.TAYBCT)
                    || grantType.equals(OAuth2GrantType.SMS)
                    || grantType.equals(OAuth2GrantType.AUTHORIZATION_CODE)
                    || grantType.equals(AuthorizationGrantType.REFRESH_TOKEN.getValue())) {
                // 只有授权模式是 taybct 才会调用登录接口去登录
                JSONObject data = new JSONObject();
                String jti = (String) jwt.getHeader(TokenConstants.JWT_JTI);
                data.put(TokenConstants.JWT_JTI, jti);
                data.put("ip", ((WebAuthenticationDetails) token.getDetails()).getRemoteAddress());
                data.put(AuthHeaderConstants.CLIENT_ID_KEY, clientId);
                long exp = Optional.ofNullable(accessToken.getExpiresAt())
                        .map(Instant::getEpochSecond).orElseThrow(() -> new BaseException("超时时间为空！"));
                data.put(TokenConstants.JWT_EXP, exp);
                data.put(TokenConstants.AUTHENTICATION_METHOD, Optional.ofNullable(jwt.getPayload(JwtTokenKeyConstants.AUTH_M)).orElse(grantType));
                data.put(TokenConstants.USER_NAME_KEY, jwt.getPayload(JwtTokenKeyConstants.USERNAME));
                data.put(TokenConstants.USER_ID_KEY, jwt.getPayload(JwtTokenKeyConstants.USER_ID));
                data.put(LoginConstants.ACCESS_TOKEN, accessToken.getTokenValue());
                userDetailsHandle.login(data);
            }
        }
    }

}
