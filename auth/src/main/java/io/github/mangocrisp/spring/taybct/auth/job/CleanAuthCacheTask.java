package io.github.mangocrisp.spring.taybct.auth.job;

import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.common.message.cheduledlog.ScheduledLogDTO;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.Scheduler;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.scheduling.job.RedisScheduledTaskJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.sql.Types;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 清理鉴权缓存，这里包括了
 * <br>- 使用 oauth2 自带的几种鉴权方式带来的，没有自动清理的缓存
 * <br>- 使用自定义的鉴权生成的 token ，刷新时间已经超时，了但是没有自动清理的缓存
 *
 * @author xijieyin <br> 2022/11/1 11:31
 * @since 1.1.0
 */
@AutoConfiguration
@Slf4j
@ConditionalOnClass({JdbcTemplate.class, RedisTemplate.class})
@Scheduler("cleanAuthCache")
public class CleanAuthCacheTask extends RedisScheduledTaskJob {

    private final JdbcTemplate jdbcTemplate;
    private static final String PK_FILTER = "authorization_grant_type = ?";
    private static final String TABLE_NAME = "oauth2_authorization";
    private static final String REMOVE_AUTHORIZATION_SQL = "DELETE FROM " + TABLE_NAME + " WHERE refresh_token_expires_at <= now() OR authorization_code_expires_at <= now() OR user_code_expires_at <= now() OR device_code_expires_at <= now() OR " + PK_FILTER;

    @Resource
    private IMessageSendService messageSendService;

    @Override
    protected Consumer<JSONObject> getLogRecorder() {
        return json -> messageSendService.send(new ScheduledLogDTO(json));
    }

    public CleanAuthCacheTask(
            JdbcTemplate jdbcTemplate
            , RedisTemplate<String, String> redisTemplate
            , Environment env) {
        super(redisTemplate, env);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(Map<String, Object> params) {
        log.debug("clearAuthCache => 当前线程名称 {} ", Thread.currentThread().getName());
        log.debug(">>>>>> 清理超时鉴权开始 >>>>>> ");
        SqlParameterValue[] parameters = new SqlParameterValue[]{
                new SqlParameterValue(Types.VARCHAR, AuthorizationGrantType.CLIENT_CREDENTIALS.getValue())
        };
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(parameters);
        jdbcTemplate.update(REMOVE_AUTHORIZATION_SQL, pss);
        log.debug(">>>>>> 清理超时鉴权结束 >>>>>> ");
    }

}
