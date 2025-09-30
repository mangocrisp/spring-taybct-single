package ${domain.packageName};

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.entity.PoiBaseConstants;
import jakarta.validation.constraints.NotBlank;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
<#list tableClass.importList as fieldType>${"\n"}import ${fieldType};</#list>
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import java.io.Serial;

/**
*
* ${tableClass.remark!}
* <br>TableName: ${tableClass.tableName}
*
* @author ${author!}
*/
@TableName(value = "${tableClass.tableName}")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "${tableClass.remark!}")
@ExcelTarget(value = "${tableClass.remark!}")
public class ${tableClass.shortClassName} implements Serializable {

@TableField(exist = false)
@Serial
private static final long serialVersionUID = 1L;

@Excel(name = "序号", width = 10, needMerge = true, mergeVertical = true, format = PoiBaseConstants.IS_ADD_INDEX)
@TableField(exist = false)
@Schema(hidden = true)
private Integer easy_poi_excel_index = 1;

<#list tableClass.pkFields as field>
    /**
    * ${field.remark!}
    */<#if !field.nullable || field.jdbcType=="VARCHAR">${"\n    "}</#if><#if !field.nullable><#if field.jdbcType=="VARCHAR">@NotBlank(message="[${field.remark!}]不能为空")<#else>@NotNull(message="[${field.remark!}]不能为空")</#if></#if><#if field.jdbcType=="VARCHAR"><#if !field.nullable>${"\n    "}</#if>@Size(max= ${field.columnLength?c},message="${field.remark!}长度不能超过${field.columnLength?c}")</#if>
    @Schema(description = "${field.remark!}")<#if field.jdbcType=="VARCHAR">${"\n    "}@Length(max= ${field.columnLength?c},message="${field.remark!}长度不能超过${field.columnLength?c}")</#if>
    @Excel(name = "${field.remark!}", width = 25, needMerge = true, mergeVertical = true)
    @TableId(value = "${field.columnName}")<#if field.jdbcType=="BIGINT">${"\n    "}@JsonSerialize(using = ToStringSerializer.class)</#if>
    private ${field.shortTypeName} ${field.fieldName};
</#list>

<#list tableClass.baseBlobFields as field>
    /**
    * ${field.remark!}
    */<#if !field.nullable || field.jdbcType=="VARCHAR">${"\n    "}</#if><#if !field.nullable><#if field.jdbcType=="VARCHAR">@NotBlank(message="[${field.remark!}]不能为空")<#else>@NotNull(message="[${field.remark!}]不能为空")</#if></#if><#if field.jdbcType=="VARCHAR"><#if !field.nullable>${"\n    "}</#if>@Size(max= ${field.columnLength?c},message="${field.remark!}长度不能超过${field.columnLength?c}")</#if>
    @Schema(description = "${field.remark!}")<#if field.jdbcType=="VARCHAR">${"\n    "}@Length(max= ${field.columnLength?c},message="${field.remark!}长度不能超过${field.columnLength?c}")</#if>
    @Excel(name = "${field.remark!}"<#if field.jdbcType=="DATE">, format = DateConstants.format.YYYY_MM_DD, timezone = "GMT+8"</#if><#if field.jdbcType=="TIMESTAMP">, format = DateConstants.format.YYYY_MM_DD_HH_mm_ss, timezone = "GMT+8"</#if>, width = 25, needMerge = true, mergeVertical = true)
    @TableField(value = "${field.columnName}")<#if field.jdbcType=="DATE">${"\n    "}@DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)</#if><#if field.jdbcType=="TIMESTAMP">${"\n    "}@DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)</#if><#if field.jdbcType=="BIGINT">${"\n    "}@JsonSerialize(using = ToStringSerializer.class)</#if>
    private ${field.shortTypeName} ${field.fieldName};
</#list>

}
