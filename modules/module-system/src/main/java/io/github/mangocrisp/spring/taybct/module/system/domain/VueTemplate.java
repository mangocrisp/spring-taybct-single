package io.github.mangocrisp.spring.taybct.module.system.domain;

import cn.afterturn.easypoi.entity.PoiBaseConstants;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldJSON;
import io.github.mangocrisp.spring.taybct.tool.core.bean.DeleteLogicEntity;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.github.mangocrisp.spring.taybct.tool.core.support.ToJSONObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * 前端通用模板
 * TableName: t_vue_template
 * </pre>
 *
 * @author SuMuYue
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_vue_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "前端通用模板")
@ExcelTarget(value = "前端通用模板")
public class VueTemplate extends DeleteLogicEntity<Long, Long> {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", width = 10, needMerge = true, mergeVertical = true, format = PoiBaseConstants.IS_ADD_INDEX)
    @TableField(exist = false)
    @Schema(hidden = true)
    private Integer easy_poi_excel_index = 1;
    /**
     * 字符串类型
     */
    @NotBlank(message = "[字符串类型]不能为空")
    @Size(max = 200, message = "[字符串类型]长度不能超过200")
    @Length(max = 200, message = "[字符串类型]长度不能超过200")
    @Schema(description = "字符串类型")
    @Excel(name = "字符串类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "string")
    private String string;
    /**
     * 整数类型
     */
    @Schema(description = "整数类型")
    @Excel(name = "整数类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "number_int")
    private Integer numberInt;
    /**
     * 长整数类型
     */
    @Schema(description = "长整数类型")
    @Excel(name = "长整数类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "number_long")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long numberLong;
    /**
     * 日期类型
     */
    @Schema(description = "日期类型")
    @Excel(name = "日期类型", format = DateConstants.format.YYYY_MM_DD, timezone = "GMT+8", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "date")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)
    private LocalDate date;
    /**
     * 日期时间类型
     */
    @Schema(description = "日期时间类型")
    @Excel(name = "日期时间类型", format = DateConstants.format.YYYY_MM_DD_HH_mm_ss, timezone = "GMT+8", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "date_time")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime dateTime;
    /**
     * 字节类型
     */
    @Schema(description = "字节类型")
    @Excel(name = "字节类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "number_byte")
    private Integer numberByte;
    /**
     * 布尔类型
     */
    @Schema(description = "布尔类型")
    @Excel(name = "布尔类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "bool_type")
    private Boolean boolType;
    /**
     * 长文本类型
     */
    @Schema(description = "长文本类型")
    @Excel(name = "长文本类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "text_type")
    private String textType;
    /**
     * JSON 类型
     */
    @Schema(description = "JSON 类型")
    @Excel(name = "JSON 类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "json_type")
    @JsonSerialize(using = ToJSONObjectSerializer.class)
    @TableFieldJSON
    private Object jsonType;
    /**
     * 单精度浮点类型
     */
    @Schema(description = "单精度浮点类型")
    @Excel(name = "单精度浮点类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "float_type")
    private Float floatType;
    /**
     * 双精度浮点类型
     */
    @Schema(description = "双精度浮点类型")
    @Excel(name = "双精度浮点类型", width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "double_type")
    @JsonSerialize(using = ToStringSerializer.class)
    private Double doubleType;

}
