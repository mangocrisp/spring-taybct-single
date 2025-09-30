package io.github.mangocrisp.spring.taybct.api.system.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 路由权限列表
 *
 * @author xijieyin <br> 2022/8/5 10:16
 * @since 1.0.0
 */
@Data
public class RouterPerm implements Serializable {
    @Serial
    private static final long serialVersionUID = 8342593364127403353L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 菜单 id
     */
    private Long menuId;
    /**
     * 权限名
     */
    private String name;
    /**
     * url 访问权限
     */
    private String urlPerm;
    /**
     * 按钮权限
     */
    private String btnPerm;
}
