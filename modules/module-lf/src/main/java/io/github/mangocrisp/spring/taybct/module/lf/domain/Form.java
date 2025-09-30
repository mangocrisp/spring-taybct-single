package io.github.mangocrisp.spring.taybct.module.lf.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldJSON;
import io.github.mangocrisp.spring.taybct.tool.core.bean.DeleteLogicEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 流程表单
 *
 * <br>TableName lf_form
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "lf_form")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程表单")
public class Form extends DeleteLogicEntity<Long, Long> {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "名称")
    @Length(max = 64, message = "编码长度不能超过64")
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 状态(0未发布，1已经发布)
     */
    @Schema(description = "状态(0未发布，1已经发布)")
    @TableFieldDefault("0")
    private Integer status;
    /**
     * 备注说明
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "备注说明")
    @Length(max = 255, message = "编码长度不能超过255")
    @TableField(condition = SqlCondition.LIKE)
    private String description;
    /**
     * 数据（实时设计最新的表单数据）
     */
    @Schema(description = "数据（实时设计最新的表单数据）")
    @TableFieldJSON
    private Object data;
    /**
     * 表单类型，是表单还是单组件（字典项 lf_form_type）
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "表单类型，是表单还是单组件（字典项 lf_form_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String type;
    /**
     * 表单组件路径
     */
    @Size(max = 500, message = "编码长度不能超过500")
    @Schema(description = "表单组件路径")
    @Length(max = 500, message = "编码长度不能超过500")
    private String path;
    /**
     * 最后版本号
     */
    @Schema(description = "最后版本号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lastVersion;

}