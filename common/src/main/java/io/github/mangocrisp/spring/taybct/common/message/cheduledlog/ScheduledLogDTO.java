package io.github.mangocrisp.spring.taybct.common.message.cheduledlog;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.mangocrisp.spring.taybct.tool.core.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * <pre>
 * 任务调度日志
 * </pre>
 *
 * @author XiJieYin
 * @since 2024/9/1 00:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledLogDTO implements Message {

    @Serial
    private static final long serialVersionUID = -3422463190806171503L;

    private JSONObject payload;

    @Override
    @JsonIgnore
    public String getPayload() {
        return payload.toJSONString(JSONWriter.Feature.WriteMapNullValue);
    }
}
