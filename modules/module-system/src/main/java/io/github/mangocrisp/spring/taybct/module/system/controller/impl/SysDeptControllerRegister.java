package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleDept;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserDept;
import io.github.mangocrisp.spring.taybct.api.system.vo.DeptUserTreeVO;
import io.github.mangocrisp.spring.taybct.module.system.controller.ISysDeptController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDeptService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRoleDeptService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserDeptService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * 部门控制器
 *
 * @author XiJieYin <br> 2023/6/8 14:07
 */
public class SysDeptControllerRegister implements ISysDeptController {

    @Autowired(required = false)
    protected ISysUserDeptService sysUserDeptService;

    @Autowired(required = false)
    protected ISysRoleDeptService sysRoleDeptService;

    @Autowired(required = false)
    protected ISysDeptService sysDeptService;

    public ISysUserDeptService getSysUserDeptService() {
        return sysUserDeptService;
    }

    public ISysRoleDeptService getSysRoleDeptService() {
        return sysRoleDeptService;
    }

    @Override
    public ISysDeptService getBaseService() {
        return sysDeptService;
    }

    @Operation(summary = "部门和用户一起的树(条件查询)")
    @WebLog
    @PostMapping("deptUserTreeByCondition")
    public R<List<DeptUserTreeVO>> deptUserTreeByCondition(@RequestBody JSONObject dto
            , @RequestParam(required = false, defaultValue = "true") Boolean makeTree
            , @RequestParam(required = false, defaultValue = "true") Boolean includeUser) {
        return R.data(getBaseService().deptUserTreeByCondition(dto, makeTree, includeUser));
    }

    @Operation(summary = "部门和用户一起的树")
    @WebLog
    @PostMapping("deptUserTree")
    public R<List<DeptUserTreeVO>> deptUserTree(@RequestBody Set<Long> deptIdSet
            , @RequestParam(required = false, defaultValue = "true") Boolean makeTree
            , @RequestParam(required = false, defaultValue = "true") Boolean includeUser) {
        return R.data(getBaseService().deptUserTree(deptIdSet, makeTree, includeUser));
    }

    @WebLog
    @Override
    public R<List<SysUserDept>> getUserDept(@RequestBody SysUserDept dto, SqlQueryParams sqlQueryParams) {
        Page<SysUserDept> page = MyBatisUtil.genPage(sqlQueryParams);
        // 这里不查询页数
        page.setSearchCount(false);
        return R.data(getSysUserDeptService().page(page, MyBatisUtil.genQueryWrapper(dto, sqlQueryParams)).getRecords());
    }

    @WebLog
    @Override
    public R<?> updateUserDept(@RequestBody Set<SysUserDept> entityList) {
        return getSysUserDeptService().updateUserDept(entityList) ? R.ok("关联成功") : R.data("关联失败！");
    }

    @WebLog
    @Override
    public R<List<SysRoleDept>> getRoleDept(@RequestBody SysRoleDept dto, SqlQueryParams sqlQueryParams) {
        Page<SysRoleDept> page = MyBatisUtil.genPage(sqlQueryParams);
        // 这里不查询页数
        page.setSearchCount(false);
        return R.data(getSysRoleDeptService().page(page, MyBatisUtil.genQueryWrapper(dto, sqlQueryParams)).getRecords());
    }

    @WebLog
    @Override
    public R<?> updateRoleDept(@RequestBody Set<SysRoleDept> entityList) {
        return getSysRoleDeptService().updateRoleDept(entityList) ? R.ok("关联成功") : R.data("关联失败！");
    }
}
