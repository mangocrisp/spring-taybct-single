package ${baseInfo.packageName};

import ${tableClass.fullClassName};
import ${baseInfo.packageName?replace(".controller", ".service")}.I${tableClass.shortClassName}Service;
import ${poiVO.packageName}.${poiVO.fileName};
import ${poiDTO.packageName}.${poiDTO.fileName};
import ${addDTO.packageName}.${addDTO.fileName};
import ${updateDTO.packageName}.${updateDTO.fileName};
import ${queryBody.packageName}.${queryBody.fileName};
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.WebLog;
import io.github.mangocrisp.spring.taybct.tool.core.bean.controller.LongKeyConvertibleController;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UpdateModel;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easyexcel.listener.ModelConvertibleListener;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easyexcel.util.EasyExcelUtil;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.support.SqlPageParams;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.service.IExcelService;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.support.ExportTemplate;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.util.DBField;
import io.github.mangocrisp.spring.taybct.tool.core.poi.easypoi.util.DBFieldUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
*
<pre>
 * 针对表【${tableClass.tableName}<#if tableClass.remark?has_content>(${tableClass.remark!})</#if>】的数据库操作 Controller 控制器
 * </pre>
*
* @author ${author!}
* @since ${.now?string('yyyy-MM-dd HH:mm:ss')}
*/
@Tag(name = "${tableClass.remark!}控制器")
@RestController
@RequestMapping("/v1/${tableClass.shortClassName?uncap_first}")
@RequiredArgsConstructor
public class ${baseInfo.fileName} implements LongKeyConvertibleController<${tableClass.shortClassName}
, I${tableClass.shortClassName}Service
, ${tableClass.shortClassName}QueryBody
, ${tableClass.shortClassName}AddDTO
, ${tableClass.shortClassName}UpdateDTO> {

final IExcelService excelService;

@Operation(summary = "单个新增")
@WebLog
@PostMapping
@ApiLog(title = "单个新增", description = "【${tableClass.remark}】单个新增", type = OperateType.INSERT)
@Override
public R<? extends ${tableClass.shortClassName}> add(@Valid @NotNull @RequestBody ${tableClass.shortClassName}AddDTO domain) {
return LongKeyConvertibleController.super.add(domain);
}

@Operation(summary = "批量新增")
@WebLog
@PostMapping("batch")
@ApiLog(title = "批量新增", description = "【${tableClass.remark}】批量新增", type = OperateType.INSERT, isSaveRequestData = false, isSaveResultData = false)
@Override
public R<? extends Collection<? extends ${tableClass.shortClassName}>> saveBatch(@Valid @NotNull @RequestBody Collection
<${tableClass.shortClassName}AddDTO> domains) {
    return LongKeyConvertibleController.super.saveBatch(domains);
    }

    @Operation(summary = "单个更新")
    @WebLog
    @ApiLog(title = "单个更新", description = "【${tableClass.remark}】单个更新", type = OperateType.UPDATE)
    @PutMapping
    @Override
    public R<? extends ${tableClass.shortClassName}> updateAllField(@Valid @NotNull
    @RequestBody ${tableClass.shortClassName}UpdateDTO domain) {
    return LongKeyConvertibleController.super.updateAllField(domain);
    }

    @Operation(summary = "批量更新")
    @WebLog
    @PutMapping("batch")
    @ApiLog(title = "批量更新", description = "批量更新【${tableClass.remark}】", type = OperateType.UPDATE,
    isSaveRequestData = false, isSaveResultData = false)
    @Override
    public R<? extends Collection<? extends ${tableClass.shortClassName}>> updateAllFieldBatch(@Valid @NotNull
    @RequestBody Collection
    <${tableClass.shortClassName}UpdateDTO> domains) {
        return LongKeyConvertibleController.super.updateAllFieldBatch(domains);
        }

        @Operation(summary = "根据 条件 批量更新数据")
        @WebLog
        @PutMapping("condition")
        @ApiLog(title = "根据 条件 批量更新数据", description = "根据 条件 批量更新【${tableClass.remark!}】", type =
        OperateType.UPDATE)
        @Override
        public R<?> update(@Valid @NotNull @RequestBody UpdateModel<${tableClass.shortClassName}
        , ${tableClass.shortClassName}UpdateDTO, ${tableClass.shortClassName}QueryBody> model) {
        return getBaseService().update(model) ? R.ok() : R.fail();
        }

        @Override
        @Operation(summary = "根据 id 删除记录")
        @WebLog
        @DeleteMapping("/{id}")
        @ApiLog(title = "根据 id 删除记录", description = "根据 id 删除【${tableClass.remark!}】", type =
        OperateType.DELETE)
        @Parameters({
        @Parameter(name = "id", description = "主键 id", required = true, in = ParameterIn.PATH)
        })
        public R<? extends ${tableClass.shortClassName}> delete(@PathVariable Long id) {
        return LongKeyConvertibleController.super.delete(id);
        }

        @Override
        @Operation(summary = "根据 id 批量删除记录")
        @WebLog
        @DeleteMapping("batch")
        @ApiLog(title = "根据 id 批量删除记录", description = "根据 id 批量删除【${tableClass.remark!}】", type =
        OperateType.DELETE)
        public R<? extends ${tableClass.shortClassName}> deleteBatch(@RequestBody Set
        <Long> ids) {
            return LongKeyConvertibleController.super.deleteBatch(ids);
            }

            @PostMapping("total")
            @WebLog
            @Operation(summary = "获取总数")
            @Override
            public R
            <Long> total(@RequestBody ${tableClass.shortClassName}QueryBody dto) {
                return R.data(getBaseService().total(JSONObject.from(dto)));
                }

                @PostMapping("page")
                @WebLog
                @Operation(summary = "获取分页")
                @Override
                public R
                <IPage
                <${tableClass.shortClassName}>> page(@RequestBody ${tableClass.shortClassName}QueryBody dto,
                SqlPageParams sqlPageParams) {
                return R.data(getBaseService().page(JSONObject.from(dto), sqlPageParams));
                }

                @Operation(summary = "获取列表")
                @WebLog
                @PostMapping("list")
                @Override
                public R
                <List
                <${tableClass.shortClassName}>> list(@RequestBody ${tableClass.shortClassName}QueryBody dto,
                SqlPageParams sqlPageParams) {
                return R.data(getBaseService().list(JSONObject.from(dto), sqlPageParams));
                }

                @GetMapping("/{id}")
                @WebLog
                @Operation(summary = "查看详情")
                @Override
                @Parameters({
                @Parameter(name = "id", description = "主键 id", required = true, in = ParameterIn.PATH)
                })
                public R<${tableClass.shortClassName}> detail(@PathVariable Long id) {
                return R.data(getBaseService().detail(JSONObject.of("id", id)));
                }

                @Operation(summary = "excel 导入数据模板")
                @WebLog
                @GetMapping("template")
                public void downloadTemplate(HttpServletResponse response) {
                EasyExcelUtil.export("【${tableClass.remark!}】导入模板", response, ${tableClass.shortClassName}
                ImpDTO.class, Collections::emptyList);
                }

                @Operation(summary = "excel 导入数据")
                @WebLog
                @PostMapping("imp")
                @ApiLog(title = "excel 导入数据", description = "excel 导入【${tableClass.remark!}】", type =
                OperateType.IMPORT, isSaveRequestData = false, isSaveResultData = false)
                public R
                <List
                <${tableClass.shortClassName}>> imp(MultipartFile file) throws IOException {
                AtomicReference
                <List
                <${tableClass.shortClassName}>> listRef = new AtomicReference<>();
                EasyExcel.read(file.getInputStream()
                , ${tableClass.shortClassName}ImpDTO.class
                , new ModelConvertibleListener<${tableClass.shortClassName}>(list -> {
                listRef.set(list);
                getBaseService().saveBatch(list);
                })).sheet().doRead();
                return R.data(listRef.get());
                }

                @Operation(summary = "excel 导出数据")
                @WebLog
                @PostMapping("exp")
                @ApiLog(title = "excel 导出数据", description = "excel 导出【${tableClass.remark!}】", type =
                OperateType.EXPORT, isSaveRequestData = false, isSaveResultData = false)
                public void exp(@RequestBody ${tableClass.shortClassName}QueryBody dto, SqlPageParams sqlPageParams,
                HttpServletResponse response) {
                EasyExcelUtil.export("【${tableClass.remark!}】导出数据", response, ${tableClass.shortClassName}
                ExpVO.class
                , () -> BeanUtil.copyToList(getBaseService().list(JSONObject.from(dto),
                sqlPageParams), ${tableClass.shortClassName}ExpVO.class));
                }

                @SneakyThrows
                @Operation(summary = "获取 excel 导出数据可选的字段")
                @WebLog
                @GetMapping("exportSelectedField")
                public R
                <List
                <DBField>> getExportSelectedField() {
                    return R.data(DBFieldUtil.generate(${tableClass.shortClassName}.class));
                    }

                    @SneakyThrows
                    @Operation(summary = "excel 导出数据（字段可选）")
                    @WebLog
                    @PostMapping("exportSelectedField")
                    public void expSelectedField(@RequestBody ExportTemplate
                    <${tableClass.shortClassName}QueryBody> template, HttpServletRequest request, HttpServletResponse
                        response) {
                        excelService.exportExcel(template, request, response, getBaseService()::listMap);
                        }

                        }
