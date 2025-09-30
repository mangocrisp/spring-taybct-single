package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 历史版本用户
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
@Schema(description = "历史版本用户")
public class HistoryUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 2146666181158711489L;

    /**
     * 用户 id
     */
    @Schema(description = "用户 id")
    private String id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String name;

}
