package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 发送文档链接以查看版本历史
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/4/22 09:41
 */
@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Schema(description = "历史数据")
public class HistoryData implements Serializable {

    @Serial
    private static final long serialVersionUID = 783591661393553238L;

    /**
     * 定义带有文档更改数据的文件的url地址，可以通过 changesurl 链接从保存文档后返回的 JSON对象 中下载
     */
    @Schema(description = "定义带有文档更改数据的文件的url地址，可以通过 changesurl 链接从保存文档后返回的 JSON对象 中下载")
    private String changesUrl;
    /**
     * 定义错误消息文本
     */
    @Schema(description = "定义错误消息文本")
    private String error;
    /**
     * 定义使用 url 参数指定的文档的扩展名
     */
    @Schema(description = "定义使用 url 参数指定的文档的扩展名")
    private String fileType;
    /**
     * 定义文档标识符，用于明确标识文档文件
     */
    @Schema(description = "定义文档标识符，用于明确标识文档文件")
    private String key;
    /**
     * 如果在保存文档后返回 changesUrl 地址，则定义上一版本文档的对象
     */
    @Schema(description = "如果在保存文档后返回 changesUrl 地址，则定义上一版本文档的对象")
    private Previous previous;
    /**
     * 定义以令牌形式添加到参数的加密签名
     */
    @Schema(description = "定义以令牌形式添加到参数的加密签名")
    private String token;
    /**
     * 定义当前文档版本的 url 地址。可以从保存文档后返回的 JSON 对象中的 url 链接下载。使用本地链接时请务必添加令牌。否则会出现错误
     */
    @Schema(description = "定义当前文档版本的 url 地址。可以从保存文档后返回的 JSON 对象中的 url 链接下载。使用本地链接时请务必添加令牌。否则会出现错误")
    private String url;
    /**
     * 定义文档版本号
     */
    @Schema(description = "定义文档版本号")
    private String version;

}
