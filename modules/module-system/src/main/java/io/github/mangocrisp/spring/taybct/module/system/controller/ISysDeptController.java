package io.github.mangocrisp.spring.taybct.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleDept;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserDept;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysDeptQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysDeptTreeVO;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDeptService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.QueryBaseController;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.CollectionSortUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.tree.TreeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 部门控制器
 *
 * @author XiJieYin <br> 2023/6/8 14:07
 */
@Tag(name = "部门相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/dept")
@ApiVersion
public interface ISysDeptController extends QueryBaseController<SysDept, ISysDeptService, SysDeptQueryDTO> {

    @Operation(summary = "获取列表")
    @GetMapping("list")
    @WebLog
    @Override
    default R<List<? extends SysDept>> list(@RequestParam(required = false) Map<String, Object> params) {
        if (!params.containsKey("pageSize")) {
            // 如果没有指定分页大小，这里默认给1000
            params.put("pageSize", 1000L);
        }
        return QueryBaseController.super.list(params);
    }

    /**
     * 获取树信息
     *
     * @return 树信息
     * @author xijieyin
     */
    @Operation(summary = "获取树")
    @PostMapping("tree")
    @WebLog
    default R<List<SysDeptTreeVO>> tree(@RequestBody SysDeptQueryDTO dto) {
        return R.data(TreeUtil.tree(getBaseService().tree(dto), CollectionSortUtil.comparingAny(SysDept::getSort), dto.getParentId()));
    }

    @Operation(summary = "获取分页")
    @PostMapping("page")
    @WebLog
    default R<IPage<? extends SysDept>> page(@RequestBody SysDeptQueryDTO dto, SqlQueryParams sqlQueryParams) {
        return R.data(getBaseService().page(dto, MyBatisUtil.genPage(sqlQueryParams), sqlQueryParams));
    }

    @Operation(summary = "获取列表")
    @PostMapping("list")
    @WebLog
    @Override
    default R<List<? extends SysDept>> list(@RequestBody SysDeptQueryDTO dto, SqlQueryParams sqlQueryParams) {
        Page<SysDept> page = MyBatisUtil.genPage(sqlQueryParams);
        // 不查询 count
        page.setSearchCount(false);
        return R.data(getBaseService().page(dto, page, sqlQueryParams).getRecords());
    }

    @Operation(summary = "获取用户与部门关联的列表")
    @PostMapping("user")
    @WebLog
    R<List<SysUserDept>> getUserDept(@RequestBody SysUserDept dto, SqlQueryParams sqlQueryParams);

    @Operation(summary = "更新用户与部门关联的列表")
    @PutMapping("user")
    @WebLog
    R<?> updateUserDept(@RequestBody Set<SysUserDept> entityList);

    @Operation(summary = "获取角色与部门关联的列表")
    @PostMapping("role")
    @WebLog
    R<List<SysRoleDept>> getRoleDept(@RequestBody SysRoleDept dto, SqlQueryParams sqlQueryParams);

    @Operation(summary = "更新角色与部门关联的列表")
    @PutMapping("role")
    @WebLog
    R<?> updateRoleDept(@RequestBody Set<SysRoleDept> entityList);

}
