package io.github.mangocrisp.spring.taybct.api.system.dto;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 菜单查询参数
 *
 * @author xijieyin <br> 2023/3/15 上午11:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "菜单查询参数")
public class SysMenuQueryDTO extends SysMenu {

    @Serial
    private static final long serialVersionUID = -4720824623321515518L;

    @Schema(description = "编辑的时候回显，需要反查这个树回来", required = true)
    private Long permCheckId;

}
