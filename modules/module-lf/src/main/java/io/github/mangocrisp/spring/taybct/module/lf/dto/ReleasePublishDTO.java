package io.github.mangocrisp.spring.taybct.module.lf.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.github.mangocrisp.spring.taybct.tool.core.handle.TableFieldDefaultLoginUserIdHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.mapping.SqlCommandType;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程发布 DTO
 * <p>
 * lf_release
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程发布 DTO")
public class ReleasePublishDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7015729494694399864L;
    /**
     * 主键
     */
    @TableFieldDefault(expression = "T(com.baomidou.mybatisplus.core.toolkit.IdWorker).getId()")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创建人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableFieldDefault(handler = TableFieldDefaultLoginUserIdHandler.class)
    private Long createUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @TableFieldDefault(isTimeNow = true)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableFieldDefault(handler = TableFieldDefaultLoginUserIdHandler.class, fill = {SqlCommandType.INSERT, SqlCommandType.UPDATE})
    private Long updateUser;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @TableFieldDefault(isTimeNow = true, fill = {SqlCommandType.INSERT, SqlCommandType.UPDATE})
    private LocalDateTime updateTime;

    /**
     * 状态[0:未删除,1:删除]
     */
    @TableLogic
    @TableFieldDefault("0")
    private Byte isDeleted;

    /**
     * 流程图 id
     */
    @NotNull(message = "[流程图 id]不能为空")
    @Schema(description = "流程图 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long designId;
    /**
     * 发布名称
     */
    @NotBlank(message = "[发布名称]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "发布名称")
    @Length(max = 64, message = "编码长度不能超过64")
    private String name;
    /**
     * 状态(0 关闭 1 打开)
     */
    @Schema(description = "状态(0 关闭 1 打开)")
    @TableFieldDefault("1")
    private Byte status;
    /**
     * 备注说明
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "备注说明")
    @Length(max = 255, message = "编码长度不能超过255")
    private String description;
    /**
     * 版本号
     */
    @Schema(description = "版本号")
    @TableFieldDefault(expression = "T(java.lang.Long).valueOf(T(java.time.LocalDateTime).now().format(T(java.time.format.DateTimeFormatter).ofPattern(\"yyyyMMddHHmmss\", T(java.util.Locale).CHINA)))")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long version;
    /**
     * 流程类型（字典项 lf_process_type）
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "流程类型（字典项 lf_process_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String type;

}
