package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
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
 * 系统参数<br>
 * sys_params
 *
 * @author xijieyin <br> 2022/8/5 10:03
 * @since 1.0.0
 */
@TableName(value = "sys_params")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统参数")
public class SysParams extends UniqueDeleteLogic<Long, Long> implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 2631649357273435920L;
    /**
     * 类型（字典-系统参数类型）
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "类型（字典-系统参数类型）")
    @Length(max = 128, message = "编码长度不能超过128")
    private String type;

    /**
     * 标题
     */
    @TableField(condition = SqlCondition.LIKE)
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "标题：系统标题")
    @Length(max = 128, message = "编码长度不能超过128")
    private String title;

    /**
     * 键
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "键 例：title")
    @Length(max = 128, message = "编码长度不能超过128")
    private String paramsKey;

    /**
     * 值
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "值 例：冠宣")
    @Length(max = 128, message = "编码长度不能超过128")
    private String paramsVal;

    /**
     * 是否可用 1可用、0不可用
     */
    @Schema(description = "是否可用")
    @TableFieldDefault("1")
    private Byte status;

    /**
     * 备注
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "备注")
    @Length(max = 255, message = "编码长度不能超过255")
    private String remark;


}