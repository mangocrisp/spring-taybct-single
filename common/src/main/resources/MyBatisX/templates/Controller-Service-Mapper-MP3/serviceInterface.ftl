package ${baseInfo.packageName};

import ${tableClass.fullClassName};
<#if baseService??&&baseService!="">
    import ${baseService};
    <#list baseService?split(".") as simpleName>
        <#if !simpleName_has_next>
            <#assign serviceSimpleName>${simpleName}</#assign>
        </#if>
    </#list>
</#if>
import com.baomidou.mybatisplus.extension.service.IService;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import java.util.Map;
import java.util.List;

/**
*
<pre>
 * 针对表【${tableClass.tableName}<#if tableClass.remark?has_content>(${tableClass.remark!})</#if>】的数据库操作Service
 * </pre>
*
* @author ${author!}
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
* @see ${tableClass.fullClassName}
*/
public interface ${baseInfo.fileName} extends IService<${tableClass.shortClassName}> {

/**
* 查询 Map 列表
*
* @param fields        指定需要查询的字段
* @param params        查询条件
* @param sqlPageParams 分页参数防止查询全表
* @return 返回列表数据
*/
List
<Map
<String, Object>> listMap(List
<String> fields, JSONObject params, SqlPageParams sqlPageParams);

    /**
    * 根据条件更新数据
    *
    * @param model 条件
    * @return 是否更新成功
    */
    <UM extends ModelConvertible
    <? extends ${tableClass.shortClassName}>, QM extends ModelConvertible<? extends ${tableClass.shortClassName}>>
    boolean update(UpdateModel<${tableClass.shortClassName}, UM, QM> model);

    /**
    * 查询总数
    *
    * @param params 查询条件
    * @param
    <E> 结果类型
        * @return 分页结果
        */
        <E extends ${tableClass.shortClassName}> long total(JSONObject params);

            /**
            * 查询分页
            *
            * @param params 查询条件
            * @param sqlPageParams 分页参数
            * @param
            <E> 结果类型
                * @return 分页结果
                */
                <E extends ${tableClass.shortClassName}> IPage
                    <E> page(JSONObject params, SqlPageParams sqlPageParams);

                        /**
                        * 查询列表
                        *
                        * @param params 查询条件
                        * @param sqlPageParams 分页参数防止查询全表
                        * @param
                        <E> 返回对象类型
                            * @return 返回列表数据
                            */
                            <E extends ${tableClass.shortClassName}> List
                                <E> list(JSONObject params, SqlPageParams sqlPageParams);

                                    /**
                                    * 查询详情
                                    *
                                    * @param params 查询条件
                                    * @param
                                    <E> 结果类型
                                        * @return 详情
                                        */
                                        <E extends ${tableClass.shortClassName}> E detail(JSONObject params);

                                            }
