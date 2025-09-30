package io.github.mangocrisp.spring.taybct.module.scheduling.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 调度日志
 * scheduled_log
 *
 * @author xijieyin
 */
@TableName(value = "scheduled_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 6611294627442281953L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private Long id;
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
     * 任务启动参数
     */
    @Schema(description = "任务启动参数")
    private String params;
    /**
     * 日志信息
     */
    @Schema(description = "日志信息")
    private String message;
    /**
     * 状态(1 正常 0 失败)
     */
    @NotNull(message = "[状态(1 正常 0 失败)]不能为空")
    @Schema(description = "状态(1 正常 0 失败)")
    private Integer status;
    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exceptionInfo;
    /**
     * 任务开始执行时间
     */
    @NotNull(message = "[任务开始执行时间]不能为空")
    @Schema(description = "任务开始执行时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime startTime;
    /**
     * 任务结束执行时间
     */
    @NotNull(message = "[任务结束执行时间]不能为空")
    @Schema(description = "任务结束执行时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime stopTime;
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "更新时间")
    @TableFieldDefault(isTimeNow = true)
    private LocalDateTime updateTime;
    /**
     * 租户 id
     */
    @Schema(description = "租户 id 区分不同租户的日志")
    @TableFieldDefault("000000")
    private String tenantId;

}