package io.github.mangocrisp.spring.taybct.module.lf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Release;
import io.github.mangocrisp.spring.taybct.module.lf.domain.ReleasePermissions;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
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

import java.util.List;

/**
 * 版本发布控制器
 *
 * @author XiJieYin <br> 2023/7/3 14:52
 */
@Tag(name = "版本发布控制器")
@RestControllerRegister("{version}/release")
@ApiVersion
public interface IReleaseController {

    @Operation(summary = "发布流程")
    @PostMapping
    @WebLog
    @ApiLog(title = "发布流程"
            , description = "每次发布的流程都会记录一个版本号", type = OperateType.INSERT)
    R<ReleasePublishDTO> publish(@Valid @NotNull @RequestBody ReleasePublishDTO domain);

    @Operation(summary = "查看发布流程列表")
    @PostMapping("publishList")
    @WebLog
    R<IPage<? extends Release>> publishList(@RequestBody ReleaseQueryDTO dto, SqlQueryParams sqlQueryParams);

    @Operation(summary = "发布详情")
    @GetMapping("{id}")
    @Parameter(name = "id", description = "发布 id", required = true, in = ParameterIn.PATH)
    R<? extends Release> detail(@PathVariable Long id);

    @Operation(tags = "分配发布查看权限"
            , summary = "分配发布查看权限"
            , description = "设置哪些人可以查看这个发布的流程，默认在发布的时候会把拥有流程设计权限的人一起分配过去，但是如果想要额外让一些人可以查看这个发布，可以单独设置")
    @PostMapping("permissions")
    @WebLog
    @ApiLog(title = "分享流程图操作"
            , description = "把操作流程图的权限分享分配给其他用户或者指定的部门的所有人", type = OperateType.INSERT)
    R<List<ReleasePermissions>> permissions(@RequestBody List<ReleasePermissions> list);

    @Operation(summary = "查看当前发布流程图的权限分配")
    @GetMapping("permissions/{releaseId}")
    @WebLog
    @Parameter(name = "releaseId", description = "发布 id", required = true, in = ParameterIn.PATH)
    R<List<ReleasePermissions>> getPermissions(@PathVariable Long releaseId, SqlQueryParams sqlQueryParams);

}
