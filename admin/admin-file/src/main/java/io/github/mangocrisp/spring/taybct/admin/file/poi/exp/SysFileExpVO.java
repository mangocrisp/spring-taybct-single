package io.github.mangocrisp.spring.taybct.admin.file.poi.exp;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * 文件管理下载对象
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
public class SysFileExpVO implements Serializable {

    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 文件名（路径）
     */
    @ExcelProperty(value = "文件名（路径）")
    private String path;
    /**
     * 上传时间
     */
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
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime updateTime;
    /**
     * 是否在使用中
     */
    @ExcelProperty(value = "是否在使用中")
    private Integer linked;
    /**
     * 关联的表
     */
    @ExcelProperty(value = "关联的表")
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
    @ExcelProperty(value = "是否已删除")
    private Integer isDeleted;
    /**
     * 文件管理服务器类型（local,oss,fdfs,minio）
     */
    @ExcelProperty(value = "文件管理服务器类型（local,oss,fdfs,minio）")
    private String manageType;
    /**
     * 文件类型
     */
    @ExcelProperty(value = "文件类型")
    private String fileType;
    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;

}
