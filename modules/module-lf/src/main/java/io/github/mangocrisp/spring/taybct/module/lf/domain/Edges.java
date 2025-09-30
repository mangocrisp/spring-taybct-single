package io.github.mangocrisp.spring.taybct.module.lf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldJSON;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程连线表
 *
 * <br>TableName lf_edges
 */
@TableName(value = "lf_edges")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程连线表")
public class Edges implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -6031451599347610821L;

    /**
     * 边id
     */
    @NotBlank(message = "[边id]不能为空")
    @Size(max = 36, message = "编码长度不能超过36")
    @Schema(description = "边id")
    @Length(max = 36, message = "编码长度不能超过36")
    @TableFieldDefault(expression = "T(com.baomidou.mybatisplus.core.toolkit.IdWorker).getId()")
    @TableId
    private String id;
    /**
     * 起始节点 id
     */
    @NotBlank(message = "[起始节点 id]不能为空")
    @Size(max = 36, message = "编码长度不能超过36")
    @Schema(description = "起始节点 id")
    @Length(max = 36, message = "编码长度不能超过36")
    private String sourceNodeId;
    /**
     * 指向节点 id
     */
    @NotBlank(message = "[指向节点 id]不能为空")
    @Size(max = 36, message = "编码长度不能超过36")
    @Schema(description = "指向节点 id")
    @Length(max = 36, message = "编码长度不能超过36")
    private String targetNodeId;
    /**
     * 线的属性数据
     */
    @Schema(description = "线的属性数据")
    @TableFieldJSON
    private Object properties;
    /**
     * 线上的文字
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "线上的文字")
    @Length(max = 255, message = "编码长度不能超过255")
    private String text;
    /**
     * 线类型（字典项 lf_node_type）
     */
    @NotBlank(message = "[线类型（字典项 lf_node_type）]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "线类型（字典项 lf_node_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String type;
    /**
     * 流程 id
     */
    @NotNull(message = "[流程 id]不能为空")
    @Schema(description = "流程 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long processId;

}
