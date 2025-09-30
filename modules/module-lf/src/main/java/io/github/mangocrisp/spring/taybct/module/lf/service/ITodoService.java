package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Todo;
import io.github.mangocrisp.spring.taybct.module.lf.dto.TodoListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.TodoListCountVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.UnOperator;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;

import java.util.List;
import java.util.Set;

/**
 * @author admin
 * <br>description 针对表【lf_todo(待办、已办)】的数据库操作Service
 * @since 2023-07-17 10:05:31
 */
public interface ITodoService extends IService<Todo> {
    /**
     * 待办数量查询
     *
     * @param status 状态
     * @return 数量
     */
    TodoListCountVO todoListCount(Byte status);

    /**
     * 待办、已办列表
     *
     * @param dto            查询参数
     * @param sqlQueryParams 分布查询参数
     * @return 分页
     */
    IPage<ProcessListVO> todoList(TodoListQueryDTO dto, SqlQueryParams sqlQueryParams);

    /**
     * 查询流程未操作者
     *
     * @param processIdSet 流程 id
     * @return 未操作者集合
     */
    List<UnOperator> queryUnOperators(Set<Long> processIdSet);

}
