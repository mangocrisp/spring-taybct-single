package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.mangocrisp.spring.taybct.module.lf.constants.ProcessConstant;
import io.github.mangocrisp.spring.taybct.module.lf.constants.ProcessStatus;
import io.github.mangocrisp.spring.taybct.module.lf.constants.TodoListStatus;
import io.github.mangocrisp.spring.taybct.module.lf.constants.TodoStatus;
import io.github.mangocrisp.spring.taybct.module.lf.domain.*;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryOperator;
import io.github.mangocrisp.spring.taybct.module.lf.dto.NodesSubmitDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ProcessNewDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.UserRequestListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.enums.ProcessItemType;
import io.github.mangocrisp.spring.taybct.module.lf.enums.TodoType;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.ProcessMapper;
import io.github.mangocrisp.spring.taybct.module.lf.pojo.BusinessField;
import io.github.mangocrisp.spring.taybct.module.lf.pojo.PermissionsDTO;
import io.github.mangocrisp.spring.taybct.module.lf.service.*;
import io.github.mangocrisp.spring.taybct.module.lf.util.ProcessUtil;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author admin
 * <br>description 针对表【lf_process(流程管理)】的数据库操作Service实现
 * @since 2023-07-03 11:32:23
 */
public class ProcessServiceImpl extends BaseServiceImpl<ProcessMapper, Process>
        implements IProcessService {

//    private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newProcess(ProcessNewDTO dto
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier
            , Supplier<ILfHistoryService> historyServiceSupplier
            , Supplier<IPresentProcessService> presentProcessServiceSupplier
            , Supplier<ITodoService> todoServiceSupplier) {

        boolean saved = getBaseMapper().newProcess(dto) > 0;

        // 保存完了再查询回来流程数据
        Process process = getById(dto.getId());

        JSONObject jsonData = ProcessUtil.getJSONObject(process.getData());
        // 获取节点集合
        if (jsonData != null) {
            // 获取到并且转换所有的节点集合
            List<Nodes> nodes = jsonData.getJSONArray(ProcessConstant.NODES).toJavaList(JSONObject.class)
                    .stream().map(node -> {
                        Nodes n = new Nodes();
                        // 节点 id
                        n.setId(node.getString(ProcessConstant.Nodes.ID));
                        // 流程 id
                        n.setProcessId(process.getId());
                        // 获取节点上的属性
                        n.setProperties(node.getJSONObject(ProcessConstant.Nodes.PROPERTIES).toJSONString());
                        // 节点类型
                        n.setType(node.getString(ProcessConstant.Nodes.TYPE));
                        // 节点上的文字
                        n.setText(Optional.ofNullable(node.getJSONObject(ProcessConstant.Nodes.TEXT))
                                .map(jsonObject -> jsonObject.getString(ProcessConstant.Nodes.VALUE)).orElse(null));
                        return n;
                    })
                    .collect(Collectors.toList());
            nodesServiceSupplier.get().saveOrUpdateBatch(nodes);
            // 设置一下开始节点的 id
            nodes.stream().filter(n -> n.getType().equalsIgnoreCase(ProcessConstant.NodesType.START))
                    .forEach(n -> dto.getStartNodes().setId(n.getId()));

            // 获取连线集合
            List<Edges> edges = jsonData.getJSONArray(ProcessConstant.EDGES).toJavaList(JSONObject.class)
                    .stream().map(edge -> {
                        Edges e = new Edges();
                        e.setId(edge.getString(ProcessConstant.Edges.ID));
                        e.setType(edge.getString(ProcessConstant.Edges.TYPE));
                        e.setTargetNodeId(edge.getString(ProcessConstant.Edges.TARGET_NODE_ID));
                        e.setSourceNodeId(edge.getString(ProcessConstant.Edges.SOURCE_NODE_ID));
                        e.setProperties(edge.getJSONObject(ProcessConstant.Edges.PROPERTIES).toJSONString());
                        e.setText(Optional.ofNullable(edge.getJSONObject(ProcessConstant.Edges.TEXT))
                                .map(jsonObject -> jsonObject.getString(ProcessConstant.Edges.VALUE)).orElse(null));
                        e.setProcessId(process.getId());
                        return e;
                    })
                    .collect(Collectors.toList());
            edgesServiceSupplier.get().saveOrUpdateBatch(edges);

            ILfHistoryService historyService = historyServiceSupplier.get();
            IPresentProcessService presentProcessService = presentProcessServiceSupplier.get();
            // 更新开始节点的属性数据
            Nodes startNodes = dto.getStartNodes();
            // 设置流程 id
            startNodes.setProcessId(process.getId());
            // 保存当前节点
            presentProcessService.save(startNodes);
            // 如果是开始节点，这里要添加第一个历史 和 当前节点
            History history = historyService.save(HistoryOperator.builder()
                    .userId(process.getUserId())
                    .deptId(process.getDeptId())
                    .postId(process.getPostId())
                    .build(), startNodes, "开始");
            updateFormData(history.getId(), process, startNodes);
            nextStep(() -> process
                    , () -> startNodes
                    , nodesServiceSupplier
                    , edgesServiceSupplier
                    , historyServiceSupplier
                    , presentProcessServiceSupplier
                    , todoServiceSupplier
                    , () -> ProcessUtil.getProcessFormData(process)
                    , history);
            // 直接异步处理下一步
//            cachedThreadPool.execute(() -> nextStep(() -> process
//                    , () -> startNodes
//                    , nodesServiceSupplier
//                    , edgesServiceSupplier
//                    , historyServiceSupplier
//                    , presentProcessServiceSupplier
//                    , todoServiceSupplier
//                    , () -> ProcessUtil.getProcessFormData(process)));
        }
        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean userSubmit(NodesSubmitDTO nodes
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier
            , Supplier<ILfHistoryService> historyServiceSupplier
            , Supplier<IPresentProcessService> presentProcessServiceSupplier
            , Supplier<ITodoService> todoServiceSupplier) {
        IEdgesService edgesService = edgesServiceSupplier.get();
        ILfHistoryService historyService = historyServiceSupplier.get();
        ITodoService todoService = todoServiceSupplier.get();
        ILoginUser loginUser = securityUtil.getLoginUser();
        // 根据节点里的 process id 获取
        Process process = getById(nodes.getProcessId());

        JSONObject nodesProperties = ProcessUtil.getJSONObject(nodes.getProperties());
        // 如果提交的时候会一起把连线的线也传过来，就直接使用
        Edges edges = nodes.getEdges();
        if (edges == null) {
            // 如果没有传，就需要两点确定一条线
            edges = edgesService.selectBySourceAndTargetId(nodes.getLastNodesId(), nodes.getId());
        }
        if (nodesProperties == null) {
            throw new BaseException("获取用户节点 properties 为空，流程无法正常进行！");
        }

        // 是否会签
        boolean isCountersign = nodesProperties.getBooleanValue(ProcessConstant.NodeProperties.IS_COUNTERSIGN);

        // 可以进行下一步
        boolean canNextStep;
        if (isCountersign) {
            // 如果是会签，就需要所有的用户都处理完成才能到下一个节点
            todoDone(nodes, todoService, process, loginUser);

            // 删除自己的待办，变成已办
            todoService.remove(Wrappers.<Todo>lambdaQuery()
                    .eq(Todo::getNodeId, nodes.getId())
                    .eq(Todo::getStatus, TodoListStatus.TODO)
                    .eq(Todo::getProcessId, nodes.getProcessId())
                    .eq(Todo::getUserId, loginUser.getUserId()));

            // 查询还有多少个待办，如果还有待办，说明还有其他用户没有处理完
            canNextStep = todoService.count(Wrappers.<Todo>lambdaQuery()
                    .eq(Todo::getNodeId, nodes.getId())
                    .eq(Todo::getStatus, TodoListStatus.TODO)) == 0;

        } else {
            // 如果不是会签节点，只要有一个人处理了就可以直接下一步了
            todoDone(nodes, todoService, process, loginUser);
            // 然后删除其他人对于这个节点的待办（因为当前用户已经处理完了）
            todoService.remove(Wrappers.<Todo>lambdaQuery()
                    .eq(Todo::getNodeId, nodes.getId())
                    .eq(Todo::getStatus, TodoListStatus.TODO)
                    .eq(Todo::getProcessId, nodes.getProcessId()));
            canNextStep = true;
        }

        // 添加一个历史记录
        History history = historyService.save(HistoryOperator.builder()
                .userId(loginUser.getUserId())
                .deptId(nodes.getDeptId())
                .postId(nodes.getPostId())
                .build(), nodes, nodes.getText());

        // 更新表单信息
        updateFormData(history.getId(), process, nodes);

        // 是否自动处理
        boolean autoExecute = nodesProperties.getBooleanValue(ProcessConstant.NodeProperties.AUTO_EXECUTE);
        if (autoExecute) {
            // 自动处理用户任务
            boolean nodeAutoResult = ProcessUtil.autoDeal(history
                    , process
                    , edges
                    , nodes
                    , nodesProperties
                    , () -> ProcessUtil.getProcessFormData(process));
            // 添加用户节点自动处理结果
            updateFormData(process, ProcessUtil.generatorFormData(history.getId(), ProcessItemType.NODE, nodes.getId(), ProcessConstant.AUTO_RESULT, nodeAutoResult));
        }

        if (canNextStep) {
            nextStep(() -> process
                    , () -> nodes
                    , nodesServiceSupplier
                    , edgesServiceSupplier
                    , historyServiceSupplier
                    , presentProcessServiceSupplier
                    , todoServiceSupplier
                    , () -> ProcessUtil.getProcessFormData(process)
                    , history);
            // 直接异步处理下一步
//            cachedThreadPool.execute(() -> nextStep(() -> process
//                    , () -> nodes
//                    , nodesServiceSupplier
//                    , edgesServiceSupplier
//                    , historyServiceSupplier
//                    , presentProcessServiceSupplier
//                    , todoServiceSupplier
//                    , () -> ProcessUtil.getProcessFormData(process)));
        }
        return true;
    }

    /**
     * 完成待办
     *
     * @param nodes       节点信息
     * @param todoService 处理待办
     * @param process     流程信息
     */
    private void todoDone(NodesSubmitDTO nodes, ITodoService todoService, Process process, ILoginUser loginUser) {
        Todo e = new Todo();
        e.setNodeId(nodes.getId());
        e.setUserId(loginUser.getUserId());
        // 设置当前用户处理已办
        e.setStatus(TodoListStatus.DONE);
        e.setProcessId(process.getId());
        e.setType(process.getType());
        e.setDesignId(process.getDesignId());
        todoService.save(e);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void nextStep(Supplier<Process> processSupplier
            , Supplier<Nodes> nodesSupplier
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier
            , Supplier<ILfHistoryService> historyServiceSupplier
            , Supplier<IPresentProcessService> presentProcessServiceSupplier
            , Supplier<ITodoService> todoServiceSupplier
            , Supplier<Map<String, Object>> contextSupplier
            , History history) {
        INodesService nodesService = nodesServiceSupplier.get();
        IEdgesService edgesService = edgesServiceSupplier.get();
        ILfHistoryService historyService = historyServiceSupplier.get();
        IPresentProcessService presentProcessService = presentProcessServiceSupplier.get();
        ITodoService todoService = todoServiceSupplier.get();
        Process process = processSupplier.get();
        Nodes nodes = nodesSupplier.get();
        // 检查是否有错误，比如，找不到节点这种问题
        int[] hasErr = new int[1];
        edgesService.selectBySourceId(nodes.getId()).forEach(edges -> {
            if (hasErr[0] == 1) {
                return;
            }
            Nodes nextNodes = nodesService.getById(edges.getTargetNodeId());
            // 连线属性
            JSONObject edgeProperties;
            edgeProperties = ProcessUtil.getJSONObject(edges.getProperties());
            // 自动处理，用于判断连线是否可以进行
            Boolean edgesAutoResult = ProcessUtil.autoDeal(history, process, edges, nextNodes, edgeProperties, contextSupplier);
            // 添加连线处理结果
            updateFormData(process, ProcessUtil.generatorFormData(history.getId(), ProcessItemType.EDGE, edges.getId(), ProcessConstant.AUTO_RESULT, edgesAutoResult));
            if (!edgesAutoResult) {
                return;
            }
            // 激活节点/连线
            activation(() -> process, () -> nodes, () -> edges, nodesServiceSupplier, edgesServiceSupplier);
            if (nextNodes == null) {
                // 更新流程状态为结束状态
                super.update(new Process()
                        , Wrappers.<Process>lambdaUpdate()
                                .set(Process::getStatus, ProcessStatus.END)
                                .set(Process::getCause, String.format("找不到节点[%s]", edges.getTargetNodeId()))
                                .eq(Process::getId, process.getId()));
                hasErr[0] = 1;
            } else {
                JSONObject nextNodesProperties = ProcessUtil.getJSONObject(nextNodes.getProperties());
                // 属性
                String nodesType = nextNodes.getType();
                switch (nodesType) {
                    case ProcessConstant.NodesType.USER -> {
                        // 如果是用户任务就直接添加一个历史节点 和 添加当前节点，因为这里需要用户去处理
                        // 这里保存用户节点的权限配置
                        ArrayList<Todo> todoList = new ArrayList<>();
                        // 是否抄送
                        AtomicBoolean isCC = new AtomicBoolean(false);
                        if (nextNodesProperties != null) {
                            isCC.set(nextNodesProperties.getBooleanValue(ProcessConstant.NodeProperties.IS_CC, false));
                            Optional.ofNullable(nextNodesProperties.getJSONArray(ProcessConstant.NodeProperties.USER_ID_LIST))
                                    .map(u -> u.toJavaList(PermissionsDTO.class))
                                    .ifPresent(list -> list.forEach(u -> {
                                        Todo e = new Todo();
                                        e.setNodeId(nextNodes.getId());
                                        e.setUserId(u.getId());
                                        e.setStatus(TodoListStatus.TODO);
                                        e.setTodoStatus(TodoStatus.TO_BE_READ);
                                        e.setProcessId(process.getId());
                                        e.setType(process.getType());
                                        e.setDesignId(process.getDesignId());
                                        if (isCC.get()) {
                                            e.setTodoType(TodoType.Code.CC);
                                        }
                                        todoList.add(e);
                                    }));
                            if (!isCC.get()) {
                                // 如果不是抄送节点才能指定部门和角色待办
                                Optional.ofNullable(nextNodesProperties.getJSONArray(ProcessConstant.NodeProperties.ROLES))
                                        .map(r -> r.toJavaList(PermissionsDTO.class))
                                        .ifPresent(list -> list.forEach(r -> {
                                            Todo e = new Todo();
                                            e.setNodeId(nextNodes.getId());
                                            e.setRoleId(r.getId());
                                            e.setStatus(TodoListStatus.TODO);
                                            e.setTodoStatus(TodoStatus.TO_BE_READ);
                                            e.setProcessId(process.getId());
                                            e.setType(process.getType());
                                            e.setDesignId(process.getDesignId());
                                            todoList.add(e);
                                        }));
                                Optional.ofNullable(nextNodesProperties.getJSONArray(ProcessConstant.NodeProperties.DEPT_ID_LIST))
                                        .map(d -> d.toJavaList(PermissionsDTO.class))
                                        .ifPresent(list -> list.forEach(d -> {
                                            Todo e = new Todo();
                                            e.setNodeId(nextNodes.getId());
                                            e.setDeptId(d.getId());
                                            e.setStatus(TodoListStatus.TODO);
                                            e.setTodoStatus(TodoStatus.TO_BE_READ);
                                            e.setProcessId(process.getId());
                                            e.setType(process.getType());
                                            e.setDesignId(process.getDesignId());
                                            todoList.add(e);
                                        }));
                            }
                        }
                        if (CollectionUtil.isEmpty(todoList)) {
                            throw new BaseException("流程配置有误，未以任何形式配置用户节点指定用户/部门/角色待办").setHttpStatus(HttpStatus.BAD_REQUEST);
                        }

                        // 添加权限配置，绑定，用户/角色/部门，只有会签才是只有所有用户都处理完才到下一个节点，但是如果不是会签，就不管是谁，只要处理了就进入下一个节点了
                        todoService.saveOrUpdateBatch(todoList);
                        // 添加下一个节点为当前节点
                        presentProcessService.save(nextNodes);
                        if (isCC.get()) {
                            // 如果用户节点是抄送节点，就直接下一步
                            nextStep(() -> process
                                    , () -> nextNodes
                                    , nodesServiceSupplier
                                    , edgesServiceSupplier
                                    , historyServiceSupplier
                                    , presentProcessServiceSupplier
                                    , todoServiceSupplier
                                    , () -> ProcessUtil.getProcessFormData(process)
                                    , history);
                        }
                        // TODO 用户节点的自动处理，需要放在用户提交的方法里面处理，提交完再自动处理，再有就是历史记录需要在提交的时候记录
                    }
                    case ProcessConstant.NodesType.SERVICE -> {// 系统任务
                        // 添加一个历史记录
                        History h = historyService.save(null, nextNodes, nextNodes.getText());
                        // 自动处理系统任务
                        boolean nodeAutoResult = ProcessUtil.autoDeal(history, process, edges, nextNodes, nextNodesProperties, contextSupplier);
                        // 这里更新一下流程的表单属性，把系统自动处理的结果添加进表单里面
                        updateFormData(process, ProcessUtil.generatorFormData(h.getId(), ProcessItemType.NODE, nextNodes.getId(), ProcessConstant.AUTO_RESULT, nodeAutoResult));
                        nextStep(() -> process
                                , () -> nextNodes
                                , nodesServiceSupplier
                                , edgesServiceSupplier
                                , historyServiceSupplier
                                , presentProcessServiceSupplier
                                , todoServiceSupplier
                                , () -> ProcessUtil.getProcessFormData(process)
                                , h);
                    }
                    case ProcessConstant.NodesType.JUDGMENT -> {
                        // 添加一个历史记录
                        historyService.save(null, nextNodes, nextNodes.getText());
                        // 如果是条件判断节点，就直接下一步
                        nextStep(() -> process
                                , () -> nextNodes
                                , nodesServiceSupplier
                                , edgesServiceSupplier
                                , historyServiceSupplier
                                , presentProcessServiceSupplier
                                , todoServiceSupplier
                                , () -> ProcessUtil.getProcessFormData(process)
                                , history);
                    }
                    case ProcessConstant.NodesType.END -> {// 结束节点
                        // 添加下一个节点为当前节点
                        presentProcessService.save(nextNodes);
                        // 更新流程状态为结束状态
                        super.update(new Process()
                                , Wrappers.<Process>lambdaUpdate().set(Process::getStatus, ProcessStatus.END)
                                        .eq(Process::getId, process.getId()));
                        // 完成之后，把所有的待办都变成已办
                        todoService.update(new Todo(), Wrappers.<Todo>lambdaUpdate()
                                .set(Todo::getStatus, TodoListStatus.DONE)
                                .eq(Todo::getProcessId, process.getId()));
                        // 添加一个历史记录
                        History h = historyService.save(null, nextNodes, nextNodes.getText());
                        // 结束之后也可以进行自动处理
                        boolean nodeAutoResult = ProcessUtil.autoDeal(history, process, edges, nextNodes, nextNodesProperties, contextSupplier);
                        // 这里更新一下流程的表单属性，把系统自动处理的结果添加进表单里面
                        updateFormData(process, ProcessUtil.generatorFormData(h.getId(), ProcessItemType.NODE, nextNodes.getId(), ProcessConstant.AUTO_RESULT, nodeAutoResult));
                    }
                }
            }
        });
    }

    @Override
    public IPage<ProcessListVO> userRequestList(UserRequestListQueryDTO dto, SqlQueryParams sqlQueryParams) {
        Page<ProcessListVO> page = MyBatisUtil.genPage(sqlQueryParams);
        long total = getBaseMapper().userRequestListCount(dto);
        List<ProcessListVO> list = Collections.emptyList();
        if (total > 0) {
            list = getBaseMapper().userRequestList(dto
                    , Optional.of(page.getCurrent()).map(c -> (c - 1) * page.getSize()).orElse(null)
                    , page.getSize()
                    , MyBatisUtil.getPageOrder(sqlQueryParams));
        }
        page.setTotal(total);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean updateFormData(Long historyId, Long processId, Nodes nodes) {
        // 获取流程中的表单
        return updateFormData(historyId, super.getById(processId), nodes);
    }

    @Override
    public boolean updateFormData(Long historyId, Process process, Nodes nodes) {
        JSONObject properties = ProcessUtil.getJSONObject(nodes.getProperties());
        if (properties != null) {
            JSONArray jsonArray = properties.getJSONArray(ProcessConstant.NodeProperties.FIELDS);
            if (jsonArray != null) {
                JSONObject formData = ProcessUtil.getJSONObject(process.getFormData());
                if (formData == null) {
                    formData = new JSONObject();
                }
                JSONObject finalFormData = formData;
                jsonArray.toJavaList(BusinessField.class).forEach(field -> {
                    // 按照 {node/edge}__{节点id}_{字段名} : 字段值 的方式存入到表单里面
//                    finalFormData.put(String.format("node_%s_%s"
//                            , nodes.getId()
//                            , field.getName()), field.getValue());
                    ProcessUtil.generatorFormData(finalFormData, historyId, ProcessItemType.NODE, nodes.getId(), field.getName(), field.getValue());
                });
                Process entity = new Process();
                entity.setId(process.getId());
                entity.setFormData(finalFormData.toJSONString());
                process.setFormData(finalFormData.toJSONString());
                return super.updateById(entity);
            }
        }
        return false;
    }

    @Override
    public boolean updateFormData(Long processId, JSONObject formData) {
        // 获取流程中的表单
        return updateFormData(super.getById(processId), formData);
    }

    @Override
    public boolean updateFormData(Process process, JSONObject formData) {
        JSONObject processFormData = ProcessUtil.getJSONObject(process.getFormData());
        if (processFormData == null) {
            processFormData = new JSONObject();
        }
        JSONObject finalFormData = processFormData;
        finalFormData.putAll(formData);
        Process entity = new Process();
        entity.setId(process.getId());
        entity.setFormData(finalFormData.toJSONString());
        process.setFormData(finalFormData.toJSONString());
        return super.updateById(entity);
    }

    @Override
    public boolean activation(Supplier<Process> processSupplier
            , Supplier<Nodes> nodesSupplier
            , Supplier<Edges> edgesSupplier
            , Supplier<INodesService> nodesServiceSupplier
            , Supplier<IEdgesService> edgesServiceSupplier) {
        Process process = processSupplier.get();
        JSONObject processData = ProcessUtil.getJSONObject(process.getData());
        if (processData == null) {
            return false;
        }
        Nodes nodes = nodesSupplier.get();
        Edges edges = edgesSupplier.get();
        // 获取到并且转换所有的节点集合
        List<Nodes> saveNodes = processData.getJSONArray(ProcessConstant.NODES).toJavaList(JSONObject.class)
                .stream()
                .filter(node -> node.getString(ProcessConstant.Nodes.ID).equals(nodes.getId()))
                .peek(node -> {
                    JSONObject properties = node.getJSONObject(ProcessConstant.Nodes.PROPERTIES);
                    properties.put(ProcessConstant.Nodes.IS_ACTIVATED, true);
                })
                .map(node -> {
                    Nodes n = new Nodes();
                    // 节点 id
                    n.setId(node.getString(ProcessConstant.Nodes.ID));
                    // 流程 id
                    n.setProcessId(process.getId());
                    // 获取节点上的属性
                    n.setProperties(nodes.getProperties());
                    // 节点类型
                    n.setType(node.getString(ProcessConstant.Nodes.TYPE));
                    // 节点上的文字
                    n.setText(Optional.ofNullable(node.getJSONObject(ProcessConstant.Nodes.TEXT))
                            .map(jsonObject -> jsonObject.getString(ProcessConstant.Nodes.VALUE)).orElse(null));
                    return n;
                })
                .collect(Collectors.toList());
        nodesServiceSupplier.get().saveOrUpdateBatch(saveNodes);
        // 获取连线集合
        List<Edges> saveEdges = processData.getJSONArray(ProcessConstant.EDGES).toJavaList(JSONObject.class)
                .stream()
                .filter(edge -> edge.getString(ProcessConstant.Edges.ID).equals(edges.getId()))
                .peek(edge -> {
                    JSONObject properties = edge.getJSONObject(ProcessConstant.Edges.PROPERTIES);
                    properties.put(ProcessConstant.Edges.IS_ACTIVATED, true);
                })
                .map(edge -> {
                    Edges e = new Edges();
                    e.setId(edge.getString(ProcessConstant.Edges.ID));
                    e.setType(edge.getString(ProcessConstant.Edges.TYPE));
                    e.setTargetNodeId(edge.getString(ProcessConstant.Edges.TARGET_NODE_ID));
                    e.setSourceNodeId(edge.getString(ProcessConstant.Edges.SOURCE_NODE_ID));
                    e.setProperties(edges.getProperties());
                    e.setText(Optional.ofNullable(edge.getJSONObject(ProcessConstant.Edges.TEXT))
                            .map(jsonObject -> jsonObject.getString(ProcessConstant.Edges.VALUE)).orElse(null));
                    e.setProcessId(process.getId());
                    return e;
                })
                .collect(Collectors.toList());
        edgesServiceSupplier.get().saveOrUpdateBatch(saveEdges);
        return updateData(process, processData);
    }

    @Override
    public boolean updateData(Process process, JSONObject data) {
        if (data == null) {
            return false;
        }
        Process entity = new Process();
        entity.setId(process.getId());
        entity.setData(data.toJSONString());
        process.setData(data.toJSONString());
        return super.updateById(entity);
    }

}
