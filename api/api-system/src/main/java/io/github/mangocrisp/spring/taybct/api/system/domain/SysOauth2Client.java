package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 客户端<br>
 * sys_oauth2_client
 *
 * @author xijieyin <br> 2022/8/5 10:03
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "客户端")
@TableName("sys_oauth2_client")
public class SysOauth2Client extends UniqueDeleteLogic<Long, Long> implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = -6949781146235908956L;

    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID")
    private String clientId;
    /**
     * 客户端名,可以用来描述客户端
     */
    @Schema(description = "客户端名")
    private String clientName;
    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥")
    private String clientSecret;
    /**
     * 资源id列表
     */
    @Schema(description = "资源id列表")
    private String resourceIds;
    /**
     * 域 逗号隔开
     */
    @Schema(description = "域")
    private String scope;
    /**
     * 授权模式 ，字符串，逗号隔开可以设置多种模式
     * <br>
     * authorization_code：授权码模式（官方推荐），适合第三方登录 client -- 带着 client id 去获取授权码 -- 需要登录验证 -- 验证通过后获取到授权码 -- 用获取到的授权码去请求获取 token -- 拿到 token 才能请求资源
     * <br>
     * implicit：简化模式（不常用） ，不再需要授权码 直接就返回了 token ，但是这个第一步的 token 是不能直接用的，是存在 Fragment 里面的 ，需要到另一个 Web-Hosted Client 服务器去解析，这个服务器会返回一个 script 让客户端来解析，解析之后的 token 就是我们需要的 token了
     * <br>
     * password：密码模式（用得也不多） ，用户直接输入密码去授权服务器获取 token
     * <br>
     * client_credentials：客户端模式 （不常用），直接认证服务器获取 token
     * <br>
     * refresh_token: 刷新令牌
     */
    @Schema(description = "授权方式")
    private String authorizedGrantTypes;
    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    private String webServerRedirectUri;
    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private String authorities;
    /**
     * 认证令牌时效
     */
    @Schema(description = "认证令牌时效")
    private Long accessTokenValidity;
    /**
     * 刷新令牌时效
     */
    @Schema(description = "刷新令牌时效")
    private Long refreshTokenValidity;
    /**
     * 扩展信息
     */
    @Schema(description = "扩展信息")
    private String additionalInformation;
    /**
     * 是否自动放行
     */
    @Schema(description = "是否自动放行")
    private String autoApprove;

}
