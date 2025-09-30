package io.github.mangocrisp.spring.taybct.module.scheduling.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 调度任务
 * scheduled_task
 *
 * @author xijieyin
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "scheduled_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "调度任务")
public class ScheduledTask extends UniqueDeleteLogic<Long, Long> {

    @Serial
    private static final long serialVersionUID = 2561037394030967780L;

    /**
     * 任务键
     */
    @NotBlank(message = "[任务键]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "任务键")
    @Length(max = 64, message = "编码长度不能超过64")
    private String taskKey;
    /**
     * 任务描述
     */
    @NotBlank(message = "[任务描述]不能为空")
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "任务描述")
    @Length(max = 255, message = "编码长度不能超过255")
    private String description;
    /**
     * cron 表达式
     */
    @NotBlank(message = "[cron 表达式]不能为空")
    @Size(max = 32, message = "编码长度不能超过32")
    @Schema(description = "cron 表达式")
    @Length(max = 32, message = "编码长度不能超过32")
    private String cron;
    /**
     * 是否自动启动(1 是 0 否)
     */
    @NotNull(message = "[是否自动启动(1 是 0 否)]不能为空")
    @Schema(description = "是否自动启动(1 是 0 否)")
    @TableFieldDefault("1")
    private Byte autoStart;
    /**
     * 排序
     */
    @NotNull(message = "[排序]不能为空")
    @Schema(description = "排序")
    @TableFieldDefault("0")
    private Integer sort;
    /**
     * 任务启动参数
     */
    @Schema(description = "任务启动参数")
    private String params;
    /**
     * 租户 id
     */
    @Schema(description = "租户 id 区分不同租户的日志")
    private String tenantId;

}