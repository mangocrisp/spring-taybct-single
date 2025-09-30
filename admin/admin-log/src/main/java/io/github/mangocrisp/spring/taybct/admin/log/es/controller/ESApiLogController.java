package io.github.mangocrisp.spring.taybct.admin.log.es.controller;

import io.github.mangocrisp.spring.taybct.admin.log.es.domain.ESApiLog;
import io.github.mangocrisp.spring.taybct.admin.log.es.service.IESApiLogService;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.es.dto.ESQueryDTO;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xijieyin <br>
 * 2021/12/11 21:57
 */
@Tag(name = "接口日志管理相关接口（es）")
@RestController
@RequestMapping(ServeConstants.CONTEXT_PATH_ADMIN_LOG + "apiLog/es")
@ConditionalOnClass(ElasticsearchTemplate.class)
public class ESApiLogController {

    @Resource
    private IESApiLogService esApiLogService;

    @Autowired
    private ISecurityUtil securityUtil;

    /**
     * es 获取日志分页
     *
     * @param queryDTO 查询请求体
     * @return {@code R<Page<ESApiLog>>}
     * @author xijieyin <br> 2022/8/4 18:48
     * @since 1.0.0
     */
    @Operation(summary = "获取分页")
    @PostMapping("page")
    @WebLog
    public R<Page<ESApiLog>> esPage(@RequestBody ESQueryDTO<ESApiLog> queryDTO) {
        queryDTO.getObject().setTenantId(securityUtil.getLoginUser().getTenantId());
        return R.data(esApiLogService.page(queryDTO));
    }

}
