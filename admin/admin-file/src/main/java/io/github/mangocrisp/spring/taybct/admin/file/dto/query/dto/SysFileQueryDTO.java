package io.github.mangocrisp.spring.taybct.admin.file.dto.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * 文件管理 列表查询对象
 * TableName: sys_file 列表查询对象
 * </pre>
 *
 * @author 24154
 * @see SysFile
 * @since 2024-09-01 21:20:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【文件管理】列表查询对象")
public class SysFileQueryDTO implements Serializable, ModelConvertible<SysFile> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 文件名（路径）
     */
    @Schema(description = "文件名（路径）")
    private String path;
    /**
     * 上传时间（时间范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "上传时间（时间范围开始）")
    private LocalDateTime uploadTime_ge;
    /**
     * 上传时间（时间范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "上传时间（时间范围结束）")
    private LocalDateTime uploadTime_le;
    /**
     * 上传人
     */
    @Schema(description = "上传人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uploadUser;
    /**
     * 更新时间（时间范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "更新时间（时间范围开始）")
    private LocalDateTime updateTime_ge;
    /**
     * 更新时间（时间范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "更新时间（时间范围结束）")
    private LocalDateTime updateTime_le;
    /**
     * 是否在使用中
     */
    @Schema(description = "是否在使用中")
    private Integer linked;
    /**
     * 关联的表
     */
    @Schema(description = "关联的表")
    private String linkedTable;
    /**
     * 关联的表的 id
     */
    @Schema(description = "关联的表的 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long linkedTableId;
    /**
     * 是否已删除
     */
    @Schema(description = "是否已删除")
    private Integer isDeleted = 0;
    /**
     * 文件管理服务器类型（local,oss,fdfs,minio）
     */
    @Schema(description = "文件管理服务器类型（local,oss,fdfs,minio）")
    private String manageType;
    /**
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;
    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;

    @Hidden
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