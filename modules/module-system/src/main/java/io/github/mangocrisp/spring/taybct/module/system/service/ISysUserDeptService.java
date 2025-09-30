package io.github.mangocrisp.spring.taybct.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserDept;

import java.util.Set;

/**
 * 针对表【sys_user_dept(用户部门关联表，可以知道用户有多少部门，也可以知道部门有多少用户)】的数据库操作Service
 *
 * @author admin
 * 2023-06-14 17:40:21
 */
public interface ISysUserDeptService extends IService<SysUserDept> {

    /**
     * 更新用户部门关联
     *
     * @param entityList 关联集合
     * @return 是否关联成功
     */
    boolean updateUserDept(Set<SysUserDept> entityList);

}
