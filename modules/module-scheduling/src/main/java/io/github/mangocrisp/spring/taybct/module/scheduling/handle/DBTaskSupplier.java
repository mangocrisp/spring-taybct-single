package io.github.mangocrisp.spring.taybct.module.scheduling.handle;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledTask;
import io.github.mangocrisp.spring.taybct.module.scheduling.mapper.ScheduledTaskMapper;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.scheduling.handle.AbstractTaskSupplier;
import io.github.mangocrisp.spring.taybct.tool.scheduling.prop.ScheduledTaskBean;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 数据库任务支持
 *
 * @author xijieyin <br> 2022/11/1 16:43
 * @since 1.1.0
 */
@AutoConfiguration
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DBTaskSupplier extends AbstractTaskSupplier {

    final ScheduledTaskMapper scheduledTaskMapper;

    @Override
    public ScheduledTaskBean getByKey(String taskKey) {
        return Optional.ofNullable(scheduledTaskMapper.selectOne(Wrappers
                        .<ScheduledTask>lambdaQuery().eq(ScheduledTask::getTaskKey, taskKey)))
                .map(this::convert)
                .orElse(null);
    }

    @Override
    public List<ScheduledTaskBean> getAllTask() {
        throw new BaseException("这里不允许获取全部，请分页查询");
    }

    @Override
    public List<ScheduledTaskBean> getAllNeedStartTask() {
        List<ScheduledTaskBean> dbTask = new java.util.ArrayList<>(scheduledTaskMapper.getAllNeedStartTask().stream().map(this::convert).toList());
        List<ScheduledTaskBean> propertyTask = scheduledProperties.getTasks().values().stream()
                // 过滤掉，所有的数据库已经存在的，这里默认数据库的优先级更高
                .filter(s -> dbTask.stream().noneMatch(d -> d.getTaskKey().equals(s.getTaskKey())))
                .sorted(Comparator.comparingInt(ScheduledTaskBean::getSort))
                .filter(s -> s.getAutoStart() == 1)
                .toList();
        if (CollectionUtil.isNotEmpty(propertyTask)) {
            dbTask.addAll(propertyTask);
        }
        return dbTask;
    }

    @NotNull
    private ScheduledTaskBean convert(ScheduledTask task) {
        // 这里要考虑到有租户的区分
        JSONObject params = JSONObject.parseObject(task.getParams());
        if (params == null) {
            params = new JSONObject();
        }
        // 这里把 tenantId 丢到 params 里面方便后面对租户做操作
        params.put("tenantId", task.getTenantId());
        return new ScheduledTaskBean(task.getTaskKey(),
                task.getDescription(),
                task.getCron(),
                0,
                task.getAutoStart().intValue(),
                task.getSort(),
                params);
    }

}
