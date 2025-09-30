package io.github.mangocrisp.spring.taybct.module.lf.controller.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.controller.IFormController;
import io.github.mangocrisp.spring.taybct.module.lf.domain.FormRelease;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.service.IFormReleaseService;
import io.github.mangocrisp.spring.taybct.module.lf.service.IFormService;
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

/**
 * @author XiJieYin <br> 2023/7/25 16:35
 */
public class FormControllerRegister implements IFormController {
    @Autowired(required = false)
    protected IFormReleaseService formReleaseService;
    @Autowired(required = false)
    protected IFormService formService;

    public IFormReleaseService getFormReleaseService() {
        return formReleaseService;
    }

    @Override
    public IFormService getBaseService() {
        return formService;
    }

    @WebLog
    @ApiLog(title = "发布流程"
            , description = "每次发布的表单都会记录一个版本号", type = OperateType.INSERT)
    @Override
    public R<FormReleasePublishDTO> publish(@Valid @NotNull @RequestBody FormReleasePublishDTO domain) {
        return getFormReleaseService().publish(domain) ? R.data(domain) : R.fail("发布失败！");
    }

    @WebLog
    @Override
    public R<IPage<? extends FormRelease>> publishList(@RequestBody FormReleaseQueryDTO dto, SqlQueryParams sqlQueryParams) {
        return R.data(getFormReleaseService().publishList(dto, sqlQueryParams));
    }

    @Override
    public R<? extends FormRelease> releaseDetail(@PathVariable Long id) {
        return R.data(getFormReleaseService().customizeGetById(id));
    }

}
