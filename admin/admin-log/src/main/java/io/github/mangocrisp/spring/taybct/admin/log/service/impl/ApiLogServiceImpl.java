package io.github.mangocrisp.spring.taybct.admin.log.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.github.mangocrisp.spring.taybct.admin.log.dto.ApiLogQueryDTO;
import io.github.mangocrisp.spring.taybct.admin.log.mapper.ApiLogMapper;
import io.github.mangocrisp.spring.taybct.admin.log.service.IApiLogService;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;

import java.util.Map;
import java.util.Optional;

/**
 * {@inheritDoc}
 * <p>
 * 这里不要实现 BaseServiceImpl 因为有默认的更新人，这样的操作，日志也有登录日志，刚登录的时候是没有用户信息的，所以不要实现
 *
 * @author xijieyin <br> 2022/8/4 19:22
 * @since 1.0.0
 */
public class ApiLogServiceImpl extends ServiceImpl<ApiLogMapper, ApiLog>
        implements IApiLogService {

    @Override
    public Wrapper<ApiLog> customizeQueryWrapper(Map<String, Object> sqlQueryParams) {
        Wrapper<ApiLog> apiLogWrapper = MyBatisUtil.genQueryWrapper(sqlQueryParams, ApiLog.class);
        LambdaQueryWrapper<ApiLog> logLambdaQueryWrapper = ((QueryWrapper<ApiLog>) apiLogWrapper).lambda();
        ApiLogQueryDTO dto = JSONObject.parseObject(JSONObject.toJSONString(sqlQueryParams), ApiLogQueryDTO.class);
        // 开始时间
        Optional.ofNullable(dto.getStartTime())
                .ifPresent(timeBegin -> logLambdaQueryWrapper.ge(ApiLog::getCreateTime, timeBegin));
        // 结束时间
        Optional.ofNullable(dto.getEndTime())
                .ifPresent(timeEnd -> logLambdaQueryWrapper.le(ApiLog::getCreateTime, timeEnd));
        return logLambdaQueryWrapper;
    }

}




