package io.github.mangocrisp.spring.taybct.module.od.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.constant.Constants;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.MybatisOptional;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 针对表【t_online_doc(在线文档)】的数据库操作Mapper
 * </pre>
 *
 * @author xijieyin
 * @see OnlineDoc
 * @since 2025/9/20 03:06
 */
public interface OnlineDocMapper extends BaseMapper<OnlineDoc> {

    /**
     * 更新 onlyoffice 文件地址
     *
     * @param onlineDoc onlineDoc
     * @return 影响行数
     */
    int updateOnlyOfficeFileUrl(OnlineDoc onlineDoc);

    /**
     * 查询 Map 列表
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 返回列表数据
     */
    @MapKey("t_online_doc_id")
    List<Map<String, Object>> listMap(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends OnlineDoc> mybatisOptional);

    /**
     * 根据条件批量更新
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 影响的行数
     */
    int updateBatchByCondition(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends OnlineDoc> mybatisOptional);

    /**
     * 查询记录条数
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 数量
     */
    long total(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends OnlineDoc> mybatisOptional);

    /**
     * 分页查询
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 分页数据
     */
    List<? extends OnlineDoc> page(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends OnlineDoc> mybatisOptional);

    /**
     * 查询单个
     *
     * @param mybatisOptional MyBatis 可选项
     * @return 元素标签详情
     */
    <E extends OnlineDoc> E detail(@Param(Constants.MYBATIS_OPTIONAL) MybatisOptional<? extends OnlineDoc> mybatisOptional);
}
