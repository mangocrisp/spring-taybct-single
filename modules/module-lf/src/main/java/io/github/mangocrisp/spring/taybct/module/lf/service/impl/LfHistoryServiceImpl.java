package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryOperator;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.HistoryMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.ILfHistoryService;
import io.github.mangocrisp.spring.taybct.module.lf.vo.HistoryListVO;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_history(流程历史)】的数据库操作Service实现
 * @since 2023-07-03 11:32:23
 */
public class LfHistoryServiceImpl extends ServiceImpl<HistoryMapper, History>
        implements ILfHistoryService {

    @Override
    public History save(HistoryOperator operator
            , Nodes nodes
            , String action) {
        History h = new History();
        h.setData(nodes.getProperties());
        h.setAction(action);
        h.setNodeId(nodes.getId());
        if (operator != null) {
            h.setUserId(operator.getUserId());
            h.setDeptId(operator.getDeptId());
            h.setPostId(operator.getPostId());
        }
        h.setProcessId(nodes.getProcessId());
        h.setNodeType(nodes.getType());
        super.save(h);
        return h;
    }

    @Override
    public List<HistoryListVO> historyList(HistoryListQueryDTO dto) {
        return getBaseMapper().historyList(dto);
    }

}




