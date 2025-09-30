package io.github.mangocrisp.spring.taybct.admin.file.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.admin.file.dto.add.SysFileAddDTO;
import io.github.mangocrisp.spring.taybct.admin.file.dto.query.body.SysFileQueryBody;
import io.github.mangocrisp.spring.taybct.admin.file.dto.update.SysFileUpdateDTO;
import io.github.mangocrisp.spring.taybct.admin.file.poi.exp.SysFileExpVO;
import io.github.mangocrisp.spring.taybct.admin.file.poi.imp.SysFileImpDTO;
import io.github.mangocrisp.spring.taybct.admin.file.service.ISysFileService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.LongKeyConvertibleController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easyexcel.listener.ModelConvertibleListener;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easyexcel.util.EasyExcelUtil;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * 针对表【sys_file(文件管理)】的数据库操作 Controller 控制器
 * </pre>
 *
 * @author 24154
 * @since 2024-09-01 21:20:40
 */
@Tag(name = "文件管理控制器")
@RestController
@RequestMapping("/sysFile")
@RequiredArgsConstructor
public class SysFileController implements LongKeyConvertibleController<SysFile
        , ISysFileService
        , SysFileQueryBody
        , SysFileAddDTO
        , SysFileUpdateDTO> {

    @Operation(summary = "单个新增")
    @WebLog
    @PostMapping
    @ApiLog(title = "单个新增", description = "【文件管理】单个新增", type = OperateType.INSERT)
    @Override
    public R<? extends SysFile> add(@Valid @NotNull @RequestBody SysFileAddDTO domain) {
        return LongKeyConvertibleController.super.add(domain);
    }

    @Operation(summary = "批量新增")
    @WebLog
    @PostMapping("batch")
    @ApiLog(title = "批量新增", description = "【文件管理】批量新增", type = OperateType.INSERT, isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<? extends Collection<? extends SysFile>> saveBatch(@Valid @NotNull @RequestBody Collection<SysFileAddDTO> domains) {
        return LongKeyConvertibleController.super.saveBatch(domains);
    }

    @Operation(summary = "单个更新")
    @WebLog
    @ApiLog(title = "单个更新", description = "【文件管理】单个更新", type = OperateType.UPDATE)
    @PutMapping
    @Override
    public R<? extends SysFile> updateAllField(@Valid @NotNull @RequestBody SysFileUpdateDTO domain) {
        return LongKeyConvertibleController.super.updateAllField(domain);
    }

    @Operation(summary = "批量更新")
    @WebLog
    @PutMapping("batch")
    @ApiLog(title = "批量更新", description = "批量更新【文件管理】", type = OperateType.UPDATE, isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<? extends Collection<? extends SysFile>> updateAllFieldBatch(@Valid @NotNull @RequestBody Collection<SysFileUpdateDTO> domains) {
        return LongKeyConvertibleController.super.updateAllFieldBatch(domains);
    }

    @Operation(summary = "根据 条件 批量更新数据")
    @WebLog
    @PutMapping("condition")
    @ApiLog(title = "根据 条件 批量更新数据", description = "根据 条件 批量更新【文件管理】", type = OperateType.UPDATE)
    @Override
    public R<?> update(@Valid @NotNull @RequestBody UpdateModel<SysFile, SysFileUpdateDTO, SysFileQueryBody> model) {
        return getBaseService().update(model) ? R.ok() : R.fail();
    }

    @Override
    @Operation(summary = "根据 id 删除记录")
    @WebLog
    @DeleteMapping("/{id}")
    @ApiLog(title = "根据 id 删除记录", description = "根据 id 删除【文件管理】", type = OperateType.DELETE)
    @Parameters({
            @Parameter(name = "id", description = "主键 id", required = true, in = ParameterIn.PATH)
    })
    public R<? extends SysFile> delete(@PathVariable Long id) {
        return LongKeyConvertibleController.super.delete(id);
    }

    @Override
    @Operation(summary = "根据 id 批量删除记录")
    @WebLog
    @DeleteMapping("batch")
    @ApiLog(title = "根据 id 批量删除记录", description = "根据 id 批量删除【文件管理】", type = OperateType.DELETE)
    public R<? extends SysFile> deleteBatch(@RequestBody Set<Long> ids) {
        return LongKeyConvertibleController.super.deleteBatch(ids);
    }

    @PostMapping("page")
    @WebLog
    @Operation(summary = "获取分页")
    @Override
    public R<IPage<SysFile>> page(@RequestBody SysFileQueryBody dto, SqlPageParams sqlPageParams) {
        return R.data(getBaseService().page(JSONObject.from(dto), sqlPageParams));
    }

    @Operation(summary = "获取列表")
    @WebLog
    @PostMapping("list")
    @Override
    public R<List<SysFile>> list(@RequestBody SysFileQueryBody dto, SqlPageParams sqlPageParams) {
        return R.data(getBaseService().list(JSONObject.from(dto), sqlPageParams));
    }

    @GetMapping("/{id}")
    @WebLog
    @Operation(summary = "查看详情")
    @Override
    public R<SysFile> detail(@PathVariable Long id) {
        return R.data(getBaseService().detail(JSONObject.of("id", id)));
    }

    @Operation(summary = "excel 导入数据模板")
    @WebLog
    @GetMapping("template")
    public void downloadTemplate(HttpServletResponse response) {
        EasyExcelUtil.export("【文件管理】导入模板", response, SysFileImpDTO.class, Collections::emptyList);
    }

    @Operation(summary = "excel 导入数据")
    @WebLog
    @PostMapping("imp")
    @ApiLog(title = "excel 导入数据", description = "excel 导入【文件管理】", type = OperateType.IMPORT, isSaveRequestData = false, isSaveResultData = false)
    public R<?> imp(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream()
                , SysFileImpDTO.class
                , new ModelConvertibleListener<SysFile>(list -> getBaseService().saveBatch(list))).sheet().doRead();
        return R.ok();
    }

    @Operation(summary = "excel 导出数据")
    @WebLog
    @PostMapping("exp")
    @ApiLog(title = "excel 导出数据", description = "excel 导出【文件管理】", type = OperateType.EXPORT, isSaveRequestData = false, isSaveResultData = false)
    public void exp(@RequestBody SysFileQueryBody dto, SqlPageParams sqlPageParams, HttpServletResponse response) {
        EasyExcelUtil.export("【文件管理】导出数据", response, SysFileExpVO.class
                , () -> BeanUtil.copyToList(getBaseService().list(JSONObject.from(dto), sqlPageParams), SysFileExpVO.class));
    }

}
