package io.github.mangocrisp.spring.taybct.module.lf.controller.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.controller.IReleaseController;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Release;
import io.github.mangocrisp.spring.taybct.module.lf.domain.ReleasePermissions;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.service.IReleasePermissionsService;
import io.github.mangocrisp.spring.taybct.module.lf.service.IReleaseService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author XiJieYin <br> 2023/7/25 16:36
 */
public class ReleaseControllerRegister implements IReleaseController {
    @Autowired(required = false)
    protected IReleaseService releaseService;
    @Autowired(required = false)
    protected IReleasePermissionsService releasePermissionsService;

    public IReleaseService getReleaseService() {
        return releaseService;
    }

    public IReleasePermissionsService getReleasePermissionsService() {
        return releasePermissionsService;
    }

    @WebLog
    @ApiLog(title = "发布流程"
            , description = "每次发布的流程都会记录一个版本号", type = OperateType.INSERT)
    @Override
    public R<ReleasePublishDTO> publish(@Valid @NotNull @RequestBody ReleasePublishDTO domain) {
        return getReleaseService().publish(domain) ? R.data(domain) : R.fail("发布失败！");
    }

    @WebLog
    @Override
    public R<IPage<? extends Release>> publishList(@RequestBody ReleaseQueryDTO dto, SqlQueryParams sqlQueryParams) {
        return R.data(getReleaseService().publishList(dto, sqlQueryParams));
    }

    @Override
    public R<? extends Release> detail(@PathVariable Long id) {
        return R.data(getReleaseService().detail(id));
    }

    @WebLog
    @ApiLog(title = "分享流程图操作"
            , description = "把操作流程图的权限分享分配给其他用户或者指定的部门的所有人", type = OperateType.INSERT)
    @Override
    public R<List<ReleasePermissions>> permissions(@RequestBody List<ReleasePermissions> list) {
        return getReleasePermissionsService().permissions(list) ? R.ok() : R.fail("操作失败！");
    }

    @WebLog
    @Override
    public R<List<ReleasePermissions>> getPermissions(@PathVariable Long releaseId, SqlQueryParams sqlQueryParams) {
        return R.data(getReleasePermissionsService().getPermissions(releaseId, null, sqlQueryParams));
    }

}
