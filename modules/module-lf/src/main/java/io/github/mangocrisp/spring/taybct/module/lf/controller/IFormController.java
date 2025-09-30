package io.github.mangocrisp.spring.taybct.module.lf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Form;
import io.github.mangocrisp.spring.taybct.module.lf.domain.FormRelease;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.service.IFormService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 表单管理控制器
 *
 * @author XiJieYin <br> 2023/7/21 15:22
 */
@Tag(name = "表单管理控制器")
@RestControllerRegister("{version}/form")
@ApiVersion
public interface IFormController extends BaseController<Form, IFormService> {

    @Operation(summary = "发布表单")
    @PostMapping("release")
    @WebLog
    @ApiLog(title = "发布流程"
            , description = "每次发布的表单都会记录一个版本号", type = OperateType.INSERT)
    R<FormReleasePublishDTO> publish(@Valid @NotNull @RequestBody FormReleasePublishDTO domain);

    @Operation(summary = "查看发布表单列表")
    @PostMapping("release/publishList")
    @WebLog
    R<IPage<? extends FormRelease>> publishList(@RequestBody FormReleaseQueryDTO dto, SqlQueryParams sqlQueryParams);

    @Operation(summary = "发布表单详情")
    @GetMapping("release/{id}")
    @Parameter(name = "id", description = "发布 id", required = true, in = ParameterIn.PATH)
    R<? extends FormRelease> releaseDetail(@PathVariable Long id);

}
