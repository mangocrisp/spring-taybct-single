package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.bean.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 权限分组<br>
 * sys_permission_group
 *
 * @author xijieyin <br> 2022/9/23 9:28
 * @since 1.0.4
 */
@TableName(value = "sys_permission_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "权限分组")
public class SysPermissionGroup extends BaseEntity<Long, Long> implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 728614846840580241L;
    /**
     * 组名
     */
    @TableField(condition = SqlCondition.LIKE)
    @Size(max = 64, message = "编码长度不能超过128")
    @Schema(description = "组名")
    @Length(max = 64, message = "编码长度不能超过128")
    private String name;

}