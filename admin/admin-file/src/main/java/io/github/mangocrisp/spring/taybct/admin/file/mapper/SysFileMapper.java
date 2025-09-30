package io.github.mangocrisp.spring.taybct.admin.file.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * 针对表【sys_file(文件管理)】的数据库操作Mapper
 * </pre>
 *
 * @author 24154
 * @see SysFile
 * @since 2024-09-01 21:20:40
 */
public interface SysFileMapper extends BaseMapper<SysFile> {


    /**
     * 根据条件批量更新
     *
     * @param bean   需要更新的对象
     * @param params 查询条件
     * @return 影响的行数
     */
    int updateBatchByCondition(@Param("bean") SysFile bean, @Param("params") JSONObject params);

    /**
     * 查询记录条数
     *
     * @param params 查询条件
     * @return 数量
     */
    long total(@Param("params") JSONObject params);

    /**
     * 分页查询
     *
     * @param params 查询条件
     * @param page   分页条件
     * @return 分页数据
     */
    List<? extends SysFile> page(@Param("params") JSONObject params, @Param("page") SqlPageParams page);

    /**
     * 查询单个
     *
     * @param params 查询条件
     * @return 元素标签详情
     */
    <E extends SysFile> E detail(@Param("params") JSONObject params);

}
