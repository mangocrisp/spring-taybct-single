package io.github.mangocrisp.spring.taybct.module.lf.controller.impl;

import io.github.mangocrisp.spring.taybct.module.lf.controller.IDesignController;
import io.github.mangocrisp.spring.taybct.module.lf.domain.DesignPermissions;
import io.github.mangocrisp.spring.taybct.module.lf.service.IDesignPermissionsService;
import io.github.mangocrisp.spring.taybct.module.lf.service.IDesignService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author XiJieYin <br> 2023/7/25 16:34
 */
public class DesignControllerRegister implements IDesignController {

    @Autowired(required = false)
    protected IDesignPermissionsService designPermissionsService;
    @Autowired(required = false)
    protected IDesignService designService;

    public IDesignPermissionsService getDesignPermissionsService() {
        return designPermissionsService;
    }

    @Override
    public IDesignService getBaseService() {
        return designService;
    }

    @WebLog
    @ApiLog(title = "分享流程图操作"
            , description = "把操作流程图的权限分享分配给其他用户或者指定的部门的所有人", type = OperateType.INSERT)
    @Override
    public R<List<DesignPermissions>> shareDesign(@RequestBody List<DesignPermissions> list) {
        return getDesignPermissionsService().shareDesign(list) ? R.ok() : R.fail("操作失败！");
    }

    @WebLog
    @Override
    public R<List<DesignPermissions>> getPermissions(@PathVariable Long designId, SqlQueryParams sqlQueryParams) {
        return R.data(getDesignPermissionsService().getPermissions(designId, null, sqlQueryParams));
    }

}
