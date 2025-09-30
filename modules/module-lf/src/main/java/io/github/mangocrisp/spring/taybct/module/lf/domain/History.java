package io.github.mangocrisp.spring.taybct.module.lf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldJSON;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程历史
 *
 * <br>TableName lf_history
 */
@TableName(value = "lf_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程历史")
public class History implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -3280703527723124564L;

    /**
     * 主键
     */
    @NotNull(message = "[主键]不能为空")
    @Schema(description = "主键")
    @TableFieldDefault(expression = "T(com.baomidou.mybatisplus.core.toolkit.IdWorker).getId()")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 操作时间
     */
    @NotNull(message = "[操作时间]不能为空")
    @Schema(description = "操作时间")
    @TableFieldDefault(isTimeNow = true)
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime time;
    /**
     * 操作人 id
     */
    @Schema(description = "操作人 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 操作人的部门
     */
    @Schema(description = "操作人的部门")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    /**
     * 流程 id
     */
    @NotNull(message = "[流程 id]不能为空")
    @Schema(description = "流程 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long processId;
    /**
     * 动作（节点的 text 或者单独有个 action 的属性）
     */
    @NotBlank(message = "[动作（节点的 text 或者单独有个 action 的属性）]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "动作（节点的 text 或者单独有个 action 的属性）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String action;
    /**
     * 操作顺序
     */
    @NotNull(message = "[操作顺序]不能为空")
    @Schema(description = "操作顺序")
    @TableFieldDefault(expression = "T(java.lang.System).currentTimeMillis()")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sort;
    /**
     * 当前节点操作的数据
     */
    @Schema(description = "当前节点操作的数据")
    @TableFieldJSON
    private Object data;
    /**
     * 当前操作的节点 id
     */
    @NotBlank(message = "[当前操作的节点 id]不能为空")
    @Size(max = 36, message = "编码长度不能超过36")
    @Schema(description = "当前操作的节点 id")
    @Length(max = 36, message = "编码长度不能超过36")
    private String nodeId;
    /**
     * 操作人的岗位
     */
    @Schema(description = "操作人的岗位")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;
    /**
     * 当前节点类型（字典项 lf_node_type）
     */
    @NotBlank(message = "[当前节点类型（字典项 lf_node_type）]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "当前节点类型（字典项 lf_node_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String nodeType;

}
