package io.github.mangocrisp.spring.taybct.admin.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 安全输入对象，去掉实体类不允许修改的字段，或者说，直接不允许修改某些字段如，创建时间，不能通过接口修改了这个来改变他原来的值
 *
 * @author xijieyin <br> 2022/8/5 9:50
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "api_log")
@Data
@Schema(description = "接口日志(安全对象)")
public class ApiLogSafeIn extends ApiLog {

    @Serial
    private static final long serialVersionUID = 8998759672796471718L;

    @Override
    public void setCreateTime(LocalDateTime createTime) {
    }

    @Override
    public void setCreateUser(Long createUser) {
    }

}
