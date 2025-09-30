package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.lf.constants.TodoListStatus;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Todo;
import io.github.mangocrisp.spring.taybct.module.lf.dto.TodoListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.TodoMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.ITodoService;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.TodoListCountVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.UnOperator;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author admin
 * <br>description 针对表【lf_todo(待办、已办)】的数据库操作Service实现
 * @since 2023-07-17 10:05:31
 */
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo>
        implements ITodoService {

    @Override
    public TodoListCountVO todoListCount(Byte status) {
        if (status == null) {
            status = TodoListStatus.TODO;
        }
        TodoListCountVO vo = new TodoListCountVO();
        vo.setTodoCount(getBaseMapper().todoCount(status, "todo_status"));
        vo.setDoneCount(getBaseMapper().todoCount(status, "done_status"));
        vo.setTypeCount(getBaseMapper().todoCount(status, "type"));
        vo.setDesignCount(getBaseMapper().todoCount(status, "design_id"));
        return vo;
    }

    @Override
    public IPage<ProcessListVO> todoList(TodoListQueryDTO dto, SqlQueryParams sqlQueryParams) {
        Page<ProcessListVO> page = MyBatisUtil.genPage(sqlQueryParams);
        long total = getBaseMapper().todoListCount(dto);
        List<ProcessListVO> list = Collections.emptyList();
        if (total > 0) {
            list = getBaseMapper().todoList(dto
                    , Optional.of(page.getCurrent()).map(c -> (c - 1) * page.getSize()).orElse(null)
                    , page.getSize()
                    , MyBatisUtil.getPageOrder(sqlQueryParams));
        }
        page.setTotal(total);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<UnOperator> queryUnOperators(Set<Long> processIdSet) {
        return getBaseMapper().queryUnOperators(processIdSet);
    }

}




