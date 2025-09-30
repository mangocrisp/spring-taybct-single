package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.tool.core.util.tree.TreeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 路由
 *
 * @author xijieyin <br> 2022/8/5 10:16
 * @since 1.0.0
 */
@Data
@Slf4j
public class RouterVO implements Serializable, TreeUtil.Tree<RouterVO> {
    @Serial
    private static final long serialVersionUID = -9020910096227102666L;
    /**
     * 主键 id
     */
    private Long id;
    /**
     * 菜单名
     */
    private String title;
    /**
     * 父级 id
     */
    private Long parentId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否收缩子菜单（当所有子菜单只有一个时，0、收缩，1不收缩）
     */
    private Byte alwaysShow;
    /**
     * 访问地址
     */
    private String path;
    /**
     * 路由名（唯一）
     */
    private String name;
    /**
     * 跳转链接，一般是父级是 Layout 级别的菜单需要跳转默认的子级路由用的
     */
    private String redirect;
    /**
     * 组件路径 /src/views/xx/x.vue
     */
    private String component;
    /**
     * 路由参数,JSON
     */
    private String props;
    /**
     * 是否隐藏
     */
    private Byte hidden;
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
     * 自定义的一些属性
     */
    private RouterMeta meta = new RouterMeta();
    /**
     * 按钮权限列表
     */
    private Set<RouterPerm> permissions;
    /**
     * 用户拥有的角色关联的权限列表
     */
    private Set<RouterPerm> rolePermissions;
    /**
     * 子集菜单
     */
    private List<RouterVO> children;

}
