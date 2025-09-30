package io.github.mangocrisp.spring.taybct.auth.security.pojo;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.common.enums.OAuthUserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Security 鉴权用户，这个用户会被 OAuth2 拿去加密到 token 里面，所以这里的字段越长，token 越长
 *
 * @author xijieyin <br> 2022/8/5 12:02
 * @since 1.0.0
 */
@Data
public class OAuth2UserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = -2985234228687876343L;
    /**
     * 用户 id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 授权类型,这个在刷新模式的时候会用到,用来指定原来是怎么授权的,因为是刷新 token ,所以也得指定清楚之前是什么模式,后面才好根据授权类型来做一些判断
     * ,比如定制 jwt token 的细节这些
     */
    private String grantType;
    /**
     * 认证方式,与授权类型不同,授权类型是给系统看让系统知道该怎么认证,认证方式是给管理人员看,让管理员知道,当前登录的用户是用什么登录的
     */
    private String authenticationMethod;
    /**
     * <pre>
     *     鉴权通过怎样的信息去获取到用户信息，这个信息指的是例如使用用户名获取到了用户信息就存储用户名，刷新 token 可以利用这个值来实现
     * </pre>
     */
    private Object principal;

    public OAuth2UserDetails setPrincipal(Object principal) {
        this.principal = principal;
        return this;
    }

    public OAuth2UserDetails setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
        return this;
    }

///////////////////////////////////////////////< 扩展字段
///////////////////////////////////////////////> 默认字段

    /**
     * 默认字段
     */
    private String username;
    private String password;
    /**
     * 用户是否锁定
     */
    private Boolean locked;
    /**
     * 用户是否能用
     */
    private Boolean enabled = true;
    private Collection<SimpleGrantedAuthority> authorities;

    /**
     * 生成 OAuth2 需要的用户
     *
     * @param dto 用户信息数据传输对象
     */
    public OAuth2UserDetails(OAuth2UserDTO dto) {
        this.userId = dto.getUserId();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.locked = dto.getStatus().equals(OAuthUserStatus.FREEZE.status());
        if (!locked) {
            this.enabled = dto.getStatus().equals(OAuthUserStatus.ENABLE.status());
        }
        if (CollectionUtil.isNotEmpty(dto.getRoles())) {
            authorities = new ArrayList<>();
            dto.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
    }

    /**
     * 权限列表
     *
     * @return {@code Collection<? extends GrantedAuthority>}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * 密码
     *
     * @return String
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * 用户名
     *
     * @return String
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 指示用户的帐户是否已过期。无法验证过期的帐户。
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指示用户是锁定还是解锁。无法验证锁定的用户。
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    /**
     * 指示用户的凭据（密码）是否已过期。过期的凭据阻止身份验证。
     *
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 指示用户是启用还是禁用。无法对禁用的用户进行身份验证。
     *
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
