package io.github.mangocrisp.spring.taybct.module.lf.api;

import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryOperator;

/**
 * 历史记录
 *
 * @author XiJieYin <br> 2023/7/14 11:17
 */
public interface HistoryReporter {
    /**
     * 记录流程历史记录
     *
     * @param operator 操作人
     * @param nodes    节点信息
     * @param action   动作
     */
    void accept(HistoryOperator operator, Nodes nodes, String action);
}
