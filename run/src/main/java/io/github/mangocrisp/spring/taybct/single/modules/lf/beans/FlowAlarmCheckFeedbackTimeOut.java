package io.github.mangocrisp.spring.taybct.single.modules.lf.beans;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.module.lf.api.ProcessAutoDealHandler;
import io.github.mangocrisp.spring.taybct.module.lf.auto.LfServiceAutoConfigure;
import io.github.mangocrisp.spring.taybct.module.lf.constants.ProcessConstant;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.enums.ProcessItemType;
import io.github.mangocrisp.spring.taybct.module.lf.service.ILfHistoryService;
import io.github.mangocrisp.spring.taybct.module.lf.util.ProcessUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * <pre>
 * 判断是否超时
 * 因为这个需要根据之前的表单的数据来进行判断，所以需要写代码来处理了
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/3 16:10
 */
@AutoConfiguration(value = "flowAlarmCheckFeedbackTimeOut", after = LfServiceAutoConfigure.class)
@Slf4j
@RequiredArgsConstructor
public class FlowAlarmCheckFeedbackTimeOut implements ProcessAutoDealHandler {

    final ILfHistoryService lfHistoryService;

    /**
     * 领导审批下达任务节点 id
     */
    final static String leaderNodeId = "8c1358a2501b41d38f28059ed121493a";
    /**
     * 领导审批反馈任务节点 id
     */
    final static String leaderAuditFeedbackNodeId = "78a9cf4fbd074cc7871a3fb7d32735f0";

    /**
     * 时间单位
     */
    interface TimeUnit {
        String DAYS = "Days";
        String HOURS = "Hours";
    }

    @Override
    public boolean apply(History history, Process process, Edges edges, Nodes nodes) {
        JSONObject formData = ProcessUtil.getJSONObject(process.getFormData());
        if (formData == null) {
            formData = new JSONObject();
        }
        List<History> userNodesHistory = lfHistoryService.list(Wrappers.<History>lambdaQuery()
                .eq(History::getProcessId, process.getId())
                .eq(History::getNodeType, ProcessConstant.NodesType.USER)
                .orderByAsc(History::getSort));
        History leaderNodeHistory = userNodesHistory.stream().filter(h -> h.getNodeId().equals(leaderNodeId)).findFirst().orElse(null);
        // 获取到领导下发任务时的限定反馈超时时间
        String howLongKey = ProcessUtil.generatorFormDataKey(leaderNodeHistory.getId(), ProcessItemType.NODE, leaderNodeId, "howLong");
        Long howLong = formData.getLong(howLongKey);
        String timeUnitKey = ProcessUtil.generatorFormDataKey(leaderNodeHistory.getId(), ProcessItemType.NODE, leaderNodeId, "timeUnit");
        String timeUnit = formData.getString(timeUnitKey);
        if (howLong != null && timeUnit != null) {
            // 找到当前历史，也就是刚刚保存的用户节点的历史的索引，然后找这个用户节点的上一个用户节点也就是领导审批反馈或者领导下发节点
            int index = CollectionUtil.indexOf(userNodesHistory, h -> h.getId().equals(history.getId()));
            if (index > 1) {
                // 计算两个历史提交的时间差就是超时时间
                History lastUserNodesHistory = userNodesHistory.get(index - 1);
                // 之前的时间加上 限定的时长 如果在提交时间之前，说明超时了
                LocalDateTime shouldCompleteTime = lastUserNodesHistory.getTime().plus(howLong, (timeUnit.equalsIgnoreCase(TimeUnit.HOURS) ? ChronoUnit.HOURS : ChronoUnit.DAYS));
                boolean isTimeout = shouldCompleteTime.isBefore(history.getTime());
                // 这里如果已经超时了，需要存储一下超时记录
                if (isTimeout) {
                    Duration duration = Duration.between(shouldCompleteTime, history.getTime());
                    lfHistoryService.save(null, nodes, "执勤人员上报反馈节点自动任务超时[" + duration.toMinutes() + "（分钟）]");
                }
                return isTimeout;
            }
        }
        return true;
    }
}
