package io.github.mangocrisp.spring.taybct.module.lf.controller;

import io.github.mangocrisp.spring.taybct.module.lf.dto.HistoryListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.HistoryListVO;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 历史记录控制器
 *
 * @author XiJieYin <br> 2023/7/19 11:30
 */
@Tag(name = "历史记录控制器")
@RestControllerRegister("{version}/history")
@ApiVersion
public interface ILfHistoryController {

    @Operation(summary = "流程历史记录列表")
    @PostMapping("list")
    R<List<HistoryListVO>> historyList(@RequestBody HistoryListQueryDTO dto);

}
