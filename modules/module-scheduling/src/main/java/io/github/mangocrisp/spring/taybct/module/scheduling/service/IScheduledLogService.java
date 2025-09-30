package io.github.mangocrisp.spring.taybct.module.scheduling.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.scheduling.domain.ScheduledLog;

import java.util.Map;

/**
 * 针对表【scheduled_log(调度日志)】的数据库操作Service
 *
 * @author 24154
 */
public interface IScheduledLogService extends IService<ScheduledLog> {

    /**
     * 日志记录者
     *
     * @param record 记录
     */
    void logRecorder(JSONObject record);

    /**
     * 分页
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return 分页
     */
    IPage<ScheduledLog> page(Map<String, Object> sqlQueryParams);


}
