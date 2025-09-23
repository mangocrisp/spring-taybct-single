package io.github.mangocrisp.spring.taybct.single.config;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mangocrisp.spring.taybct.auth.security.filter.CaptchaFilter;
import io.github.mangocrisp.spring.taybct.auth.security.filter.PasswordCheckFilter;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeTokenAuthenticationToken;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.handle.PasswordExceptionReporter;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.auth.security.support.OAuth2SqliteAuthorizationRowMapper;
import io.github.mangocrisp.spring.taybct.auth.security.support.OAuth2UserMixin;
import io.github.mangocrisp.spring.taybct.auth.service.IRegisteredService;
import io.github.mangocrisp.spring.taybct.common.prop.SecureProp;
import io.github.mangocrisp.spring.taybct.module.system.handle.AuthServeClientHandle;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserService;
import io.github.mangocrisp.spring.taybct.single.handle.AuthServeClientSingleHandle;
import io.github.mangocrisp.spring.taybct.single.handle.AuthUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.single.handle.ExcelDictHandlerImpl;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalExceptionReporter;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.service.IExcelService;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.service.impl.ExcelServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.util.EasyPOIUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.util.List;

/**
 * @author xijieyin <br> 2023/1/3 14:11
 */
@AutoConfiguration
@AutoConfigureOrder(Integer.MIN_VALUE)
public class SingleAuthConfig {

    @Bean
    public IUserDetailsHandle feignUserDetailsHandle(ISysUserService sysUserService, ISysUserOnlineService sysUserOnlineService) {
        return new AuthUserDetailsHandle(sysUserService, sysUserOnlineService);
    }

    @Bean
    public AuthServeClientHandle authServeClientHandle(IRegisteredService registeredService) {
        return new AuthServeClientSingleHandle(registeredService);
    }

    /**
     * 验证码过滤器
     *
     * @param redisTemplate          redis操作
     * @param sysParamsObtainService 系统参数获取
     * @param prop                   配置参数
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<CaptchaFilter> captchaFilter(StringRedisTemplate redisTemplate
            , ISysParamsObtainService sysParamsObtainService
            , SecureProp prop) {
        FilterRegistrationBean<CaptchaFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CaptchaFilter(redisTemplate, sysParamsObtainService, prop));
        registrationBean.addUrlPatterns("/auth/oauth/login");
        registrationBean.setName("CaptchaFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

    /**
     * 密码验证过滤器,密码验证失败一定次数就不给过了
     *
     * @param redisTemplate redis 操作
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<PasswordCheckFilter> passwordCheckFilter(RedisTemplate<String, Integer> redisTemplate) {
        FilterRegistrationBean<PasswordCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PasswordCheckFilter(redisTemplate));
        registrationBean.addUrlPatterns("/auth/oauth/login");
        registrationBean.setName("PasswordCheckFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 3);
        return registrationBean;
    }

    /**
     * 全局异常捕获记录器
     *
     * @return IGlobalExceptionReporter
     */
    @Bean
    public IGlobalExceptionReporter globalExceptionReporter(RedisTemplate<String, Integer> redisTemplate) {
        return new PasswordExceptionReporter(redisTemplate);
    }

//    @Bean
//    public DataSourceInitEvent encryptedDataSourceInitEvent(EncryptedDataSourceProperties properties) {
//        return new EncryptedDataSourceInitEvent(properties);
//    }

    @Bean
    public IExcelDictHandler excelDictHandler(ISysDictService sysDictService) {
        IExcelDictHandler excelDictHandler = new ExcelDictHandlerImpl(sysDictService);
        EasyPOIUtil.excelDictHandler = excelDictHandler;
        return excelDictHandler;
    }

    @Bean
    public IExcelService excelService() {
        return new ExcelServiceImpl();
    }

    /**
     * 因为用到了 SQLite，类型转换会有问题，这里做一下类型解析器
     *
     * @param jdbcTemplate               数据源
     * @param registeredClientRepository 注册
     * @return OAuth2AuthorizationService
     */
//    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate
            , RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        OAuth2SqliteAuthorizationRowMapper authorizationRowMapper =
                new OAuth2SqliteAuthorizationRowMapper(jdbcTemplate, registeredClientRepository);
        authorizationRowMapper.setLobHandler(new DefaultLobHandler());
        authorizationRowMapper.setObjectMapper(objectMapper());
        service.setAuthorizationRowMapper(authorizationRowMapper);
        return service;
    }

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        //放入自定义的user类
        objectMapper.addMixIn(OAuth2UserDetails.class, OAuth2UserMixin.class);
        objectMapper.addMixIn(CustomizeTokenAuthenticationToken.class, OAuth2UserMixin.class);
        return objectMapper;
    }
}
