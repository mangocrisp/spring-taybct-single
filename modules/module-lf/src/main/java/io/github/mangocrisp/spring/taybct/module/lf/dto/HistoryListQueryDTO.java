package io.github.mangocrisp.spring.taybct.module.lf.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * 流程历史记录查询 DTO
 *
 * @author XiJieYin <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程历史记录查询 DTO")
public class HistoryListQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3821998231054391209L;
    /**
     * 运行流程 id
     */
    @Schema(description = "运行流程 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long processId;
    /**
     * 当前节点类型（字典项 lf_node_type）
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "当前节点类型（字典项 lf_node_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private Set<String> nodeType;
    /**
     * 与我相关
     */
    @Schema(description = "与我相关")
    private Integer relatedToMe;

}
