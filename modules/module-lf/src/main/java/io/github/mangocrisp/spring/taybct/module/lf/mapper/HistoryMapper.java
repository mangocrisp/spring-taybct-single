package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.HistoryListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_history(流程历史)】的数据库操作Mapper
 * @see History
 * @since 2023-07-03 11:32:23
 */
public interface HistoryMapper extends BaseMapper<History> {

    /**
     * 查询历史记录列表
     *
     * @param params 查询参数
     * @return list
     */
    List<HistoryListVO> historyList(@Param("params") HistoryListQueryDTO params);

}




