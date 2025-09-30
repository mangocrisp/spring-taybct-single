package io.github.mangocrisp.spring.taybct.module.scheduling.dto;

import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 调度日志
 * scheduled_log
 *
 * @author xijieyin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledLogQueryDTO extends ScheduledLog {

    @Schema(description = "任务 key")
    private String key;
    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime timeBegin;
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime timeEnd;

    @Serial
    private static final long serialVersionUID = 3148758681520911718L;
}