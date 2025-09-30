package io.github.mangocrisp.spring.taybct.api.system.dto;

import io.github.mangocrisp.spring.taybct.api.system.handle.PasswordHandler;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElement;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElements;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 鉴权用户数据传输对象
 *
 * @author xijieyin <br> 2022/8/5 10:07
 * @since 1.0.0
 */
@Data
@EnhanceElements
public class OAuth2UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6164869247773293204L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    @EnhanceElement(parameterHandler = {PasswordHandler.En.class}, resultHandler = {PasswordHandler.De.class})
    private String password;

    /**
     * 用户状态：OAuthUserStatus
     */
    private Byte status;

    /**
     * 用户角色编码集合 ["ADMIN","NORMAL","LEADER"...]
     */
    private List<String> roles;

    /*
      TODO 后续可以选择租户，即登录成功后，去查询这个用户拥有的租户可以选择，然后也可以去查找，或者申请加入某些租户
       使用 jti 作 key ，租户 id 作值存在缓存（redis）里面然后，一个 jti 对应一个租户表示这个用户这次登录选择的是什么租户，
       这样去区分这个用户当前登录的是什么租户

      租户 id
     */

}
