package io.github.mangocrisp.spring.taybct.module.scheduling.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledTask;

import java.util.List;

/**
 * 针对表【scheduled_task(调度任务)】的数据库操作Mapper
 *
 * @author 24154
 * @see ScheduledTask
 */
public interface ScheduledTaskMapper extends BaseMapper<ScheduledTask> {

    /**
     * 获取程序初始化需要自启的任务信息
     *
     * @return {@code List<ScheduledTask>}
     */
    @InterceptorIgnore(tenantLine = "true")
    List<ScheduledTask> getAllNeedStartTask();

}




