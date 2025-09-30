package io.github.mangocrisp.spring.taybct.module.lf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import io.github.mangocrisp.spring.taybct.module.lf.dto.NodesSubmitDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ProcessNewDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.TodoListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.UserRequestListQueryDTO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.ProcessListVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.TodoListCountVO;
import io.github.mangocrisp.spring.taybct.module.lf.vo.UnOperator;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
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
import java.util.Set;

/**
 * 流程控制器
 *
 * @author XiJieYin <br> 2023/7/7 15:56
 */
@Tag(name = "流程控制器")
@RestControllerRegister("{version}/process")
@ApiVersion
public interface IProcessController {

    @Operation(summary = "新建一个流程")
    @PostMapping("new")
    R<?> newProcess(@RequestBody @Valid @NotNull ProcessNewDTO process);


    @Operation(summary = "待办/已办统计")
    @GetMapping("todoListCount/{status}")
    @Parameter(name = "status", description = "状态", required = true, in = ParameterIn.PATH)
    R<TodoListCountVO> todoListCount(@PathVariable Byte status);

    @Operation(summary = "我的待办/已办")
    @PostMapping("todoList")
    R<IPage<ProcessListVO>> todoList(@RequestBody TodoListQueryDTO dto, SqlQueryParams sqlQueryParams);

    @Operation(summary = "查询未操作者(流程的未操作者)")
    @PostMapping("unOperators")
    R<List<UnOperator>> queryUnOperators(@RequestBody Set<Long> processIdSet);

    @Operation(summary = "我的请求")
    @PostMapping("userRequestList")
    R<IPage<ProcessListVO>> userRequestList(@RequestBody UserRequestListQueryDTO dto, SqlQueryParams sqlQueryParams);

    @Operation(summary = "用户提交待办")
    @PostMapping("userSubmit")
    R<?> userSubmit(@RequestBody NodesSubmitDTO nodes);

    @Operation(summary = "流程详情")
    @GetMapping("{id}")
    @Parameter(name = "id", description = "流程 id", required = true, in = ParameterIn.PATH)
    R<Process> detail(@PathVariable Long id);

}
