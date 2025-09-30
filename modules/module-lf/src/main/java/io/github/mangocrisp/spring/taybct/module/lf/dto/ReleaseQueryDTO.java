package io.github.mangocrisp.spring.taybct.module.lf.dto;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程发布查询 DTO
 *
 * @author XiJieYin <br> 2023/7/6 9:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程发布查询 DTO")
public class ReleaseQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1924181771524388696L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创建人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    /**
     * 创建时间开始
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime createTimeBegin;

    /**
     * 创建时间结束
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime createTimeEnd;

    /**
     * 流程图 id
     */
    @Schema(description = "流程图 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long designId;
    /**
     * 发布名称
     */
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "发布名称")
    @Length(max = 64, message = "编码长度不能超过64")
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 备注说明
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "备注说明")
    @Length(max = 255, message = "编码长度不能超过255")
    @TableField(condition = SqlCondition.LIKE)
    private String description;
    /**
     * 状态(0 关闭 1 打开)
     */
    @Schema(description = "状态(0 关闭 1 打开)")
    private Byte status;
    /**
     * 版本号
     */
    @Schema(description = "版本号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long version;
    /**
     * 流程类型（字典项 lf_process_type）
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "流程类型（字典项 lf_process_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String type;

    /**
     * 显示最新版本
     */
    @Schema(description = "显示最新版本是否只显示最新版本")
    public boolean showNewVersion;

}
