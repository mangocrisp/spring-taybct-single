package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;

/**
 * @author admin
 * <br>description 针对表【lf_nodes(流程节点)】的数据库操作Mapper
 * @see Nodes
 * @since 2023-07-03 11:32:23
 */
public interface NodesMapper extends BaseMapper<Nodes> {
    /**
     * 选择性插入
     *
     * @param record 节点集合
     * @return 影响行数
     */
    int insertSelective(Nodes record);
}




