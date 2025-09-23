package io.github.mangocrisp.spring.taybct.module.system.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.system.domain.VueTemplate;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 针对表【t_vue_template(前端通用模板)】的数据库操作Service
 * </pre>
 *
 * @author SuMuYue
 * @see VueTemplate
 * @since 2025-08-15 11:12:11
 */
public interface IVueTemplateService extends IService<VueTemplate> {

    /**
     * 查询 Map 列表
     *
     * @param fields        指定需要查询的字段
     * @param params        查询条件
     * @param sqlPageParams 分页参数防止查询全表
     * @return 返回列表数据
     */
    List<Map<String, Object>> listMap(List<String> fields, JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 根据条件更新数据
     *
     * @param model 条件
     * @return 是否更新成功
     */
    <UM extends ModelConvertible<? extends VueTemplate>, QM extends ModelConvertible<? extends VueTemplate>> boolean update(UpdateModel<VueTemplate, UM, QM> model);

    /**
     * 查询总数
     *
     * @param params 查询条件
     * @param <E>    结果类型
     * @return 分页结果
     */
    <E extends VueTemplate> long total(JSONObject params);

    /**
     * 查询分页
     *
     * @param params        查询条件
     * @param sqlPageParams 分页参数
     * @param <E>           结果类型
     * @return 分页结果
     */
    <E extends VueTemplate> IPage<E> page(JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 查询列表
     *
     * @param params        查询条件
     * @param sqlPageParams 分页参数防止查询全表
     * @param <E>           返回对象类型
     * @return 返回列表数据
     */
    <E extends VueTemplate> List<E> list(JSONObject params, SqlPageParams sqlPageParams);

    /**
     * 查询详情
     *
     * @param params 查询条件
     * @param <E>    结果类型
     * @return 详情
     */
    <E extends VueTemplate> E detail(JSONObject params);

}
