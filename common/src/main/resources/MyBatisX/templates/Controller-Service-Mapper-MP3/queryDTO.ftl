package ${baseInfo.packageName};

import ${tableClass.fullClassName};
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
*
<pre>
 * ${tableClass.remark!} 列表查询对象
 * TableName: ${tableClass.tableName} 列表查询对象
 * </pre>
*
* @author ${author!}
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
* @see ${tableClass.fullClassName}
*/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【${tableClass.remark!}】列表查询对象")
public class ${tableClass.shortClassName}QueryDTO implements Serializable, ModelConvertible<${tableClass.shortClassName}> {

@Serial
private static final long serialVersionUID = 1L;

<#list tableClass.pkFields as field>
    /**
    * ${field.remark!}
    */
    @Schema(description = "${field.remark!}")<#if field.jdbcType=="BIGINT">${"\n    "}@JsonSerialize(using = ToStringSerializer.class)</#if>
    private ${field.shortTypeName} ${field.fieldName};

    /**
    * ${field.remark!}选择
    */
    @Schema(description = "${field.remark!}选择")
    private Collection<${field.shortTypeName}> ${field.fieldName}Selection;
</#list>

<#list tableClass.baseBlobFields as field>
    <#if field.jdbcType=="TIMESTAMP">
        /**
        * ${field.remark!}（时间范围开始）
        */
        @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
        @Schema(description = "${field.remark!}（时间范围开始）")
        private ${field.shortTypeName} ${field.fieldName}_ge;
        /**
        * ${field.remark!}（时间范围结束）
        */
        @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
        @Schema(description = "${field.remark!}（时间范围结束）")
        private ${field.shortTypeName} ${field.fieldName}_le;
    <#elseif field.jdbcType=="DATE">
        /**
        * ${field.remark!}（日期范围开始）
        */
        @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)
        @Schema(description = "${field.remark!}（日期范围开始）")
        private ${field.shortTypeName} ${field.fieldName}_ge;
        /**
        * ${field.remark!}（日期范围结束）
        */
        @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)${"\n    "}@JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)
        @Schema(description = "${field.remark!}（日期范围结束）")
        private ${field.shortTypeName} ${field.fieldName}_le;
    <#else>
        /**
        * ${field.remark!}
        */
        @Schema(description = "${field.remark!}")<#if field.jdbcType=="BIGINT">${"\n    "}@JsonSerialize(using = ToStringSerializer.class)</#if>
        private ${field.shortTypeName} ${field.fieldName}<#if field.columnName=="is_deleted"> = 0</#if>;
    </#if>
</#list>

@Hidden
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