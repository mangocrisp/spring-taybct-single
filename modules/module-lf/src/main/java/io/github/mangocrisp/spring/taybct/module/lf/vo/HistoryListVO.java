package io.github.mangocrisp.spring.taybct.module.lf.vo;

import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;

/**
 * 流程历史 VO 类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程历史 VO 类")
public class HistoryListVO extends History {

    @Serial
    private static final long serialVersionUID = 8200129443366216365L;

    /**
     * 操作用户昵称
     */
    @Schema(description = "操作用户昵称")
    private String userName;
    /**
     * 操作者部门名称
     */
    @Schema(description = "操作者部门名称")
    private String deptName;
    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

}
