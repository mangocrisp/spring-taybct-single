package io.github.mangocrisp.spring.taybct.api.system.vo;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Set;

/**
 * 角色菜单关联扩展
 *
 * @author xijieyin <br> 2022/8/5 10:18
 * @since 1.0.0
 */
@Schema(description = "角色菜单关联扩展")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenuVO extends SysRole {

    @Serial
    private static final long serialVersionUID = 7262633992431236848L;

    Set<SysMenuVO> menus;

}
