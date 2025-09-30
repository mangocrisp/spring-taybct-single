package ${baseInfo.packageName};

import ${tableClass.fullClassName};
import ${queryDTO.packageName}.${queryDTO.fileName};
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
*
<pre>
 * ${tableClass.remark!} 列表多条件查询对象
 * TableName: ${tableClass.tableName} 列表多条件查询对象
 * </pre>
*
* @author ${author!}
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
* @see ${tableClass.fullClassName}
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【${tableClass.remark!}】列表多条件查询对象")
public class ${tableClass.shortClassName}QueryBody implements Serializable, ModelConvertible<${tableClass.shortClassName}> {

@Serial
private static final long serialVersionUID = 1L;


/**
* 【${tableClass.remark!}】查询 dto
*/
@Schema(description = "【${tableClass.remark!}】查询 dto")
private ${tableClass.shortClassName}QueryDTO ${tableClass.shortClassName?uncap_first}QueryDTO = new ${tableClass.shortClassName}QueryDTO();

// TODO 还可以继续添加 dto 进来

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
this.convertedBean = (bean = BeanUtil.copyProperties(${tableClass.shortClassName?uncap_first}QueryDTO, beanClass(), ignoreProperties));
return bean;
}

}