package io.github.mangocrisp.spring.taybct.single.modules.system;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.domain.VueTemplate;
import io.github.mangocrisp.spring.taybct.module.system.dto.add.VueTemplateAddDTO;
import io.github.mangocrisp.spring.taybct.module.system.dto.query.body.VueTemplateQueryBody;
import io.github.mangocrisp.spring.taybct.module.system.dto.update.VueTemplateUpdateDTO;
import io.github.mangocrisp.spring.taybct.module.system.poi.exp.VueTemplateExpVO;
import io.github.mangocrisp.spring.taybct.module.system.poi.imp.VueTemplateImpDTO;
import io.github.mangocrisp.spring.taybct.module.system.service.IVueTemplateService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.LongKeyConvertibleController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easyexcel.listener.ModelConvertibleListener;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easyexcel.util.EasyExcelUtil;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.service.IExcelService;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.support.ExportTemplate;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.util.DBField;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.util.DBFieldUtil;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
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
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * 针对表【t_vue_template(前端通用模板)】的数据库操作 Controller 控制器
 * </pre>
 *
 * @author SuMuYue
 * @since 2025-08-15 11:12:11
 */
@AutoConfiguration
@Tag(name = "前端通用模板控制器")
@RestController
@RequestMapping(ServeConstants.CONTEXT_PATH_SYSTEM + "v1/vueTemplate")
@RequiredArgsConstructor
public class VueTemplateController implements LongKeyConvertibleController<VueTemplate
        , IVueTemplateService
        , VueTemplateQueryBody
        , VueTemplateAddDTO
        , VueTemplateUpdateDTO> {

    final IExcelService excelService;

    @Operation(summary = "单个新增")
    @WebLog
    @PostMapping
    @ApiLog(title = "单个新增", description = "【前端通用模板】单个新增", type = OperateType.INSERT)
    @Override
    public R<? extends VueTemplate> add(@Valid @NotNull @RequestBody VueTemplateAddDTO domain) {
        return LongKeyConvertibleController.super.add(domain);
    }

    @Operation(summary = "批量新增")
    @WebLog
    @PostMapping("batch")
    @ApiLog(title = "批量新增", description = "【前端通用模板】批量新增", type = OperateType.INSERT, isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<? extends Collection<? extends VueTemplate>> saveBatch(@Valid @NotNull @RequestBody Collection<VueTemplateAddDTO> domains) {
        return LongKeyConvertibleController.super.saveBatch(domains);
    }

    @Operation(summary = "单个更新")
    @WebLog
    @ApiLog(title = "单个更新", description = "【前端通用模板】单个更新", type = OperateType.UPDATE)
    @PutMapping
    @Override
    public R<? extends VueTemplate> updateAllField(@Valid @NotNull @RequestBody VueTemplateUpdateDTO domain) {
        return LongKeyConvertibleController.super.updateAllField(domain);
    }

    @Operation(summary = "批量更新")
    @WebLog
    @PutMapping("batch")
    @ApiLog(title = "批量更新", description = "批量更新【前端通用模板】", type = OperateType.UPDATE, isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<? extends Collection<? extends VueTemplate>> updateAllFieldBatch(@Valid @NotNull @RequestBody Collection<VueTemplateUpdateDTO> domains) {
        return LongKeyConvertibleController.super.updateAllFieldBatch(domains);
    }

    @Operation(summary = "根据 条件 批量更新数据")
    @WebLog
    @PutMapping("condition")
    @ApiLog(title = "根据 条件 批量更新数据", description = "根据 条件 批量更新【前端通用模板】", type = OperateType.UPDATE)
    @Override
    public R<?> update(@Valid @NotNull @RequestBody UpdateModel<VueTemplate, VueTemplateUpdateDTO, VueTemplateQueryBody> model) {
        return getBaseService().update(model) ? R.ok() : R.fail();
    }

    @Override
    @Operation(summary = "根据 id 删除记录")
    @WebLog
    @DeleteMapping("/{id}")
    @ApiLog(title = "根据 id 删除记录", description = "根据 id 删除【前端通用模板】", type = OperateType.DELETE)
    @Parameters({
            @Parameter(name = "id", description = "主键 id", required = true, in = ParameterIn.PATH)
    })
    public R<? extends VueTemplate> delete(@PathVariable Long id) {
        return LongKeyConvertibleController.super.delete(id);
    }

    @Override
    @Operation(summary = "根据 id 批量删除记录")
    @WebLog
    @DeleteMapping("batch")
    @ApiLog(title = "根据 id 批量删除记录", description = "根据 id 批量删除【前端通用模板】", type = OperateType.DELETE)
    public R<? extends VueTemplate> deleteBatch(@RequestBody Set<Long> ids) {
        return LongKeyConvertibleController.super.deleteBatch(ids);
    }

    @PostMapping("total")
    @WebLog
    @Operation(summary = "获取总数")
    @Override
    public R<Long> total(@RequestBody VueTemplateQueryBody dto) {
        return R.data(getBaseService().total(JSONObject.from(dto)));
    }

    @PostMapping("page")
    @WebLog
    @Operation(summary = "获取分页")
    @Override
    public R<IPage<VueTemplate>> page(@RequestBody VueTemplateQueryBody dto, SqlPageParams sqlPageParams) {
        return R.data(getBaseService().page(JSONObject.from(dto), sqlPageParams));
    }

    @Operation(summary = "获取列表")
    @WebLog
    @PostMapping("list")
    @Override
    public R<List<VueTemplate>> list(@RequestBody VueTemplateQueryBody dto, SqlPageParams sqlPageParams) {
        return R.data(getBaseService().list(JSONObject.from(dto), sqlPageParams));
    }

    @GetMapping("/{id}")
    @WebLog
    @Operation(summary = "查看详情")
    @Override
    @Parameters({
            @Parameter(name = "id", description = "主键 id", required = true, in = ParameterIn.PATH)
    })
    public R<VueTemplate> detail(@PathVariable Long id) {
        return R.data(getBaseService().detail(JSONObject.of("id", id)));
    }

    @Operation(summary = "excel 导入数据模板")
    @WebLog
    @GetMapping("template")
    public void downloadTemplate(HttpServletResponse response) {
        EasyExcelUtil.export("【前端通用模板】导入模板", response, VueTemplateImpDTO.class, Collections::emptyList);
    }

    @Operation(summary = "excel 导入数据")
    @WebLog
    @PostMapping("imp")
    @ApiLog(title = "excel 导入数据", description = "excel 导入【前端通用模板】", type = OperateType.IMPORT, isSaveRequestData = false, isSaveResultData = false)
    public R<List<VueTemplate>> imp(MultipartFile file) throws IOException {
        AtomicReference<List<VueTemplate>> listRef = new AtomicReference<>();
        EasyExcel.read(file.getInputStream()
                , VueTemplateImpDTO.class
                , new ModelConvertibleListener<VueTemplate>(list -> {
                    listRef.set(list);
                    getBaseService().saveBatch(list);
                })).sheet().doRead();
        return R.data(listRef.get());
    }

    @Operation(summary = "excel 导出数据")
    @WebLog
    @PostMapping("exp")
    @ApiLog(title = "excel 导出数据", description = "excel 导出【前端通用模板】", type = OperateType.EXPORT, isSaveRequestData = false, isSaveResultData = false)
    public void exp(@RequestBody VueTemplateQueryBody dto, SqlPageParams sqlPageParams, HttpServletResponse response) {
        EasyExcelUtil.export("【前端通用模板】导出数据", response, VueTemplateExpVO.class
                , () -> BeanUtil.copyToList(getBaseService().list(JSONObject.from(dto), sqlPageParams), VueTemplateExpVO.class));
    }

    @SneakyThrows
    @Operation(summary = "获取 excel 导出数据可选的字段")
    @WebLog
    @GetMapping("exportSelectedField")
    public R<List<DBField>> getExportSelectedField() {
        return R.data(DBFieldUtil.generate(VueTemplate.class));
    }

    @SneakyThrows
    @Operation(summary = "excel 导出数据（字段可选）")
    @WebLog
    @PostMapping("exportSelectedField")
    public void expSelectedField(@RequestBody ExportTemplate<VueTemplateQueryBody> template, HttpServletRequest request, HttpServletResponse response) {
        excelService.exportExcel(template, request, response, getBaseService()::listMap);
    }

}
