package io.github.mangocrisp.spring.taybct.module.lf.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 待办数量查询 VO
 *
 * @author XiJieYin <br> 2023/7/19 14:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "待办数量查询 VO")
public class TodoListCountVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8559793940385106870L;

    /**
     * 待办数量
     */
    @Schema(description = "待办数量")
    private List<TodoCount> todoCount;
    /**
     * 已办数量
     */
    @Schema(description = "已办数量")
    private List<TodoCount> doneCount;
    /**
     * 流程类型待办数量
     */
    @Schema(description = "流程类型待办数量")
    private List<TodoCount> typeCount;
    /**
     * 设计待办数量
     */
    @Schema(description = "设计待办数量")
    private List<TodoCount> designCount;

}
