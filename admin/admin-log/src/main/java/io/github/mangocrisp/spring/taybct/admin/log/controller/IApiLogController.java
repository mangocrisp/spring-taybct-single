package io.github.mangocrisp.spring.taybct.admin.log.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;
import io.github.mangocrisp.spring.taybct.admin.log.dto.ApiLogQueryDTO;
import io.github.mangocrisp.spring.taybct.admin.log.service.IApiLogService;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.QueryBaseController;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 接口日志管理控制器（基于数据库），主要的增删改用户这个类的接口，如果是要查询数据，推荐是使用集成了 es 的接口
 *
 * @author xijieyin <br> 2022/8/4 18:23
 * @since 1.0.0
 */
@Tag(name = "接口日志管理相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_ADMIN_LOG + "{version}/apiLog")
@ApiVersion
public interface IApiLogController extends QueryBaseController<ApiLog, IApiLogService, ApiLogQueryDTO> {

    /**
     * 这里重写父方法是因为父方法默认新增操作是需要记录日志的，但是日志记录是需要记录是谁在操作的，也就是要知道当前登录的是谁，
     * 但是，如果是要记录登录日志的话，这个时候是还没有登录的，所以请求必定会报错，所以才有了这个重写，这里就不要再写日志了，
     * 因为自己就是日志控制器，自己不需要写自己的日志，使用这个接口的时候也是要注意，如果是登录日志请把 tenant id 设置到实体类
     *
     * @param domain 新增的对象
     * @return {@code R<ApiLog>}
     * @author xijieyin <br> 2022/8/4 18:25
     * @since 1.0.0
     */
    @Operation(summary = "新增对象")
    @Override
    default R<? extends ApiLog> add(@Valid @NotNull @RequestBody ApiLog domain) {
        return getBaseService().save(domain) ? R.data(domain) : R.fail(String.format("新增%s失败！", getResource()));
    }

    /**
     * 清空日志
     *
     * @return R
     * @author xijieyin <br> 2022/8/4 18:45
     * @since 1.0.0
     */
    @Operation(summary = "清空日志")
    @DeleteMapping("all")
    default R<?> cleanAll() {
        return getBaseService().remove(Wrappers.lambdaQuery()) ? R.ok() : R.fail();
    }

}
