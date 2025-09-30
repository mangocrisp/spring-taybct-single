package ${baseInfo.packageName};

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${tableClass.fullClassName};
import ${serviceInterface.packageName}.${serviceInterface.fileName};
import ${mapperInterface.packageName}.${mapperInterface.fileName};
<#if baseService??&&baseService!="">
    import ${baseService};
    <#list baseService?split(".") as simpleName>
        <#if !simpleName_has_next>
            <#assign serviceSimpleName>${simpleName}</#assign>
        </#if>
    </#list>
</#if>
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.MybatisOptional;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import java.util.Map;
import java.util.List;

/**
*
<pre>
 * 针对表【${tableClass.tableName}<#if tableClass.remark?has_content>(${tableClass.remark!})</#if>】的数据库操作Service实现
 * </pre>
*
* @author ${author!}
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
* @see ${tableClass.fullClassName}
*/
@AutoConfiguration
@Service
public class ${baseInfo.fileName} extends ServiceImpl<${mapperInterface.fileName}, ${tableClass.shortClassName}>
implements ${serviceInterface.fileName}{

@Override
public List
<Map
<String, Object>> listMap(List
<String> fields, JSONObject params, SqlPageParams sqlPageParams) {
    return getBaseMapper().listMap(MyBatisUtil.<${tableClass.shortClassName}>mybatisOptional()
    .select(fields)
    .params(params)
    .page(sqlPageParams));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public
    <UM extends ModelConvertible
    <? extends ${tableClass.shortClassName}>, QM extends ModelConvertible<? extends ${tableClass.shortClassName}>>
    boolean update(UpdateModel<${tableClass.shortClassName}, UM, QM> model) {
    model.getUpdateList().forEach(condition -> getBaseMapper().updateBatchByCondition(MyBatisUtil
    .<${tableClass.shortClassName}>mybatisOptional()
    .bean(condition.getBean())
    .params(condition.getParams())));
    return true;
    }

    @Override
    public
    <E extends ${tableClass.shortClassName}> long total(JSONObject params) {
        MybatisOptional
        <E> mybatisOptional = MyBatisUtil
            .
            <E>mybatisOptional()
                .params(params);
                return getBaseMapper().total(mybatisOptional);
                }

                @Override
                public
                <E extends ${tableClass.shortClassName}> IPage
                    <E> page(JSONObject params, SqlPageParams sqlPageParams) {
                        IPage
                        <E> page = sqlPageParams.genPage();
                            MybatisOptional
                            <E> mybatisOptional = MyBatisUtil
                                .
                                <E>mybatisOptional()
                                    .params(params)
                                    .page(sqlPageParams);
                                    if (sqlPageParams.getCountTotal()){
                                    page.setTotal(getBaseMapper().total(mybatisOptional));
                                    if (page.getTotal() == 0){
                                    return page;
                                    }
                                    }
                                    List<? extends ${tableClass.shortClassName}> list =
                                    getBaseMapper().page(mybatisOptional);
                                    if (CollectionUtil.isNotEmpty(list)){
                                    page.setRecords(list.stream().map(e -> (E) e).toList());
                                    }
                                    return page;
                                    }

                                    @Override
                                    public
                                    <E extends ${tableClass.shortClassName}> List
                                        <E> list(JSONObject params, SqlPageParams sqlPageParams){
                                            return getBaseMapper().page(MyBatisUtil
                                            .
                                            <E>mybatisOptional()
                                                .params(params)
                                                .page(sqlPageParams)).stream().map(e -> (E) e).toList();
                                                }

                                                @Override
                                                public
                                                <E extends ${tableClass.shortClassName}> E detail(JSONObject params) {
                                                    return getBaseMapper().detail(MyBatisUtil
                                                    .
                                                    <E>mybatisOptional()
                                                        .params(params));
                                                        }

                                                        }
