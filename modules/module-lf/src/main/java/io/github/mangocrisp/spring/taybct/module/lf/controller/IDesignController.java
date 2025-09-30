package io.github.mangocrisp.spring.taybct.module.lf.controller;

import io.github.mangocrisp.spring.taybct.module.lf.domain.Design;
import io.github.mangocrisp.spring.taybct.module.lf.domain.DesignPermissions;
import io.github.mangocrisp.spring.taybct.module.lf.service.IDesignService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 流程图设计控制器
 *
 * @author XiJieYin <br> 2023/4/18 17:15
 */
@Tag(name = "流程图设计控制器")
@RestControllerRegister("{version}/design")
@ApiVersion
public interface IDesignController extends BaseController<Design, IDesignService> {

    @Operation(tags = "分享流程图操作"
            , summary = "分享流程图操作"
            , description = "其实也就是让谁来可以操作这个流程图设计，有对应的可以操作的权限可以分配")
    @PostMapping("share")
    @WebLog
    @ApiLog(title = "分享流程图操作"
            , description = "把操作流程图的权限分享分配给其他用户或者指定的部门的所有人", type = OperateType.INSERT)
    R<List<DesignPermissions>> shareDesign(@RequestBody List<DesignPermissions> list);

    @Operation(tags = "查看当前流程图的权限分配"
            , summary = "查看当前流程图的权限分配"
            , description = "查看当前流程图的权限分配")
    @GetMapping("permissions/{designId}")
    @WebLog
    @Parameter(name = "designId", description = "设计 id", required = true, in = ParameterIn.PATH)
    R<List<DesignPermissions>> getPermissions(@PathVariable Long designId, SqlQueryParams sqlQueryParams);

}
