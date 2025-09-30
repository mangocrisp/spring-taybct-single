package io.github.mangocrisp.spring.taybct.module.lf.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;

/**
 * 新建流程 DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程管理")
public class ProcessNewDTO extends Process {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = -6839720519371984133L;

    /**
     * 开始节点的属性数据，新建流程，是从第一个开始节点开始的，这里一般会传开始节点的属性数据
     */
    @NotNull(message = "开始节点和属性数据")
    @Schema(description = "开始节点和属性数据")
    private Nodes startNodes;

}
