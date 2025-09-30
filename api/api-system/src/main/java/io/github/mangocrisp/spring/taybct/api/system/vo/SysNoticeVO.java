package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysNotice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;


/**
 * @author xijieyin <br> 2022/10/10 16:04
 * @since 1.0.5
 */
@Schema(description = "消息通知扩展")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysNoticeVO extends SysNotice {

    @Serial
    private static final long serialVersionUID = -6054506526978714900L;
    /**
     * 状态(0不可见 1 已读 2待办)
     */
    @NotNull(message = "[状态(0不可见 1 已读 2待办)]不能为空")
    @Schema(description = "状态(0不可见 1 已读 2待办)")
    private Byte status;

}
