package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import com.alibaba.fastjson2.JSONArray;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 历史版本
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/4/22 09:15
 */
@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Schema(description = "历史版本")
public class History implements Serializable {

    @Serial
    private static final long serialVersionUID = 5342495814302806814L;

    /**
     * 定义保存文档后返回的 历史对象 的更改
     */
    @Schema(description = "定义保存文档后返回的 历史对象 的更改")
    private JSONArray changes;
    /**
     * 定义文档版本创建日期
     */
    @Schema(description = "定义文档版本创建日期")
    private String created;
    /**
     * 定义服务用来识别文档的唯一文档标识符
     */
    @Schema(description = "定义服务用来识别文档的唯一文档标识符")
    private String key;
    /**
     * 定义当前服务器版本号。 如果发送 changes 参数，则还需要发送 serverVersion 参数
     */
    @Schema(description = "定义当前服务器版本号。 如果发送 changes 参数，则还需要发送 serverVersion 参数")
    private String serverVersion;
    /**
     * 定义作为文档版本作者的用户
     */
    @Schema(description = "定义作为文档版本作者的用户")
    private HistoryUser user;
    /**
     * 定义文档版本号
     */
    @Schema(description = "定义文档版本号")
    private String version;

}
