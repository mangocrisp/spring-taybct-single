package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.system.domain.VueTemplate;
import io.github.mangocrisp.spring.taybct.module.system.mapper.VueTemplateMapper;
import io.github.mangocrisp.spring.taybct.module.system.service.IVueTemplateService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.MybatisOptional;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 针对表【t_vue_template(前端通用模板)】的数据库操作Service实现
 * </pre>
 *
 * @author SuMuYue
 * @see VueTemplate
 * @since 2025-08-15 11:12:11
 */
@AutoConfiguration
@Service
public class VueTemplateServiceImpl extends ServiceImpl<VueTemplateMapper, VueTemplate>
        implements IVueTemplateService {

    @Override
    public List<Map<String, Object>> listMap(List<String> fields, JSONObject params, SqlPageParams sqlPageParams) {
        return getBaseMapper().listMap(MyBatisUtil.<VueTemplate>mybatisOptional()
                .select(fields)
                .params(params)
                .page(sqlPageParams));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public <UM extends ModelConvertible<? extends VueTemplate>, QM extends ModelConvertible<? extends VueTemplate>> boolean update(UpdateModel<VueTemplate, UM, QM> model) {
        model.getUpdateList().forEach(condition -> getBaseMapper().updateBatchByCondition(MyBatisUtil
                .<VueTemplate>mybatisOptional()
                .bean(condition.getBean())
                .params(condition.getParams())));
        return true;
    }

    @Override
    public <E extends VueTemplate> long total(JSONObject params) {
        MybatisOptional<E> mybatisOptional = MyBatisUtil
                .<E>mybatisOptional()
                .params(params);
        return getBaseMapper().total(mybatisOptional);
    }

    @Override
    public <E extends VueTemplate> IPage<E> page(JSONObject params, SqlPageParams sqlPageParams) {
        IPage<E> page = sqlPageParams.genPage();
        MybatisOptional<E> mybatisOptional = MyBatisUtil
                .<E>mybatisOptional()
                .params(params)
                .page(sqlPageParams);
        if (sqlPageParams.getCountTotal()) {
            page.setTotal(getBaseMapper().total(mybatisOptional));
            if (page.getTotal() == 0) {
                return page;
            }
        }
        List<? extends VueTemplate> list = getBaseMapper().page(mybatisOptional);
        if (CollectionUtil.isNotEmpty(list)) {
            page.setRecords(list.stream().map(e -> (E) e).toList());
        }
        return page;
    }

    @Override
    public <E extends VueTemplate> List<E> list(JSONObject params, SqlPageParams sqlPageParams) {
        return getBaseMapper().page(MyBatisUtil
                .<E>mybatisOptional()
                .params(params)
                .page(sqlPageParams)).stream().map(e -> (E) e).toList();
    }

    @Override
    public <E extends VueTemplate> E detail(JSONObject params) {
        return getBaseMapper().detail(MyBatisUtil
                .<E>mybatisOptional()
                .params(params));
    }

}
