package io.github.mangocrisp.spring.taybct.module.lf.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 未操作者
 *
 * @author XiJieYin <br> 2023/7/17 11:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "未操作者")
public class UnOperator implements Serializable {

    @Serial
    private static final long serialVersionUID = -2418630399996497780L;

    /**
     * 运行流程 id
     */
    @Schema(description = "运行流程 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long processId;
    /**
     * 操作者 id
     */
    @Schema(description = "操作者 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 操作者名
     */
    @Schema(description = "操作者名")
    private String name;
    /**
     * 操作者类型(user,role,dept)
     */
    @Schema(description = "操作者类型(user,role,dept)")
    private String type;

}
