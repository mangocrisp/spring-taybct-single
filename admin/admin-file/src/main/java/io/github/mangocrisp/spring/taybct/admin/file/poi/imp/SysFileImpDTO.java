package io.github.mangocrisp.spring.taybct.admin.file.poi.imp;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * 文件管理上传对象
 * </pre>
 *
 * @author 24154
 * @see SysFile
 * @since 2024-09-01 21:20:40
 */
@Data
@EqualsAndHashCode
@HeadRowHeight(25)
@ContentRowHeight(20)
@ColumnWidth(20)
@HeadFontStyle(fontName = "微软雅黑", fontHeightInPoints = 14)
@ContentFontStyle(fontName = "微软雅黑", fontHeightInPoints = 12)
public class SysFileImpDTO implements Serializable, ModelConvertible<SysFile> {

    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 文件名（路径）
     */
    @NotBlank(message = "[文件名（路径）]不能为空")
    @Size(max = 1000, message = "编码长度不能超过1000")
    @ExcelProperty(value = "文件名（路径）")
    @Length(max = 1000, message = "编码长度不能超过1000")
    private String path;
    /**
     * 上传时间
     */
    @NotNull(message = "[上传时间]不能为空")
    @ExcelProperty(value = "上传时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime uploadTime;
    /**
     * 上传人
     */
    @ExcelProperty(value = "上传人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uploadUser;
    /**
     * 更新时间
     */
    @NotNull(message = "[更新时间]不能为空")
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime updateTime;
    /**
     * 是否在使用中
     */
    @NotNull(message = "[是否在使用中]不能为空")
    @ExcelProperty(value = "是否在使用中")
    private Integer linked;
    /**
     * 关联的表
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @ExcelProperty(value = "关联的表")
    @Length(max = 100, message = "编码长度不能超过100")
    private String linkedTable;
    /**
     * 关联的表的 id
     */
    @ExcelProperty(value = "关联的表的 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long linkedTableId;
    /**
     * 是否已删除
     */
    @NotNull(message = "[是否已删除]不能为空")
    @ExcelProperty(value = "是否已删除")
    private Integer isDeleted;
    /**
     * 文件管理服务器类型（local,oss,fdfs,minio）
     */
    @NotBlank(message = "[文件管理服务器类型（local,oss,fdfs,minio）]不能为空")
    @Size(max = 50, message = "编码长度不能超过50")
    @ExcelProperty(value = "文件管理服务器类型（local,oss,fdfs,minio）")
    @Length(max = 50, message = "编码长度不能超过50")
    private String manageType;
    /**
     * 文件类型
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ExcelProperty(value = "文件类型")
    @Length(max = 255, message = "编码长度不能超过255")
    private String fileType;
    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;

    @Hidden
    @ExcelIgnore
    SysFile convertedBean;

    @Override
    @Hidden
    public void setConvertedBean(SysFile convertedBean) {
        throw new BaseException("not support!");
    }

    @Override
    public SysFile bean(String... ignoreProperties) {
        SysFile bean;
        if ((bean = getConvertedBean()) != null) {
            return bean;
        }
        this.convertedBean = (bean = BeanUtil.copyProperties(this, beanClass(), ignoreProperties));
        return bean;
    }
}
