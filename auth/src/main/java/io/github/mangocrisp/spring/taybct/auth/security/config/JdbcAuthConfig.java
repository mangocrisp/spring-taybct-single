package io.github.mangocrisp.spring.taybct.auth.security.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeTokenAuthenticationToken;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.auth.security.support.OAuth2UserMixin;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.util.List;

/**
 * @author xijieyin <br> 2023/1/3 13:48
 */
@ConditionalOnClass(JdbcTemplate.class)
@AutoConfiguration
public class JdbcAuthConfig {
    /**
     * An instance of RegisteredClientRepository for managing clients.<br>
     * oauth2 用于第三方认证，RegisteredClientRepository 主要用于管理第三方（每个第三方就是一个客户端）
     * <br>
     * 如果没有配置 IClientDetailsHandle，就不会配置这个
     *
     * @return RegisteredClientRepository
     */
    @Bean
    @Order(0)
    @ConditionalOnMissingBean(RegisteredClientRepository.class)
    public RegisteredClientRepository jdbcClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(OAuth2AuthorizationService.class)
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate
            , RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper authorizationRowMapper =
                new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
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

    @Bean
    @ConditionalOnMissingBean(OAuth2AuthorizationConsentService.class)
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate
            , RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    // TODO 这里用户就不用他这个 jdbc 管理了，客户端的话，因为可能会存一个会话一样的东西，会存储 code 和 token 之间的关系，所以这里就不用 jdbc 了
//    @Bean
//    @ConditionalOnMissingBean(UserDetailsService.class)
//    @ConditionalOnClass(JdbcTemplate.class)
//    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

}
