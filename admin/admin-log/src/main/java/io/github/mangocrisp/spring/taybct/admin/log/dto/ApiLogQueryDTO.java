package io.github.mangocrisp.spring.taybct.admin.log.dto;

import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 接口日志查询参数<br>
 * api_log
 *
 * @author xijieyin <br> 2022/8/5 9:47
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "接口日志查询参数")
public class ApiLogQueryDTO extends ApiLog {
    @Serial
    private static final long serialVersionUID = 4127140708906628752L;
    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

}
