package io.github.mangocrisp.spring.taybct.single.modules.lf.beans;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.module.lf.api.ProcessAutoDealHandler;
import io.github.mangocrisp.spring.taybct.module.lf.auto.LfServiceAutoConfigure;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.enums.ProcessItemType;
import io.github.mangocrisp.spring.taybct.module.lf.service.IEdgesService;
import io.github.mangocrisp.spring.taybct.module.lf.service.ILfHistoryService;
import io.github.mangocrisp.spring.taybct.module.lf.service.INodesService;
import io.github.mangocrisp.spring.taybct.module.lf.util.ProcessUtil;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <pre>
 * 根据领导指定的部门下发任务
 * 因业务需要，下一个用户任务需要领导指定的部门去执行，所以需要在这一步利用系统任务，
 * 把领导指定的部门代入到用户任务的指定部门
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/3 16:08
 */
@AutoConfiguration(value = "flowAlarmLeaderIssuance", after = LfServiceAutoConfigure.class)
@Slf4j
@RequiredArgsConstructor
public class FlowAlarmLeaderIssuance implements ProcessAutoDealHandler {

    final INodesService nodesService;
    final IEdgesService edgesService;
    final ILfHistoryService lfHistoryService;


    /** 领导审批下达任务节点 id*/
    final static String leaderNodeId = "8c1358a2501b41d38f28059ed121493a";

    @Override
    public boolean apply(History history, Process process, Edges edges, Nodes nodes) {
        JSONObject formData = ProcessUtil.getJSONObject(process.getFormData());
        if (formData == null) {
            formData = new JSONObject();
        }
        History leaderNodeHistory = lfHistoryService.getOne(Wrappers.<History>lambdaQuery()
                .eq(History::getProcessId, process.getId())
                .eq(History::getNodeId, leaderNodeId));
        // 获取领导指定的部门
        String key = ProcessUtil.generatorFormDataKey(leaderNodeHistory.getId(), ProcessItemType.NODE, leaderNodeId, "issuanceDept");
        String issuanceDept = formData.getString(key);
        // 根据当前节点（系统任务节点）获取他的连线的下个节点（用户处理节点），把指定的部门设置进去
        edgesService.selectBySourceId(nodes.getId()).forEach(edge -> {
            Nodes nextNodes = nodesService.getById(edges.getTargetNodeId());
            if (issuanceDept != null) {
                JSONObject nodesProperties = ProcessUtil.getJSONObject(nextNodes.getProperties());
                if (nodesProperties == null) {
                    throw new BaseException("获取用户节点 properties 为空，流程无法正常进行！");
                }
                JSONArray deptIdList = nodesProperties.getJSONArray("deptIdList");
                if (deptIdList == null) {
                    deptIdList = new JSONArray();
                }
                deptIdList.add(issuanceDept);
                nodesProperties.put("deptIdList", deptIdList);
                // 更新节点
                Nodes saveNode = new Nodes();
                saveNode.setId(nextNodes.getId());
                saveNode.setProperties(nodesProperties.toJSONString());
                nodesService.updateById(saveNode);
            }
        });
        return true;
    }
}
