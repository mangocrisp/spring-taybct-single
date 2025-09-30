package ${baseInfo.packageName};

import ${tableClass.fullClassName};
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import jakarta.validation.constraints.NotBlank;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
<#list tableClass.importList as fieldType>${"\n"}import ${fieldType};</#list>
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import java.io.Serial;

/**
*
<pre>
 * ${tableClass.remark!}上传对象
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
public class ${tableClass.shortClassName}ImpDTO implements Serializable, ModelConvertible<${tableClass.shortClassName}> {

@Serial
@ExcelIgnore
private static final long serialVersionUID = 1L;

<#list tableClass.baseBlobFields as field>
    /**
    * ${field.remark!}
    */<#if !field.nullable><#if field.jdbcType=="VARCHAR">${"\n    "}@NotBlank(message="[${field.remark!}]不能为空")<#else>${"\n    "}@NotNull(message="[${field.remark!}]不能为空")</#if></#if><#if field.jdbcType=="VARCHAR"&&field.columnLength gt 0>${"\n    "}@Size(max= ${field.columnLength?c},message="[${field.remark!}]长度不能超过${field.columnLength?c}")${"\n    "}@Length(max= ${field.columnLength?c},message="[${field.remark!}]长度不能超过${field.columnLength?c}")</#if>
    @ExcelProperty(value = "${field.remark!}")<#if field.jdbcType=="DATE">${"\n    "}@DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)</#if><#if field.jdbcType=="TIMESTAMP">${"\n    "}@DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)</#if><#if field.jdbcType=="BIGINT">${"\n    "}@JsonSerialize(using = ToStringSerializer.class)</#if>
    private ${field.shortTypeName} ${field.fieldName};
</#list>

@Hidden
@ExcelIgnore
${tableClass.shortClassName} convertedBean;

@Override
@Hidden
public void setConvertedBean(${tableClass.shortClassName} convertedBean) {
throw new BaseException("not support!");
}

@Override
public ${tableClass.shortClassName} bean(String... ignoreProperties) {
${tableClass.shortClassName} bean;
if ((bean = getConvertedBean()) != null) {
return bean;
}
this.convertedBean = (bean = BeanUtil.copyProperties(this, beanClass(), ignoreProperties));
return bean;
}
}
