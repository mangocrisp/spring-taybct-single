package io.github.mangocrisp.spring.taybct.module.scheduling.vo;

import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledTask;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 调度任务
 * scheduled_task
 *
 * @author xijieyin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "调度任务扩展")
public class ScheduledTaskVO extends ScheduledTask {

    @Serial
    private static final long serialVersionUID = 8331617525918609877L;
    /**
     * 当前是否已启动 1 已启动 0 未启动
     */
    @Schema(description = "当前是否已启动 1 已启动 0 未启动")
    private Integer startFlag;

}