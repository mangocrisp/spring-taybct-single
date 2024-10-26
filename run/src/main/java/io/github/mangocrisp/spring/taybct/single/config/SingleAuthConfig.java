package io.github.mangocrisp.spring.taybct.single.config;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import io.github.mangocrisp.spring.taybct.auth.security.filter.CaptchaFilter;
import io.github.mangocrisp.spring.taybct.auth.security.filter.LoginFilter;
import io.github.mangocrisp.spring.taybct.auth.security.filter.PasswordCheckFilter;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.handle.PasswordExceptionReporter;
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

import java.security.KeyPair;

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
     * 登录过滤器,过滤请求的时候加密的请求头信息,解密成正确的后放行
     *
     * @param keyPair 密钥对
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter(KeyPair keyPair) {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter(keyPair));
        registrationBean.addUrlPatterns("/auth/oauth/login");
        registrationBean.setName("LoginFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
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
}
