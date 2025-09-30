package io.github.mangocrisp.spring.taybct.module.od.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDocPermit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.util.Set;

/**
 * <pre>
 * 在线文档返回结果 vo
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 03:15
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "在线文档返回结果 vo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnlineDocVO extends OnlineDoc {
    @Serial
    private static final long serialVersionUID = -30862213294989411L;

    /**
     * 是否是管理员
     */
    @TableField(value = "admin")
    private Byte isAdmin;

    /**
     * 文档共享范围
     */
    @Schema(description = "文档共享范围")
    private Set<OnlineDocPermit> onlineDocPermitSet;

}
