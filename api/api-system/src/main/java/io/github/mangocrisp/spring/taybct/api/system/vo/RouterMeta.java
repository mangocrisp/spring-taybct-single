package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 路由 meta 配置
 *
 * @author xijieyin <br> 2022/8/5 10:16
 * @since 1.0.0
 */
@Data
public class RouterMeta implements Serializable {
    @Serial
    private static final long serialVersionUID = 8805140024887745267L;
    /**
     * 菜单 id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否新开窗口 1 是 0 否
     */
    @Schema(description = "是否新开窗口 1 是 0 否")
    private Byte isBlank;
    /**
     * 是否需要缓存
     */
    private Byte isCache;
    /**
     * 菜单类型（M目录 C菜单 L外部连接）
     */
    private String menuType;
    /**
     * 按钮权限列表
     */
    private Set<RouterPerm> permissions;
}
