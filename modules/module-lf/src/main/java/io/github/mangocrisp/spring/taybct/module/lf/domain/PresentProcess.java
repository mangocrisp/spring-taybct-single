package io.github.mangocrisp.spring.taybct.module.lf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
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
 * 当前正在进行的流程
 *
 * <br>TableName lf_present_process
 */
@TableName(value = "lf_present_process")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "当前正在进行的流程")
public class PresentProcess implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -5312365081033507567L;

    /**
     * 流程 id
     */
    @NotNull(message = "[流程 id]不能为空")
    @Schema(description = "流程 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long processId;
    /**
     * 当前节点 id
     */
    @NotBlank(message = "[当前节点 id]不能为空")
    @Size(max = 36, message = "编码长度不能超过36")
    @Schema(description = "当前节点 id")
    @Length(max = 36, message = "编码长度不能超过36")
    private String nodeId;
    /**
     * 当前节点类型（字典项 lf_node_type）
     */
    @NotBlank(message = "[当前节点类型（字典项 lf_node_type）]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "当前节点类型（字典项 lf_node_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String nodeType;
    /**
     * 更新时间
     */
    @NotNull(message = "[更新时间]不能为空")
    @Schema(description = "更新时间")
    @TableFieldDefault(isTimeNow = true)
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime updateTime;

}
