package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;

/**
 * @author admin
 * <br>description 针对表【lf_edges(流程连线表)】的数据库操作Mapper
 * @see Edges
 * @since 2023-07-03 11:32:23
 */
public interface EdgesMapper extends BaseMapper<Edges> {
    /**
     * 选择性保存
     *
     * @param record 记录
     * @return 影响的行数
     */
    int insertSelective(Edges record);

}




