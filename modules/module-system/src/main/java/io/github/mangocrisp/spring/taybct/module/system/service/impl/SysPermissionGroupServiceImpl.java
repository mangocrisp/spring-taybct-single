package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermission;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysPermissionGroup;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysPermissionGroupMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysPermissionMapper;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysPermissionGroupService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author xijieyin <br> 2022/9/23 9:34
 * @since 1.0.4
 */
public class SysPermissionGroupServiceImpl extends BaseServiceImpl<SysPermissionGroupMapper, SysPermissionGroup>
        implements ISysPermissionGroupService {

    @Autowired(required = false)
    protected SysPermissionMapper sysPermissionMapper;

    @Override
    public boolean removeByIds(Collection<?> list) {
        boolean b = super.removeByIds(list);
        if (b) {
            sysPermissionMapper.update(new SysPermission(), Wrappers.<SysPermission>lambdaUpdate().set(SysPermission::getGroupId, null)
                    .in(SysPermission::getGroupId, list));
        }
        return b;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean b = super.removeById(id);
        if (b) {
            sysPermissionMapper.update(new SysPermission(), Wrappers.<SysPermission>lambdaUpdate().set(SysPermission::getGroupId, null)
                    .eq(SysPermission::getGroupId, id));
        }
        return b;
    }
}
