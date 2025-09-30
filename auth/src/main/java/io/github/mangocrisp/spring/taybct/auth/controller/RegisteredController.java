package io.github.mangocrisp.spring.taybct.auth.controller;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.auth.service.IRegisteredService;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xijieyin <br> 2023/1/3 17:27
 */
@AutoConfiguration
@RestController
@RequiredArgsConstructor
@Tag(name = "客户端注册控制器")
@RequestMapping(ServeConstants.CONTEXT_PATH_AUTH)
public class RegisteredController {

    final IRegisteredService registeredService;

    @Operation(summary = "客户端保存/更新")
    @PostMapping("registeredClient")
    public R<?> save(@RequestBody @NotNull SysOauth2Client sysOauth2Client) {
        return R.data(registeredService.save(sysOauth2Client));
    }

}
