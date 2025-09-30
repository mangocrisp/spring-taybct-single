package io.github.mangocrisp.spring.taybct.module.scheduling.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledTask;
import io.github.mangocrisp.spring.taybct.module.scheduling.vo.ScheduledTaskVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 针对表【scheduled_task(调度任务)】的数据库操作Service
 *
 * @author 24154
 */
public interface IScheduledTaskService extends IBaseService<ScheduledTask> {
    /**
     * 任务分页（状态扩展）
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return {@code IPage<ScheduledTaskVO>} 任务分页
     */
    IPage<ScheduledTaskVO> taskPage(Map<String, Object> sqlQueryParams);

    /**
     * 获取需要自动启动的任务列表
     *
     * @return {@code List<ScheduledTask>} 需要启动的任务列表
     * @author xijieyin <br> 2022/12/6 11:19
     * @since 1.0.0
     */
    List<ScheduledTask> getAllNeedStartTask();

}
