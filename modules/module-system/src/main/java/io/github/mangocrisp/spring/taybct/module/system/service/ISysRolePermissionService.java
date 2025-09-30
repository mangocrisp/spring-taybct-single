package io.github.mangocrisp.spring.taybct.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRolePermission;

import java.util.Collection;

/**
 * 角色权限关联
 *
 * @author xijieyin <br> 2022/8/5 22:08
 * @see SysRolePermission
 * @since 1.0.0
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {


    /**
     * 批量保存
     *
     * @param entityList 批量保存的值
     * @param primaryBy  以什么为主 1 角色，2 权限
     * @return boolean
     */
    boolean saveBatch(Collection<SysRolePermission> entityList, Integer primaryBy);


    /**
     * 清理脏数据
     */
    void clearDirtyData();

    /**
     * 初始化角色权限
     */
    void iniConfig();
}
