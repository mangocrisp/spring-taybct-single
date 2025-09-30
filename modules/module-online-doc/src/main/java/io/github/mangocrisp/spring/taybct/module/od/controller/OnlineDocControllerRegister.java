package io.github.mangocrisp.spring.taybct.module.od.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.module.od.dto.add.OnlineDocAddDTO;
import io.github.mangocrisp.spring.taybct.module.od.dto.query.body.OnlineDocQueryBody;
import io.github.mangocrisp.spring.taybct.module.od.dto.update.OnlineDocUpdateDTO;
import io.github.mangocrisp.spring.taybct.module.od.service.IOnlineDocService;
import io.github.mangocrisp.spring.taybct.module.od.vo.OnlineDocVO;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.LongKeyConvertibleController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.file.util.FileServiceBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * 针对表【t_online_doc(在线文档)】的数据库操作 Controller 控制器
 * </pre>
 *
 * @author SuMuYue
 * @since 2025-03-04 14:59:19
 */
@Tag(name = "在线文档控制器")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_ONLINE_DOC + "{version}/onlineDoc")
@ApiVersion
@RequiredArgsConstructor
@Slf4j
public class OnlineDocControllerRegister implements LongKeyConvertibleController<OnlineDoc
        , IOnlineDocService
        , OnlineDocQueryBody
        , OnlineDocAddDTO
        , OnlineDocUpdateDTO> {

    final IOnlineDocService onlineDocService;

    @Override
    public IOnlineDocService getBaseService() {
        return this.onlineDocService;
    }

    /**
     * 回显图片/下载文件
     *
     * @param response 自动引入
     * @param path     路径
     * @param fileName 文件名
     * @param d        是否下载
     */
    @Operation(summary = "回显图片/下载文件")
    @RequestMapping(value = {"/statics"})
    @Parameters({
            @Parameter(name = "path", description = "文件路径")
            , @Parameter(name = "fileName", description = "文件名")
            , @Parameter(name = "d", description = "是否需要下载")
            , @Parameter(name = "dName", description = "下载指定的文件名")
    })
    public void statics(HttpServletResponse response, @RequestParam String path
            , @RequestParam(required = false) String fileName
            , @RequestParam(required = false, defaultValue = "false") Boolean d
            , @RequestParam(required = false) String dName) {
        InputStream inputStream = null;
        if (fileName == null) {
            int i = path.lastIndexOf("/");
            if (i != -1) {
                fileName = path.substring(i + 1);
            } else {
                fileName = path;
            }
        }
        try {
            if (d) {
                // 是否需要下载，下载是会返回一个流文件，会直接下载文件
                if (dName == null) {
                    dName = fileName;
                }
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" +
                        (URLEncoder.encode(dName, StandardCharsets.UTF_8)) + ";filename*=UTF-8''" +
                        (URLEncoder.encode(dName, StandardCharsets.UTF_8)));
            }
            inputStream = FileServiceBuilder.get(path);
            response.setContentLength(inputStream.available());
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) > 0) {
                response.getOutputStream().write(bytes, 0, len);
            }
            response.getOutputStream().flush();
        } catch (Exception e) {
            throw new BaseException(String.format("文件下载失败！%s", e.getMessage()));
        } finally {
            Optional.ofNullable(inputStream).ifPresent(is -> {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Operation(summary = "onlyoffice 回调接口")
    @WebLog
    @RequestMapping("callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        return getBaseService().callback(request, response);
    }

    @Operation(summary = "新增/导入")
    @WebLog
    @PostMapping
    @ApiLog(title = "新增/导入", description = "【在线文档】新增/导入", type = OperateType.INSERT)
    public R<? extends OnlineDoc> save(@Valid @NotNull String data, MultipartFile file) {
        OnlineDocAddDTO onlineDocAddDTO = JSONObject.parseObject(data, OnlineDocAddDTO.class);
        return getBaseService().save(onlineDocAddDTO, file) ? R.data(onlineDocAddDTO.getConvertedBean()) : R.fail(String.format("新增%s失败！", getResource()));
    }

    @Operation(summary = "单个更新")
    @WebLog
    @ApiLog(title = "单个更新", description = "【在线文档】单个更新", type = OperateType.UPDATE)
    @PutMapping
    @Override
    public R<? extends OnlineDoc> updateAllField(@Valid @NotNull @RequestBody OnlineDocUpdateDTO dto) {
        return getBaseService().update(dto) ? R.data(dto.getConvertedBean()) : R.fail(String.format("更新%s失败！", getResource()));
    }

    @Override
    @Operation(summary = "根据 id 删除记录")
    @WebLog
    @DeleteMapping("/{id}")
    @ApiLog(title = "根据 id 删除记录", description = "根据 id 删除【在线文档】", type = OperateType.DELETE)
    @Parameters({
            @Parameter(name = "id", description = "主键 id", required = true, in = ParameterIn.PATH)
    })
    public R<? extends OnlineDoc> delete(@PathVariable Long id) {
        return getBaseService().remove(id) ? R.ok(String.format("删除%s成功！", getResource())) : R.fail(String.format("删除%s失败！", getResource()));
    }

    @PostMapping("total")
    @WebLog
    @Operation(summary = "获取总数")
    @Override
    public R<Long> total(@RequestBody OnlineDocQueryBody dto) {
        return R.data(getBaseService().total(JSONObject.from(dto)));
    }

    @PostMapping("page")
    @WebLog
    @Operation(summary = "获取分页")
    @Override
    public R<IPage<OnlineDocVO>> page(@RequestBody OnlineDocQueryBody dto, SqlPageParams sqlPageParams) {
        return R.data(getBaseService().page(JSONObject.from(dto), sqlPageParams));
    }

    @Operation(summary = "获取列表")
    @WebLog
    @PostMapping("list")
    @Override
    public R<List<OnlineDocVO>> list(@RequestBody OnlineDocQueryBody dto, SqlPageParams sqlPageParams) {
        return R.data(getBaseService().list(JSONObject.from(dto), sqlPageParams));
    }

    @GetMapping("/{id}")
    @WebLog
    @Operation(summary = "查看详情")
    @Override
    @Parameters({
            @Parameter(name = "id", description = "主键 id", required = true, in = ParameterIn.PATH)
    })
    public R<OnlineDocVO> detail(@PathVariable Long id) {
        return R.data(getBaseService().detail(JSONObject.of("id", id)));
    }

}
