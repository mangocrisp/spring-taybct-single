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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * 流程表单发布表
 *
 * <br>TableName lf_form_release
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "lf_form_release")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程发布表")
public class FormRelease extends DeleteLogicEntity<Long, Long> {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 表单 id
     */
    @NotNull(message = "[表单 id]不能为空")
    @Schema(description = "表单 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long formId;
    /**
     * 发布名称
     */
    @NotBlank(message = "[发布名称]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "发布名称")
    @Length(max = 64, message = "编码长度不能超过64")
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 状态(0 关闭 1 打开)
     */
    @Schema(description = "状态(0 关闭 1 打开)")
    @TableFieldDefault("1")
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
     * 数据（每个版本的数据）
     */
    @NotNull(message = "[数据（每个版本的数据）]不能为空")
    @Schema(description = "数据（每个版本的数据）")
    @TableFieldJSON
    private Object data;
    /**
     * 版本号（yyyyMMddHHmmss）
     */
    @NotNull(message = "[版本号（yyyyMMddHHmmss）]不能为空")
    @Schema(description = "版本号（yyyyMMddHHmmss）")
    @TableFieldDefault(expression = "T(java.lang.Long).valueOf(T(java.time.LocalDateTime).now().format(T(java.time.format.DateTimeFormatter).ofPattern(\"yyyyMMddHHmmss\", T(java.util.Locale).CHINA)))")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long version;
    /**
     * 表单类型，是表单还是单组件（字典项 lf_form_type）
     */
    @NotNull(message = "[表单类型]不能为空")
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

}