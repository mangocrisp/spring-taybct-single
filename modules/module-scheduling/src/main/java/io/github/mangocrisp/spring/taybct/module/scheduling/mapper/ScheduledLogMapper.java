package io.github.mangocrisp.spring.taybct.module.scheduling.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledLog;

/**
 * 针对表【scheduled_log(调度日志)】的数据库操作Mapper
 *
 * @author 24154
 * @see ScheduledLog
 */
public interface ScheduledLogMapper extends BaseMapper<ScheduledLog> {

    /**
     * 因为记录日志是不会知道是哪个租户的，所以这里插入操作是可以直接指定插入到哪个租户里面的
     *
     * @param entity 日志实体
     * @return int
     */
    @Override
    @InterceptorIgnore(tenantLine = "true")
    int insert(ScheduledLog entity);

}




