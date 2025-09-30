package io.github.mangocrisp.spring.taybct.api.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysNotice;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysNoticeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 针对表【sys_notice(消息通知)】的数据库操作Mapper
 *
 * @author xijieyin <br> 2022/10/10 15:57
 * @see SysNotice
 * @since 1.0.5
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 统计查询
     *
     * @param relatedCondition 关联条件
     * @param params           查询参数
     * @return long
     */
    long countQuery(@Param("relatedCondition") Map<String, String> relatedCondition, @Param("params") Map<String, Object> params);

    /**
     * 条件查询
     *
     * @param relatedCondition 关联关系查询条件
     * @param params           查询参数
     * @param offset           查询起始位置
     * @param size             大小
     * @param pageOrder        排序字段
     * @return {@code List<SysNoticeVO>}
     * @author xijieyin <br> 2022/10/10 15:57
     * @since 1.0.5
     */
    List<SysNoticeVO> listQuery(@Param("relatedCondition") Map<String, String> relatedCondition
            , @Param("params") Map<String, Object> params
            , @Param("offset") Long offset
            , @Param("size") Long size
            , @Param("pageOrder") String pageOrder);

}




