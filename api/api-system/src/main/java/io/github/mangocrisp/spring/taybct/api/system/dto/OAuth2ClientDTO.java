package io.github.mangocrisp.spring.taybct.api.system.dto;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 客户端数据传输对象
 *
 * @author xijieyin <br> 2022/8/5 10:07
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "客户端数据传输对象")
public class OAuth2ClientDTO extends SysOauth2Client {
    @Serial
    private static final long serialVersionUID = -6967485702774323589L;
}
