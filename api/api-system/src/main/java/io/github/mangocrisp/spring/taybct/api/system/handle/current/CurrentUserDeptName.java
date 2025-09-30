package io.github.mangocrisp.spring.taybct.api.system.handle.current;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserDept;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysDeptMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserDeptMapper;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.config.TableFieldDefaultHandler;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * <pre>
 * 当前登录用户的部门名
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 00:27
 */
@RequiredArgsConstructor
public class CurrentUserDeptName implements TableFieldDefaultHandler<String> {

    final SysUserDeptMapper sysUserDeptMapper;

    final SysDeptMapper sysDeptMapper;

    final ISecurityUtil securityUtil;

    @Override
    public String get(Object entity) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        SysUserDept sysUserDept = sysUserDeptMapper.selectOne(Wrappers.<SysUserDept>lambdaQuery()
                .eq(SysUserDept::getUserId, loginUser.getUserId())
                // id 是雪花id，理论上是按照创建时间排序的，但是可能存在多个部门，所以这里取第一个，如果这样的方式不满足业务需求，请自行将实体类的字段填充不为空就行了
                .orderByAsc(SysUserDept::getId)
                .last("limit 1"));
        if (Objects.isNull(sysUserDept)) {
            return null;
        }
        SysDept sysDept = sysDeptMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                .select(SysDept::getName, SysDept::getId)
                .eq(SysDept::getId, sysUserDept.getDeptId()));
        if (Objects.isNull(sysDept)) {
            return null;
        }
        return sysDept.getName();
    }

}
