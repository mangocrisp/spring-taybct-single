package io.github.mangocrisp.spring.taybct.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleMenu;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysRoleMenuVO;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单关联表操作的 service
 *
 * @author xijieyin <br> 2022/8/5 22:05
 * @see SysRoleMenu
 * @since 1.0.0
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色菜单关联列表
     *
     * @param dto 请求参数
     * @return {@code List<SysRoleMenuVO>}
     * @author xijieyin <br> 2022/8/5 22:05
     * @since 1.0.0
     */
    List<SysRoleMenuVO> list(SysRoleMenu dto);

    /**
     * 批量保存
     *
     * @param entityList 角色菜单关联
     * @param primaryBy  根据角色或者菜单来关联
     * @return boolean
     */
    boolean saveBatch(Collection<SysRoleMenu> entityList, Integer primaryBy);

}
