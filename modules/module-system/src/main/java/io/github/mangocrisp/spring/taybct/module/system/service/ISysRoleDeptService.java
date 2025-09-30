package io.github.mangocrisp.spring.taybct.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleDept;

import java.util.Set;

/**
 * 针对表【sys_role_dept(角色部门关联表，可以知道角色有多少部门，也可以知道部门有多少角色)】的数据库操作Service
 *
 * @author admin
 * 2023-06-14 17:40:21
 */
public interface ISysRoleDeptService extends IService<SysRoleDept> {

    /**
     * 更新角色部门关联
     *
     * @param entityList 关联集合
     * @return 是否关联成功
     */
    boolean updateRoleDept(Set<SysRoleDept> entityList);

}
