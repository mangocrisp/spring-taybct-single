package io.github.mangocrisp.spring.taybct.module.lf.controller;

import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 流程节点控制器
 *
 * @author XiJieYin <br> 2023/7/19 11:30
 */
@Tag(name = "流程节点控制器")
@RestControllerRegister("{version}/nodes")
@ApiVersion
public interface INodesController {

    @Operation(summary = "流程节点详情")
    @GetMapping("{id}")
    @Parameter(name = "id", description = "节点 id", required = true, in = ParameterIn.PATH)
    @WebLog
    R<Nodes> detail(@PathVariable String id);

}
