package io.github.mangocrisp.spring.taybct.auth.security.handle.deft;

import cn.hutool.core.bean.BeanUtil;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.prop.UserConfig;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author xijieyin
 */
@NoArgsConstructor
@AllArgsConstructor
public class PropertiesUserDetailsHandle implements IUserDetailsHandle {

    private UserConfig userConfig;

    @Override
    public OAuth2UserDTO getUserByUsername(String username) {
        if (userConfig.getUsername() != null && userConfig.getUsername().equalsIgnoreCase(username)) {
            return BeanUtil.copyProperties(userConfig, OAuth2UserDTO.class);
        } else {
            for (OAuth2UserDTO user : userConfig.getUsers()) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    return BeanUtil.copyProperties(user, OAuth2UserDTO.class);
                }
            }
        }
        return null;
    }
}
