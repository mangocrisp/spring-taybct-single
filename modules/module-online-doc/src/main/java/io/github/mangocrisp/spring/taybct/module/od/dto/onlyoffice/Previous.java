package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 如果在保存文档后返回 changesUrl 地址，则定义上一版本文档的对象
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/4/22 09:54
 */
@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Schema(description = "如果在保存文档后返回 changesUrl 地址，则定义上一版本文档的对象")
public class Previous implements Serializable {

    @Serial
    private static final long serialVersionUID = -2202425919386168104L;

    /**
     * 定义使用 previous.url 参数指定的文档的扩展
     */
    @Schema(description = "定义使用 previous.url 参数指定的文档的扩展")
    private String fileType;
    /**
     * 定义文档先前版本的文档标识符
     */
    @Schema(description = "定义文档先前版本的文档标识符")
    private String key;
    /**
     * 定义上一版本文档的 url 地址
     */
    @Schema(description = "定义上一版本文档的 url 地址")
    private String url;

}
