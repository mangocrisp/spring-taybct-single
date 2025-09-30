package io.github.mangocrisp.spring.taybct.admin.file.controller;

import io.github.mangocrisp.spring.taybct.admin.file.service.ISysFileService;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiLog;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.ApiVersion;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.RestControllerRegister;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.file.util.FileServiceBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

/**
 * 文件处理控制器，这个控制器只上传文件和下载文件<br>
 * 文件管理有多种存储方式支持，在 v1.0.0 版本支持 Local,FastDFS,MinIO,OSS 这四种
 *
 * @author xijieyin <br> 2021/12/3 14:24
 * @since 1.0.0
 */
@Tag(name = "文件处理相关接口")
@RestControllerRegister(ServeConstants.CONTEXT_PATH_ADMIN_FILE + "{version}")
@ApiVersion
public interface IFileController {

    ISysFileService getSysFileService();

    /**
     * 上传文件
     *
     * @param file 文件数组
     * @return 返回文件上传成功的路径
     */
    @Operation(summary = "文件上传")
    @PostMapping(value = {"/upload"})
    @ApiLog(title = "上传文件", description = "上传多个文件", type = OperateType.IMPORT, isSaveRequestData = false)
    default R<?> upload(@NotNull MultipartFile[] file) {
        Assert.notEmpty(file, "文件不能为空！");
        List<String> urls = new ArrayList<>();
        for (MultipartFile f : file) {
            String url;
            try {
                url = FileServiceBuilder.upload(f);
            } catch (Exception e) {
                throw new BaseException(String.format("文件[%s]上传失败！%s", f.getOriginalFilename(), e.getMessage()));
            }
            urls.add(url);
        }
        getSysFileService().upload(new LinkedHashSet<>(urls));
        return R.data(urls);
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
    @GetMapping(value = {"/download"})
    @Parameters({
            @Parameter(name = "path", description = "文件路径")
            , @Parameter(name = "fileName", description = "文件名")
            , @Parameter(name = "d", description = "是否需要下载")
            , @Parameter(name = "dName", description = "下载指定的文件名")
    })
    default void download(HttpServletResponse response, @RequestParam String path
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


}
