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
 * 流程节点
 *
 * <br>TableName lf_nodes
 */
@TableName(value = "lf_nodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程节点")
public class Nodes implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 8819273502304462334L;

    /**
     * 主键（节点的id，这里是使用前端生成的 uuid）
     */
    @NotBlank(message = "[主键（节点的id，这里是使用前端生成的 uuid）]不能为空")
    @Size(max = 36, message = "编码长度不能超过36")
    @Schema(description = "主键（节点的id，这里是使用前端生成的 uuid）")
    @Length(max = 36, message = "编码长度不能超过36")
    @TableFieldDefault(expression = "T(com.baomidou.mybatisplus.core.toolkit.IdWorker).getId()")
    @TableId
    private String id;
    /**
     * 流程 id
     */
    @Schema(description = "流程 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long processId;
    /**
     * 节点的属性数据
     */
    @NotNull(message = "[节点的属性数据]不能为空")
    @Schema(description = "节点的属性数据")
    @TableFieldJSON
    private Object properties;
    /**
     * 节点上的文字
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "节点上的文字")
    @Length(max = 255, message = "编码长度不能超过255")
    private String text;
    /**
     * 节点类型（字典项 lf_node_type）
     */
    @NotBlank(message = "[节点类型（字典项 lf_node_type）]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "节点类型（字典项 lf_node_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String type;

}
