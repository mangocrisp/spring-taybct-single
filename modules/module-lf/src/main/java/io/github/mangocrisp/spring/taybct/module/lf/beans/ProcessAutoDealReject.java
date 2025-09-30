package io.github.mangocrisp.spring.taybct.module.lf.beans;

import io.github.mangocrisp.spring.taybct.module.lf.api.ProcessAutoDealHandler;
import io.github.mangocrisp.spring.taybct.module.lf.constants.TodoListStatus;
import io.github.mangocrisp.spring.taybct.module.lf.constants.TodoStatus;
import io.github.mangocrisp.spring.taybct.module.lf.domain.*;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.enums.TodoType;
import io.github.mangocrisp.spring.taybct.module.lf.service.ITodoService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <pre>
 * 驳回 beans
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/12 17:56
 */
@AutoConfiguration("flowProcessAutoDealReject")
@Slf4j
@RequiredArgsConstructor
public class ProcessAutoDealReject implements ProcessAutoDealHandler {

    @Resource
    private ITodoService todoService;

    @Override
    public boolean apply(History history, Process process, Edges edges, Nodes nodes) {
        if (todoService != null) {
            // 给流程发起人发送一个驳回的待办
            Todo e = new Todo();
            e.setNodeId(nodes.getId());
            e.setUserId(process.getUserId());
            e.setProcessId(process.getId());
            // 设置当前用户处理已办
            e.setStatus(TodoListStatus.TODO);
            e.setType(process.getType());
            e.setTodoStatus(TodoStatus.REJECT);
            e.setTodoType(TodoType.Code.CC);
            e.setDesignId(process.getDesignId());
            todoService.save(e);
        }
        return false;
    }
}
