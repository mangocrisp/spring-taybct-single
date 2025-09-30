package io.github.mangocrisp.spring.taybct.common.constants;

/**
 * OAuth2 授权模式
 *
 * @author xijieyin <br> 2022/8/5 18:29
 * @since 1.0.0
 */
public interface OAuth2GrantType {

    /**
     * 授权码
     */
    String AUTHORIZATION_CODE = "authorization_code";
    /**
     * 简化模式，需要多一个验证服务器
     */
    String IMPLICIT = "implicit";
    /**
     * 密码模式
     */
    String PASSWORD = "password";
    /**
     * 客户端模式，没有用户
     */
    String CLIENT_CREDENTIALS = "client_credentials";
    /**
     * 刷新 token 令牌
     */
    String REFRESH_TOKEN = "refresh_token";
    /**
     * 同步密码模式
     */
    String TAYBCT = "taybct";
    /**
     * 刷新模式
     */
    String TAYBCT_REFRESH = "taybct_refresh";
    /**
     * 微信二维码
     */
    String WECHAT_QR_CODE = "wechat_qr_code";
    /**
     * 手机号，短信
     */
    String SMS = "sms";
}
