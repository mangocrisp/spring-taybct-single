package io.github.mangocrisp.spring.taybct.module.lf.controller.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.controller.IProcessController;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.dto.NodesSubmitDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ProcessNewDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.TodoListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.UserRequestListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.service.*;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.TodoListCountVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.UnOperator;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

/**
 * @author XiJieYin <br> 2023/7/25 16:36
 */
public class ProcessControllerRegister implements IProcessController {
    @Autowired(required = false)
    protected IProcessService processService;
    @Autowired(required = false)
    protected INodesService nodesService;
    @Autowired(required = false)
    protected IEdgesService edgesService;
    @Autowired(required = false)
    protected IPresentProcessService presentProcessService;
    @Autowired(required = false)
    protected ILfHistoryService lfHistoryService;
    @Autowired(required = false)
    protected ITodoService todoService;

    public IProcessService getProcessService() {
        return processService;
    }

    public INodesService getNodesService() {
        return nodesService;
    }

    public IEdgesService getEdgesService() {
        return edgesService;
    }

    public IPresentProcessService getPresentProcessService() {
        return presentProcessService;
    }

    public ILfHistoryService getLfHistoryService() {
        return lfHistoryService;
    }

    public ITodoService getTodoService() {
        return todoService;
    }

    @Override
    public R<?> newProcess(@RequestBody @Valid @NotNull ProcessNewDTO process) {
        return getProcessService().newProcess(process
                , this::getNodesService
                , this::getEdgesService
                , this::getLfHistoryService
                , this::getPresentProcessService
                , this::getTodoService) ? R.ok() : R.fail("创建申请失败！");
    }

    @Override
    public R<TodoListCountVO> todoListCount(@PathVariable Byte status) {
        return R.data(getTodoService().todoListCount(status));
    }

    @Override
    public R<IPage<ProcessListVO>> todoList(@RequestBody TodoListQueryDTO dto, SqlQueryParams sqlQueryParams) {
        return R.data(getTodoService().todoList(dto, sqlQueryParams));
    }

    @Override
    public R<List<UnOperator>> queryUnOperators(@RequestBody Set<Long> processIdSet) {
        return R.data(getTodoService().queryUnOperators(processIdSet));
    }

    @Override
    public R<IPage<ProcessListVO>> userRequestList(@RequestBody UserRequestListQueryDTO dto, SqlQueryParams sqlQueryParams) {
        return R.data(getProcessService().userRequestList(dto, sqlQueryParams));
    }

    @Override
    public R<?> userSubmit(@RequestBody NodesSubmitDTO nodes) {
        return getProcessService().userSubmit(nodes
                , this::getNodesService
                , this::getEdgesService
                , this::getLfHistoryService
                , this::getPresentProcessService
                , this::getTodoService) ? R.ok() : R.fail("提交失败！");
    }

    @Override
    public R<Process> detail(@PathVariable Long id) {
        return R.data(getProcessService().getById(id));
    }

}
