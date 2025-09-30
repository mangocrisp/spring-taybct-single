package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.dict.NodeType;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.dto.NodesSubmitDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ProcessNewDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.UserRequestListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author admin
 * <br>description 针对表【lf_process(流程管理)】的数据库操作Service
 * @since 2023-07-03 11:32:23
 */
public interface IProcessService extends IBaseService<Process> {
    /**
     * 新建流程
     *
     * @param process                       流程数据
     * @param nodesServiceSupplier          处理节点
     * @param edgesServiceSupplier          处理边
     * @param historyServiceSupplier        处理历史记录
     * @param presentProcessServiceSupplier 处理当前节点
     * @param todoServiceSupplier           处理待办
     * @return boolean
     */
    boolean newProcess(ProcessNewDTO process
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier
            , Supplier<ILfHistoryService> historyServiceSupplier
            , Supplier<IPresentProcessService> presentProcessServiceSupplier
            , Supplier<ITodoService> todoServiceSupplier);

    /**
     * 用户提交待办
     *
     * @param nodes                         提交的节点
     * @param nodesServiceSupplier          处理节点
     * @param edgesServiceSupplier          处理边
     * @param historyServiceSupplier        处理历史记录
     * @param presentProcessServiceSupplier 处理当前节点
     * @param todoServiceSupplier           处理待办
     * @return boolean
     */
    boolean userSubmit(NodesSubmitDTO nodes
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier
            , Supplier<ILfHistoryService> historyServiceSupplier
            , Supplier<IPresentProcessService> presentProcessServiceSupplier
            , Supplier<ITodoService> todoServiceSupplier);

    /**
     * 流程的下一步
     *
     * @param processSupplier               流程信息
     * @param nodesSupplier                 需要下一步的节点信息
     * @param nodesServiceSupplier          处理节点
     * @param edgesServiceSupplier          处理边
     * @param historyServiceSupplier        处理历史记录
     * @param presentProcessServiceSupplier 处理当前节点
     * @param todoServiceSupplier           处理待办
     * @param contextSupplier               提供可操作的表单属性
     * @see NodeType
     */
    void nextStep(Supplier<Process> processSupplier
            , Supplier<Nodes> nodesSupplier
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier
            , Supplier<ILfHistoryService> historyServiceSupplier
            , Supplier<IPresentProcessService> presentProcessServiceSupplier
            , Supplier<ITodoService> todoServiceSupplier
            , Supplier<Map<String, Object>> contextSupplier
            , History history);

    /**
     * 用户的任务列表查询
     *
     * @param dto            查询参数
     * @param sqlQueryParams 分布查询参数
     * @return 分页
     */
    IPage<ProcessListVO> userRequestList(UserRequestListQueryDTO dto, SqlQueryParams sqlQueryParams);

    /**
     * 更新流程表单字段
     *
     * @param historyId 历史记录 id（防止一个节点被重复处理 from 表单数据重复问题）
     * @param processId 流程 id
     * @param nodes     节点信息
     * @return boolean
     */
    boolean updateFormData(Long historyId, Long processId, Nodes nodes);

    /**
     * 更新流程表单字段
     *
     * @param historyId 历史记录 id（防止一个节点被重复处理 from 表单数据重复问题）
     * @param process   流程信息
     * @param nodes     节点信息
     * @return boolean
     */
    boolean updateFormData(Long historyId, Process process, Nodes nodes);

    /**
     * 更新流程表单字段
     *
     * @param processId 流程 id
     * @param formData  表单信息
     * @return boolean
     */
    boolean updateFormData(Long processId, JSONObject formData);

    /**
     * 更新流程表单字段
     *
     * @param process  流程 信息
     * @param formData 表单信息
     * @return boolean
     */
    boolean updateFormData(Process process, JSONObject formData);

    /**
     * 激活节点，连线
     *
     * @param processSupplier      流程信息
     * @param nodesSupplier        节点
     * @param edgesSupplier        连线
     * @param nodesServiceSupplier 处理节点
     * @param edgesServiceSupplier 处理边
     * @return boolean
     */
    boolean activation(Supplier<Process> processSupplier
            , Supplier<Nodes> nodesSupplier
            , Supplier<Edges> edgesSupplier
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier);

    /**
     * 更新流程实时数据
     *
     * @param process 流程信息
     * @param data    数据
     * @return boolean
     */
    boolean updateData(Process process, JSONObject data);

}
