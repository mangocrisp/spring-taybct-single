package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.bean.BaseEntity;
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
 * 权限管理表<br>
 * sys_permission
 *
 * @author xijieyin <br> 2022/8/5 10:04
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "权限")
public class SysPermission extends BaseEntity<Long, Long> implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 8863893883519506647L;
    /**
     * 权限分组
     */
    @Schema(description = "权限分组")
    private Long groupId;
    /**
     * 权限名
     */
    @NotBlank(message = "[权限名]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "权限名")
    @Length(max = 64, message = "编码长度不能超过64")
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 菜单id
     */
    @Schema(description = "菜单id")
    private Long menuId;
    /**
     * url 权限
     */
    @NotBlank(message = "[url 权限]不能为空")
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "url 权限", required = true)
    @Length(max = 128, message = "编码长度不能超过128")
    private String urlPerm;
    /**
     * 按钮权限
     */
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "按钮权限")
    @Length(max = 64, message = "编码长度不能超过64")
    private String btnPerm;

}
