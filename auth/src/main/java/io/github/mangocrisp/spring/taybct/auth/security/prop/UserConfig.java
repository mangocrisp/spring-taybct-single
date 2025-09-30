package io.github.mangocrisp.spring.taybct.auth.security.prop;

import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.tool.core.constant.PropertiesPrefixConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息配置，用于直接把用户信息存储在配置文件里面的情况
 *
 * @author xijieyin <br> 2022/8/5 12:27
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AutoConfiguration
@ConfigurationProperties(prefix = PropertiesPrefixConstants.AUTH + ".user")
public class UserConfig extends OAuth2UserDTO {

    @Serial
    private static final long serialVersionUID = 7412558805216143878L;
    List<OAuth2UserDTO> users = new ArrayList<>();

}
