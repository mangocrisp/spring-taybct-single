package io.github.mangocrisp.spring.taybct.module.system.controller.impl;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRoleMenu;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysRoleMenuVO;
import io.github.mangocrisp.spring.taybct.module.system.controller.IRoleMenuController;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRoleMenuService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色菜单相关接口
 *
 * @author xijieyin <br> 2022/8/5 21:20
 * @see SysRoleMenu
 * @since 1.0.0
 */
public class RoleMenuControllerRegister implements IRoleMenuController {

    @Autowired(required = false)
    protected ISysRoleMenuService sysRoleMenuService;

    public ISysRoleMenuService getSysRoleMenuService() {
        return sysRoleMenuService;
    }

    /**
     * 获取列表
     *
     * @param dto 请求参数
     * @return {@code R<List < SysRoleMenu>>}
     * @author xijieyin <br> 2022/8/5 21:21
     * @since 1.0.0
     */
    @Override
    public R<List<SysRoleMenu>> list(SysRoleMenu dto) {
        return R.data(getSysRoleMenuService().list(MyBatisUtil.genQueryWrapper(dto, null)));
    }

    /**
     * 获取列表（带角色菜单详细信息）
     *
     * @param dto 请求参数
     * @return {@code R<List < SysRoleMenuVO>>}
     * @author xijieyin <br> 2022/8/5 21:21
     * @since 1.0.0
     */
    @Override
    public R<List<SysRoleMenuVO>> listVO(@NotNull SysRoleMenu dto) {
        return R.data(getSysRoleMenuService().list(dto));
    }

    /**
     * 批量保存对象
     *
     * @param domains   请求参数
     * @param primaryBy 根据哪个对象来操作，1是根据角色，其他是根据菜单
     * @return {@code R<List < SysRoleMenu>>}
     * @author xijieyin <br> 2022/8/5 21:21
     * @since 1.0.0
     */
    @ApiLog(title = "批量保存对象", description = "批量保存对象，并且在新增成功后一起返回", type = OperateType.INSERT
            , isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<List<SysRoleMenu>> saveBatch(@Valid @NotNull @RequestBody List<SysRoleMenu> domains, @RequestParam Integer primaryBy) {
        return getSysRoleMenuService().saveBatch(domains, primaryBy) ? R.data(domains)
                : R.fail(String.format("批量保存%s失败！", "角色关联菜单"));
    }

}




