package io.github.mangocrisp.spring.taybct.module.scheduling.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledLog;
import io.github.mangocrisp.spring.taybct.module.scheduling.dto.ScheduledLogQueryDTO;
import io.github.mangocrisp.spring.taybct.module.scheduling.mapper.ScheduledLogMapper;
import io.github.mangocrisp.spring.taybct.module.scheduling.service.IScheduledLogService;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * 针对表【scheduled_log(调度日志)】的数据库操作Service实现
 *
 * @author 24154
 */
public class ScheduledLogServiceImpl extends ServiceImpl<ScheduledLogMapper, ScheduledLog>
        implements IScheduledLogService {

    @Override
    public void logRecorder(JSONObject record) {
        JSONObject params = record.getJSONObject("params");
        ScheduledLog scheduledLog = record.toJavaObject(ScheduledLog.class);
        assert scheduledLog != null;
        scheduledLog.setUpdateTime(LocalDateTime.now());
        String tenantId = params.getString("tenantId");
        scheduledLog.setTenantId(tenantId);
        scheduledLog.setId(IdWorker.getId());
        save(scheduledLog);
    }

    @Override
    public IPage<ScheduledLog> page(Map<String, Object> sqlQueryParams) {
        Wrapper<ScheduledLog> scheduledLogWrapper = MyBatisUtil.genQueryWrapper(sqlQueryParams, ScheduledLog.class);
        LambdaQueryWrapper<ScheduledLog> logLambdaQueryWrapper = ((QueryWrapper<ScheduledLog>) scheduledLogWrapper).lambda();
        ScheduledLogQueryDTO dto = JSONObject.parseObject(JSONObject.toJSONString(sqlQueryParams), ScheduledLogQueryDTO.class);
        // 开始时间
        Optional.ofNullable(dto.getTimeBegin())
                .ifPresent(timeBegin -> logLambdaQueryWrapper.ge(ScheduledLog::getStartTime, timeBegin));
        // 结束时间
        Optional.ofNullable(dto.getTimeEnd())
                .ifPresent(timeEnd -> logLambdaQueryWrapper.le(ScheduledLog::getStartTime, timeEnd));
        return page(MyBatisUtil.genPage(sqlQueryParams), scheduledLogWrapper);
    }

}




