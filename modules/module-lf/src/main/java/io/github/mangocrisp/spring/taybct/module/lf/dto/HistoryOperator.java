package io.github.mangocrisp.spring.taybct.module.lf.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 历史记录操作者
 *
 * @author XiJieYin <br> 2023/7/19 12:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "历史记录操作者")
@Builder
public class HistoryOperator implements Serializable {
    @Serial
    private static final long serialVersionUID = -647879019573889339L;

    /**
     * 流程发起人 id
     */
    @Schema(description = "流程发起人 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 发起部门
     */
    @Schema(description = "发起部门")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    /**
     * 岗位
     */
    @Schema(description = "岗位")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;
}
