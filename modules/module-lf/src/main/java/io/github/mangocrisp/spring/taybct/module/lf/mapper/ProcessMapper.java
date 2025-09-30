package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.dto.UserRequestListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_process(流程管理)】的数据库操作Mapper
 * @see Process
 * @since 2023-07-03 11:32:23
 */
public interface ProcessMapper extends BaseMapper<Process> {

    /**
     * 新建流程
     *
     * @param process 流程信息
     * @return 影响行数
     */
    int newProcess(@Param("process") Process process);

    /**
     * 我的请求数量
     *
     * @param params 查询参数
     * @return 数量
     */
    long userRequestListCount(@Param("params") UserRequestListQueryDTO params);

    /**
     * 我的请求分页查询
     *
     * @param params    查询参数
     * @param offset    查询起始位置
     * @param size      大小
     * @param pageOrder 排序字段
     * @return 查询列表结果
     */
    List<ProcessListVO> userRequestList(@Param("params") UserRequestListQueryDTO params
            , @Param("offset") Long offset
            , @Param("size") Long size
            , @Param("pageOrder") String pageOrder);
}




