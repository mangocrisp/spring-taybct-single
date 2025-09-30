package io.github.mangocrisp.spring.taybct.auth.security.util;

import cn.hutool.core.convert.Convert;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeTokenAuthenticationToken;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalExceptionReporter;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalPrinter;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * 返回结果处理器
 * <br>
 * 这个注解用不了 {@link org.springframework.web.bind.annotation.RestControllerAdvice }，这里写一下统一的 response 返回结果处理类
 *
 * @author xijieyin <br> 2022/12/29 19:40
 */
@Slf4j
public class ResponseHandler implements AuthenticationFailureHandler
        , AuthenticationSuccessHandler
        , AuthenticationEntryPoint
        , AccessDeniedHandler {

    @Setter
    private BiFunction<Authentication, HttpServletResponse, R<?>> successResultConverter;
    @Setter
    private IGlobalExceptionReporter globalExceptionReporter;
    private final IGlobalPrinter globalPrinter;

    /**
     * 其他的操作，这里，指的是希望使用 Authentication 做点什么，这个操作会在往前端输出之前操作
     */
    @Setter
    private Consumer<Authentication> operationsBeforeOutput = authentication -> {
    };

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    protected ApplicationEventPublisher eventPublisher;

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private AuthenticationManager authenticationManager;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private RememberMeServices rememberMeServices = new NullRememberMeServices();

    private RequestMatcher requiresAuthenticationRequestMatcher;

    private boolean continueChainBeforeSuccessfulAuthentication = false;

    private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

    private boolean allowSessionCreation = true;

    private AuthenticationSuccessHandler successHandler = this;

    private AuthenticationFailureHandler failureHandler = this;

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/oauth/login", "POST");

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public ResponseHandler(IGlobalExceptionReporter globalExceptionReporter, IGlobalPrinter globalPrinter) {
        this.globalExceptionReporter = globalExceptionReporter;
        this.globalPrinter = globalPrinter;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        failure(request, response, exception);
        //unsuccessfulAuthentication(request, response, exception);
        saveException(request, exception);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        failure(request, response, exception);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        failure(request, response, exception);
    }

    public void failure(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        Throwable e = exception;
        if (exception instanceof OAuth2AuthenticationException oAuth2AuthenticationException) {
            OAuth2Error error = oAuth2AuthenticationException.getError();
            e = new BaseException(String.format("%s%s"
                    , Convert.toStr(error.getErrorCode(), "")
                    , Convert.toStr(error.getDescription(), "")
            ), HttpStatus.UNAUTHORIZED);
        }
        globalExceptionReporter.recording(request, e);
        globalPrinter.print(e, response);
    }

    /**
     * Caches the {@code AuthenticationException} for use in view rendering.
     * <p>
     * If {@code forwardToDestination} is set to true, request scope will be used,
     * otherwise it will attempt to store the exception in the session. If there is no
     * session and {@code allowSessionCreation} is {@code true} a session will be created.
     * Otherwise the exception will not be stored.
     */
    protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        HttpSession session = request.getSession(false);
        if (session != null || this.allowSessionCreation) {
            request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication == null) {
            // return immediately as subclass has indicated that it hasn't completed
            return;
        }
        if (this.continueChainBeforeSuccessfulAuthentication) {
            chain.doFilter(request, response);
        }
        // 如果是自定义的登录 token 要缓存起来方便后面做登录
        if (authentication instanceof CustomizeTokenAuthenticationToken customizeTokenAuthenticationToken) {
            Authentication successAuthentication = createSuccessAuthentication(customizeTokenAuthenticationToken);
            // 清理掉 session
            //this.sessionStrategy.onAuthentication(successAuthentication, request, response);
            successfulAuthentication(request, response, successAuthentication);
        }
        operationsBeforeOutput.accept(authentication);
        globalPrinter.outputResponse(response, successResultConverter.apply(authentication, response));
        clearAuthenticationAttributes(request);
        if (!this.continueChainBeforeSuccessfulAuthentication) {
            chain.doFilter(request, response);
        }
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 如果是自定义的登录 token 要缓存起来方便后面做登录
        if (authentication instanceof CustomizeTokenAuthenticationToken customizeTokenAuthenticationToken) {
            Authentication successAuthentication = createSuccessAuthentication(customizeTokenAuthenticationToken);
            // 清理掉 session
            //this.sessionStrategy.onAuthentication(successAuthentication, request, response);
            successfulAuthentication(request, response, successAuthentication);
        }

        operationsBeforeOutput.accept(authentication);
        globalPrinter.outputResponse(response, successResultConverter.apply(authentication, response));
        clearAuthenticationAttributes(request);
    }

    /**
     * 创建可以用于存储在 session holder 里面的数据
     *
     * @param authentication 自定义的 token
     * @return OAuth2 授权服务器可以接受的 token
     */
    protected Authentication createSuccessAuthentication(Authentication authentication) {
        UsernamePasswordAuthenticationToken result;
        if (authentication instanceof CustomizeTokenAuthenticationToken token) {
            result = UsernamePasswordAuthenticationToken.authenticated(token.getUserDetails(),
                    token.getCredentials(), token.getAuthorities());
            result.setDetails(authentication.getDetails());
        } else {
            result = UsernamePasswordAuthenticationToken.authenticated(authentication.getPrincipal(),
                    authentication.getCredentials(), authentication.getAuthorities());
            result.setDetails(authentication.getDetails());
        }
        log.debug("Authenticated user");
        return result;
    }

    /**
     * Removes temporary authentication-related data which may have been stored in the
     * session during the authentication process.
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        request.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    /**
     * Default behaviour for successful authentication.
     * <pre>
     * Sets the successful <b>Authentication</b> object on the
     * {@link SecurityContextHolder}
     * Informs the configured <b>RememberMeServices</b> of the successful login
     * Fires an {@link InteractiveAuthenticationSuccessEvent} via the configured
     * <b>ApplicationEventPublisher</b>
     * Delegates additional behaviour to the
     * {@link AuthenticationSuccessHandler}.
     * </pre>
     * <p>
     * Subclasses can override this method to continue the {@link FilterChain} after
     * successful authentication.
     * </p>
     *
     * @param authentication the object returned from the <b>attemptAuthentication</b>
     *                       method.
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) {
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);

        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        if (log.isDebugEnabled()) {
            log.debug("Set SecurityContextHolder to {}", authentication);
        }
        this.rememberMeServices.loginSuccess(request, response, authentication);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authentication, this.getClass()));
        }
        // this.successHandler.onAuthenticationSuccess(request, response, authResult);
        // onAuthenticationSuccess(request, response, authResult);
    }

    /**
     * Default behaviour for unsuccessful authentication.
     * <pre>
     * Clears the {@link SecurityContextHolder}
     * Stores the exception in the session (if it exists or
     * <b>allowSesssionCreation</b> is set to <b>true</b>)
     * Informs the configured <b>RememberMeServices</b> of the failed login
     * Delegates additional behaviour to the
     * {@link AuthenticationFailureHandler}.
     * </pre>
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        this.securityContextHolderStrategy.clearContext();
        log.trace("Failed to process authentication request", failed);
        log.trace("Cleared SecurityContextHolder");
        log.trace("Handling authentication failure");
        this.rememberMeServices.loginFail(request, response);
        // this.failureHandler.onAuthenticationFailure(request, response, failed);
        failure(request, response, failed);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    public RememberMeServices getRememberMeServices() {
        return this.rememberMeServices;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        Assert.notNull(rememberMeServices, "rememberMeServices cannot be null");
        this.rememberMeServices = rememberMeServices;
    }

    /**
     * Indicates if the filter chain should be continued prior to delegation to
     * {@code #successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
     * , which may be useful in certain environment (such as Tapestry applications).
     * Defaults to <code>false</code>.
     */
    public void setContinueChainBeforeSuccessfulAuthentication(boolean continueChainBeforeSuccessfulAuthentication) {
        this.continueChainBeforeSuccessfulAuthentication = continueChainBeforeSuccessfulAuthentication;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected boolean getAllowSessionCreation() {
        return this.allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

    /**
     * <pre>
     * The session handling strategy which will be invoked immediately after an
     * authentication request is successfully processed by the
     * <b>AuthenticationManager</b>. Used, for example, to handle changing of the
     * session identifier to prevent session fixation attacks.
     * </pre>
     *
     * @param sessionStrategy the implementation to use. If not set a null implementation
     *                        is used.
     */
    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    /**
     * Sets the strategy used to handle a successful authentication. By default a
     * {@link SavedRequestAwareAuthenticationSuccessHandler} is used.
     */
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    /**
     * Sets the {@link SecurityContextRepository} to save the {@link SecurityContext} on
     * authentication success. The default action is not to save the
     * {@link SecurityContext}.
     *
     * @param securityContextRepository the {@link SecurityContextRepository} to use.
     *                                  Cannot be null.
     */
    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull(securityContextRepository, "securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    /**
     * Sets the {@link SecurityContextHolderStrategy} to use. The default action is to use
     * the {@link SecurityContextHolderStrategy} stored in {@link SecurityContextHolder}.
     *
     * @since 5.8
     */
    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    protected AuthenticationSuccessHandler getSuccessHandler() {
        return this.successHandler;
    }

    protected AuthenticationFailureHandler getFailureHandler() {
        return this.failureHandler;
    }

}
