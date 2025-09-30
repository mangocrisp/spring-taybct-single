package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.EdgesMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IEdgesService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_edges(流程连线表)】的数据库操作Service实现
 * @since 2023-07-03 11:32:23
 */
public class EdgesServiceImpl extends ServiceImpl<EdgesMapper, Edges>
        implements IEdgesService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatchSelective(Collection<Edges> records) {
        records.forEach(baseMapper::insertSelective);
        return true;
    }

    @Override
    public List<Edges> selectBySourceId(String sourceId) {
        return list(Wrappers.<Edges>lambdaQuery()
                .eq(Edges::getSourceNodeId, sourceId));
    }

    @Override
    public Edges selectBySourceAndTargetId(String sourceId, String targetId) {
        return getOne(Wrappers.<Edges>lambdaQuery()
                .eq(Edges::getSourceNodeId, sourceId)
                .eq(Edges::getTargetNodeId, targetId));
    }
}




