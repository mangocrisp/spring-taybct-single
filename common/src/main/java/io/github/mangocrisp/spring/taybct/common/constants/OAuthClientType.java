package io.github.mangocrisp.spring.taybct.common.constants;

/**
 * 授权客户端类型
 *
 * @author xijieyin <br> 2022/8/5 18:30
 * @since 1.0.0
 */
public interface OAuthClientType {
    /**
     * 管理 pc 端
     */
    String ADMIN_PC = "taybct_pc";
    /**
     * App h5 端
     */
    String H5_APP = "taybct_h5";
    /**
     * 微信小程序端
     */
    String WECHAT_APP = "taybct_wechat";

}
