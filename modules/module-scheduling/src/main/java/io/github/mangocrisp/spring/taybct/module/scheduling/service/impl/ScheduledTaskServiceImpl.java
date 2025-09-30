package io.github.mangocrisp.spring.taybct.module.scheduling.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledTask;
import io.github.mangocrisp.spring.taybct.module.scheduling.mapper.ScheduledTaskMapper;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledTaskService;
import io.github.mangocrisp.spring.taybct.module.scheduling.vo.ScheduledTaskVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.scheduling.service.ISchedulingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 针对表【scheduled_task(调度任务)】的数据库操作Service实现
 *
 * @author 24154
 */
public class ScheduledTaskServiceImpl extends BaseServiceImpl<ScheduledTaskMapper, ScheduledTask>
        implements IScheduledTaskService {

    @Autowired(required = false)
    protected ISchedulingService schedulingService;

    @Override
    public IPage<ScheduledTaskVO> taskPage(Map<String, Object> sqlQueryParams) {
        IPage<ScheduledTask> scheduledTaskPage = page(customizeQueryPage(sqlQueryParams), customizeQueryWrapper(sqlQueryParams));
        Page<ScheduledTaskVO> scheduledTaskVoPage = new Page<>();

        scheduledTaskVoPage.setTotal(scheduledTaskPage.getTotal());
        scheduledTaskVoPage.setPages(scheduledTaskPage.getPages());
        scheduledTaskVoPage.setCurrent(scheduledTaskPage.getCurrent());
        scheduledTaskVoPage.setSize(scheduledTaskPage.getSize());

        List<ScheduledTask> records = scheduledTaskPage.getRecords();
        List<ScheduledTaskVO> scheduledTaskVoS = BeanUtil.copyToList(records, ScheduledTaskVO.class);
        scheduledTaskVoS.forEach(task -> {
            String taskKey = task.getTaskKey();
            //是否启动标记处理
            task.setStartFlag(schedulingService.isStart(taskKey) ? 1 : 0);
        });
        scheduledTaskVoPage.setRecords(scheduledTaskVoS);
        return scheduledTaskVoPage;
    }

    @Override
    public List<ScheduledTask> getAllNeedStartTask() {
        return getBaseMapper().getAllNeedStartTask();
    }
}




