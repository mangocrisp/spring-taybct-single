package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;

import java.util.Collection;
import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_edges(流程连线表)】的数据库操作Service
 * @since 2023-07-03 11:32:23
 */
public interface IEdgesService extends IService<Edges> {
    /**
     * 批量保存连线
     *
     * @param records 集合
     * @return boolean
     */
    boolean insertBatchSelective(Collection<Edges> records);

    /**
     * 根据源 id 查询线
     *
     * @param sourceId 源 id
     * @return list
     */
    List<Edges> selectBySourceId(String sourceId);

    /**
     * 根据源和目标 id 查询连线
     *
     * @param sourceId 源 id
     * @param targetId 目标 id
     * @return list
     */
    Edges selectBySourceAndTargetId(String sourceId, String targetId);
}
