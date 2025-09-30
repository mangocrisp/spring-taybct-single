package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Todo;
import io.github.mangocrisp.spring.taybct.module.lf.dto.TodoListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.TodoCount;
import io.github.mangocrisp.spring.taybct.module.lf.vo.UnOperator;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author admin
 * <br>description 针对表【lf_todo(待办、已办)】的数据库操作Mapper
 * @see Todo
 * @since 2023-07-17 10:05:31
 */
public interface TodoMapper extends BaseMapper<Todo> {

    /**
     * 待办数量分类查询
     *
     * @param kind 按什么分类
     * @return 数量
     */
    List<TodoCount> todoCount(@Param("status") Byte status, @Param("kind") String kind);

    /**
     * 待办数量
     *
     * @param params 查询参数
     * @return 数量
     */
    long todoListCount(@Param("params") TodoListQueryDTO params);

    /**
     * 待办分页查询
     *
     * @param params    查询参数
     * @param offset    查询起始位置
     * @param size      大小
     * @param pageOrder 排序字段
     * @return 查询列表结果
     */
    List<ProcessListVO> todoList(@Param("params") TodoListQueryDTO params
            , @Param("offset") Long offset
            , @Param("size") Long size
            , @Param("pageOrder") String pageOrder);

    /**
     * 查询未操作者
     *
     * @param processIdSet 流程 id
     * @return 未操作者
     */
    List<UnOperator> queryUnOperators(@Param("processIdSet") Set<Long> processIdSet);

}




