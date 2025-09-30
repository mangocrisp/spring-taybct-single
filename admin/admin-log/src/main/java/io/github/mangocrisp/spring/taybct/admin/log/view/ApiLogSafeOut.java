package io.github.mangocrisp.spring.taybct.admin.log.view;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 安全输出对象，去掉实体类不允许查看的字段，或者说，直接让这个字段有数据可读，或者加密一些敏感信息等操作。
 *
 * @author xijieyin <br> 2022/8/5 9:51
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "api_log")
@Data
@Schema(description = "接口日志(安全对象)")
public class ApiLogSafeOut extends ApiLog {

    @Serial
    private static final long serialVersionUID = -5322680232844327941L;

    @Override
    public String getUsername() {
        return StringUtil.encrypt(super.getUsername());
    }

}
