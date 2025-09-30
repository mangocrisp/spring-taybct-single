package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
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
 * 角色<br>
 * sys_role
 *
 * @author xijieyin <br> 2022/8/5 10:05
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "角色")
public class SysRole extends UniqueDeleteLogic<Long, Long> implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = -9005601082403765114L;
    /**
     * 角色名
     */
    @NotBlank(message = "[角色名]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "角色名")
    @Length(max = 64, message = "编码长度不能超过64")
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 角色代码
     */
    @NotBlank(message = "[角色代码]不能为空")
    @Size(max = 32, message = "编码长度不能超过32")
    @Schema(description = "角色代码")
    @Length(max = 32, message = "编码长度不能超过32")
    private String code;
    /**
     * 排序
     */
    @Schema(description = "排序")
    @TableFieldDefault("1")
    private Integer sort;
    /**
     * 状态(1 有效 0 无效  2 冻结)
     */
    @Schema(description = "状态(1 有效 0 无效  2 冻结)")
    @TableFieldDefault("1")
    private Byte status;

}
