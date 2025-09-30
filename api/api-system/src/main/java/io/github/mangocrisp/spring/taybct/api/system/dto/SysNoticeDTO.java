package io.github.mangocrisp.spring.taybct.api.system.dto;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysNotice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * 消息通知数据传输
 *
 * @author xijieyin <br> 2022/10/11 14:28
 * @since 1.0.5
 */
@Schema(description = "消息通知数据传输")
@Data
public class SysNoticeDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7597468287339560420L;

    /**
     * 通知消息主体
     */
    @NotNull(message = "[通知消息主体]不能为空")
    @Schema(description = "通知消息主体")
    private SysNotice sysNotice;
    /**
     * 消息通知附加的通知对象关系，如果不指定就是通知公告，所有人可见
     */
    @Schema(description = "消息通知附加的通知对象关系，如果不指定就是通知公告，所有人可见")
    private Collection<SysNoticeUserDTO> noticeUsers;

}
