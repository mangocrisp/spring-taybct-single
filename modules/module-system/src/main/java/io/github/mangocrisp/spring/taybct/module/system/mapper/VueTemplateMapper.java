package io.github.mangocrisp.spring.taybct.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.system.domain.VueTemplate;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.constant.Constants;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.MybatisOptional;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 针对表【t_vue_template(前端通用模板)】的数据库操作Mapper
 * </pre>
 *
 * @author SuMuYue
 * @see VueTemplate
 * @since 2025-08-15 11:12:11
 */
public interface VueTemplateMapper extends BaseMapper<VueTemplate> {

    /**
     * 查询 Map 列表
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 返回列表数据
     */
    @MapKey("t_vue_template_id")
    List<Map<String, Object>> listMap(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends VueTemplate> mybatisOptional);

    /**
     * 根据条件批量更新
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 影响的行数
     */
    int updateBatchByCondition(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends VueTemplate> mybatisOptional);

    /**
     * 查询记录条数
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 数量
     */
    long total(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends VueTemplate> mybatisOptional);

    /**
     * 分页查询
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 分页数据
     */
    List<? extends VueTemplate> page(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends VueTemplate> mybatisOptional);

    /**
     * 查询单个
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 元素标签详情
     */
    <E extends VueTemplate> E detail(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends VueTemplate> mybatisOptional);

}
