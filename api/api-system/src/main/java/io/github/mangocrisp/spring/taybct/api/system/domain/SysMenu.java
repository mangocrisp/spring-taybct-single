package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.DeleteLogicEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单<br>
 * sys_menu
 *
 * @author xijieyin <br> 2022/8/5 10:02
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "菜单")
public class SysMenu extends DeleteLogicEntity<Long, Long> implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 4125068315001321470L;

    /**
     * 菜单名
     */
    @NotBlank(message = "[菜单名]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "菜单名", required = true)
    @Length(max = 64, message = "编码长度不能超过64")
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 父级菜单
     */
    @Schema(description = "父级菜单", required = true)
    private Long parentId;
    /**
     * 是否收缩子菜单（当所有子菜单只有一个时，1、收缩，0不收缩）
     */
    @Schema(description = "是否收缩子菜单（当所有子菜单只有一个时，1、收缩，0不收缩）", required = true)
    @TableFieldDefault("1")
    private Byte alwaysShow;
    /**
     * 路由参数,JSON 字符串
     */
    @Schema(description = "路由参数,JSON 字符串")
    private String props;
    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;
    /**
     * 路由名
     */
    @Size(max = 32, message = "编码长度不能超过32")
    @Schema(description = "路由名", required = true)
    @Length(max = 32, message = "编码长度不能超过32")
    private String routeName;
    /**
     * 路由路径
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "路由路径", required = true)
    @Length(max = 255, message = "编码长度不能超过255")
    private String routePath;
    /**
     * 组件路径
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "组件路径", required = true)
    @Length(max = 255, message = "编码长度不能超过255")
    private String component;
    /**
     * 外链地址
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "外链地址")
    @Length(max = 255, message = "编码长度不能超过255")
    private String redirect;
    /**
     * 是否缓存（1 缓存 0 不缓存）
     */
    @Schema(description = "是否缓存（1缓存 0不缓存）")
    @TableFieldDefault("1")
    private Byte isCache;
    /**
     * 菜单类型（M目录 C菜单 L外部连接）
     */
    @Schema(description = "菜单类型（M目录 C菜单 L外部连接）")
    @TableFieldDefault(" ")
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    @Schema(description = "菜单状态（0显示 1隐藏）")
    @TableFieldDefault("0")
    private Byte hidden;
    /**
     * 菜单状态（1正常 0停用）
     */
    @Schema(description = "菜单状态（1正常 0停用）")
    @TableFieldDefault("1")
    private Byte status;
    /**
     * 菜单图标
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "菜单图标", required = true)
    @Length(max = 100, message = "编码长度不能超过100")
    private String icon;
    /**
     * 是否新开窗口 1 是 0 否
     */
    @Schema(description = "是否新开窗口 1 是 0 否")
    @TableFieldDefault("0")
    private Byte isBlank;

}
