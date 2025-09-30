package ${mapperInterface.packageName};

import ${tableClass.fullClassName};
<#if tableClass.pkFields??>
    <#list tableClass.pkFields as field><#assign pkName>${field.shortTypeName}</#assign></#list>
</#if>
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.constant.Constants;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.MybatisOptional;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
*
<pre>
 * 针对表【${tableClass.tableName}<#if tableClass.remark?has_content>(${tableClass.remark!})</#if>】的数据库操作Mapper
 * </pre>
*
* @author ${author!}
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
* @see ${tableClass.fullClassName}
*/
public interface ${mapperInterface.fileName} extends BaseMapper<${tableClass.shortClassName}> {

/**
* 查询 Map 列表
*
* @param mybatisOptional MyBatis 可选项
* @return 返回列表数据
*/
<#list tableClass.pkFields as field>@MapKey("${tableClass.tableName}_${field.columnName}")</#list>
List
<Map
<String, Object>> listMap(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends ${tableClass.shortClassName}> mybatisOptional);

/**
* 根据条件批量更新
*
* @param mybatisOptional MyBatis 可选项
* @return 影响的行数
*/
int updateBatchByCondition(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends ${tableClass.shortClassName}> mybatisOptional);

/**
* 查询记录条数
*
* @param mybatisOptional MyBatis 可选项
* @return 数量
*/
long total(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends ${tableClass.shortClassName}> mybatisOptional);

/**
* 分页查询
*
* @param mybatisOptional MyBatis 可选项
* @return 分页数据
*/
List<? extends ${tableClass.shortClassName}> page(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends ${tableClass.shortClassName}> mybatisOptional);

/**
* 查询单个
*
* @param mybatisOptional MyBatis 可选项
* @return 元素标签详情
*/
<E extends ${tableClass.shortClassName}> E detail(@Param(Constants.MYBATIS_OPTIONAL)
    MybatisOptional<? extends ${tableClass.shortClassName}> mybatisOptional);

    }
