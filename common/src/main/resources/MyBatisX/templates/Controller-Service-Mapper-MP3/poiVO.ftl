package ${baseInfo.packageName};

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
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
<pre>
 * ${tableClass.remark!}下载对象
 * </pre>
*
* @author ${author!}
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
* @see ${tableClass.fullClassName}
*/
@Data
@EqualsAndHashCode
@HeadRowHeight(25)
@ContentRowHeight(20)
@ColumnWidth(20)
@HeadFontStyle(fontName = "微软雅黑", fontHeightInPoints = 14)
@ContentFontStyle(fontName = "微软雅黑", fontHeightInPoints = 12)
public class ${tableClass.shortClassName}ExpVO implements Serializable {

@Serial
@ExcelIgnore
private static final long serialVersionUID = 1L;

<#list tableClass.pkFields as field>
    /**
    * ${field.remark!}
    */
    @ExcelProperty(value = "${field.remark!}")<#if field.jdbcType=="BIGINT">${"\n    "}@JsonSerialize(using = ToStringSerializer.class)</#if>
    private ${field.shortTypeName} ${field.fieldName};
</#list>

<#list tableClass.baseBlobFields as field>
    /**
    * ${field.remark!}
    */
    @ExcelProperty(value = "${field.remark!}")<#if field.jdbcType=="DATE">${"\n    "}@DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)</#if><#if field.jdbcType=="TIMESTAMP">${"\n    "}@DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)</#if><#if field.jdbcType=="BIGINT">${"\n    "}@JsonSerialize(using = ToStringSerializer.class)</#if>
    private ${field.shortTypeName} ${field.fieldName};
</#list>

}
