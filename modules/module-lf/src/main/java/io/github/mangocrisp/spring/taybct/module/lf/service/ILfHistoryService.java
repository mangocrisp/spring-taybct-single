package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryOperator;
import io.github.mangocrisp.spring.taybct.module.lf.vo.HistoryListVO;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_history(流程历史)】的数据库操作Service
 * @since 2023-07-03 11:32:23
 */
public interface ILfHistoryService extends IService<History> {

    /**
     * 保存历史记录
     *
     * @param operator 操作人
     * @param nodes    需要记录的历史节点信息
     * @param action   动作，做了什么操作
     */
    History save(HistoryOperator operator, Nodes nodes, String action);

    /**
     * 查询历史记录列表
     *
     * @param dto 查询参数
     * @return list
     */
    List<HistoryListVO> historyList(HistoryListQueryDTO dto);

}
