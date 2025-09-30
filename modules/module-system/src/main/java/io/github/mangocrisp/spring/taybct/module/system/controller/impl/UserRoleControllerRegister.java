package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserRole;
import io.github.mangocrisp.spring.taybct.module.system.controller.IUserRoleController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserRoleService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 系统用户角色关联相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:42
 * @since 1.0.0
 */
public class UserRoleControllerRegister implements IUserRoleController {

    @Autowired(required = false)
    protected ISysUserRoleService sysUserRoleService;

    public ISysUserRoleService getSysUserRoleService() {
        return sysUserRoleService;
    }

    /**
     * 获取列表
     *
     * @param sqlQueryParams {@literal sql 查询参数}
     * @return {@code R<List<SysUserRole>>}
     * @author xijieyin <br> 2022/8/5 21:43
     * @since 1.0.0
     */
    @WebLog
    @Override
    public R<List<SysUserRole>> list(@RequestParam(required = false) Map<String, Object> sqlQueryParams) {
        return R.data(getSysUserRoleService().list(MyBatisUtil.genQueryWrapper(sqlQueryParams, SysUserRole.class)));
    }

    /**
     * 批量保存对象
     *
     * @param domains 请求参数对象
     * @return {@code R<List < SysUserRole>>}
     * @author xijieyin <br> 2022/8/5 21:43
     * @since 1.0.0
     */
    @ApiLog(title = "批量保存对象", description = "批量保存对象，并且在新增成功后一起返回", type = OperateType.INSERT, isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<List<SysUserRole>> saveBatch(@Valid @NotNull @RequestBody List<SysUserRole> domains) {
        return getSysUserRoleService().saveBatch(domains) ? R.data(domains) : R.fail(String.format("批量保存%s失败！", "用户关联角色"));
    }

}
