package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 显示文档版本历史 DTO
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
@Schema(description = "显示文档版本历史 DTO")
public class RefreshHistoryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -9021548554632746465L;

    /**
     * 定义当前文档版本号
     */
    @Schema(description = "定义当前文档版本号")
    private String currentVersion;
    /**
     * 定义错误消息文本
     */
    @Schema(description = "定义错误消息文本")
    private String error;
    /**
     * 使用文档版本定义数组
     */
    @Schema(description = "使用文档版本定义数组")
    @Singular("addHistory")
    private List<History> history;

}
