package io.github.mangocrisp.spring.taybct.module.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2ClientDTO;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysOauth2ClientService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.SafeConvert;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.BaseController;
import io.github.mangocrisp.spring.taybct.tool.core.enums.EntityType;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 客户端管理
 *
 * @author xijieyin <br> 2022/8/5 21:28
 * @see SysOauth2Client
 * @see ISysOauth2ClientService
 * @since 1.0.0
 */
@RestControllerRegister(ServeConstants.CONTEXT_PATH_SYSTEM + "{version}/oauth2Client")
@ApiVersion
@Tag(name = "客户端管理")
public interface ISysOauth2ClientController extends BaseController<SysOauth2Client, ISysOauth2ClientService> {

    /**
     * 根据客户端 id 获取客户端信息
     *
     * @param clientId 客户端 id
     * @return {@code R<OAuth2ClientDTO>}
     * @author xijieyin <br> 2022/8/5 21:30
     * @since 1.0.0
     */
    @Operation(summary = "根据客户端 id 获取客户端信息")
    @PostMapping(value = "/clientId/{clientId}")
    default R<OAuth2ClientDTO> getOauth2ClientById(@PathVariable(value = "clientId") String clientId) {
        return Optional.ofNullable(getBaseService().getOne(
                        Wrappers.<SysOauth2Client>lambdaQuery().eq(SysOauth2Client::getClientId, clientId)))
                .map(client -> R.data(BeanUtil.copyProperties(client, OAuth2ClientDTO.class)))
                .orElseThrow(() -> new BaseException(String.format("未查询到客户端[%s]", clientId)));
    }

    /**
     * 查询客户端信息，这里不给直接给查看客户端密钥
     *
     * @param params 所有参数
     * @return {@code R<IPage<SysOauth2Client>>}
     * @author xijieyin <br> 2022/10/19 17:01
     * @since 1.0.5
     */
    @SafeConvert(safeOut = SysOauth2Client.class, resultType = EntityType.Page, ignoreOut = {"clientSecret"})
    @Override
    @WebLog
    default R<IPage<? extends SysOauth2Client>> page(@RequestParam(required = false) Map<String, Object> params) {
        return BaseController.super.page(params);
    }

    @SafeConvert(safeOut = SysOauth2Client.class, resultType = EntityType.Page, ignoreOut = {"clientSecret"})
    @Override
    @WebLog
    default R<List<? extends SysOauth2Client>> list(@RequestParam(required = false) Map<String, Object> params) {
        return BaseController.super.list(params);
    }

    @SafeConvert(safeOut = SysOauth2Client.class, resultType = EntityType.Entity, ignoreOut = {"clientSecret"})
    @Override
    @WebLog
    default R<? extends SysOauth2Client> detail(@NotNull @PathVariable Long id) {
        return BaseController.super.detail(id);
    }

    /**
     * 更新客户端，不能给他修改客户端 id
     *
     * @param domain 修改对象
     * @return {@code R<SysOauth2Client>}
     * @author xijieyin <br> 2022/10/19 16:57
     * @since 1.0.5
     */
    @Override
    @WebLog
    @SafeConvert(key = "domain", ignoreIn = {"clientSecret"})
    default R<? extends SysOauth2Client> updateAllField(@Valid @NotNull @RequestBody SysOauth2Client domain) {
        return BaseController.super.updateAllField(domain);
    }

}
