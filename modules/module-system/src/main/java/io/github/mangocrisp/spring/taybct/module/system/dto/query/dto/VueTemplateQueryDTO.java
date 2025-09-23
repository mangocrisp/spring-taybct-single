package io.github.mangocrisp.spring.taybct.module.system.dto.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.module.system.domain.VueTemplate;
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
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <pre>
 * 前端通用模板 列表查询对象
 * TableName: t_vue_template 列表查询对象
 * </pre>
 *
 * @author SuMuYue
 * @see VueTemplate
 * @since 2025-08-15 11:12:11
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【前端通用模板】列表查询对象")
public class VueTemplateQueryDTO implements Serializable, ModelConvertible<VueTemplate> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 主键选择
     */
    @Schema(description = "主键选择")
    private Collection<Long> idSelection;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;
    /**
     * 创建时间（时间范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "创建时间（时间范围开始）")
    private LocalDateTime createTime_ge;
    /**
     * 创建时间（时间范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "创建时间（时间范围结束）")
    private LocalDateTime createTime_le;
    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;
    /**
     * 修改时间（时间范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "修改时间（时间范围开始）")
    private LocalDateTime updateTime_ge;
    /**
     * 修改时间（时间范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "修改时间（时间范围结束）")
    private LocalDateTime updateTime_le;
    /**
     * 是否已删除
     */
    @Schema(description = "是否已删除")
    private Integer isDeleted = 0;
    /**
     * 字符串类型
     */
    @Schema(description = "字符串类型")
    private String string;
    /**
     * 整数类型
     */
    @Schema(description = "整数类型")
    private Integer numberInt;
    /**
     * 长整数类型
     */
    @Schema(description = "长整数类型")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long numberLong;
    /**
     * 日期类型（日期范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)
    @Schema(description = "日期类型（日期范围开始）")
    private LocalDate date_ge;
    /**
     * 日期类型（日期范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)
    @Schema(description = "日期类型（日期范围结束）")
    private LocalDate date_le;
    /**
     * 日期时间类型（时间范围开始）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "日期时间类型（时间范围开始）")
    private LocalDateTime dateTime_ge;
    /**
     * 日期时间类型（时间范围结束）
     */
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @Schema(description = "日期时间类型（时间范围结束）")
    private LocalDateTime dateTime_le;
    /**
     * 字节类型
     */
    @Schema(description = "字节类型")
    private Integer numberByte;
    /**
     * 布尔类型
     */
    @Schema(description = "布尔类型")
    private Boolean boolType;
    /**
     * 长文本类型
     */
    @Schema(description = "长文本类型")
    private String textType;
    /**
     * JSON 类型
     */
    @Schema(description = "JSON 类型")
    private Object jsonType;
    /**
     * 单精度浮点类型
     */
    @Schema(description = "单精度浮点类型")
    private Float floatType;
    /**
     * 双精度浮点类型
     */
    @Schema(description = "双精度浮点类型")
    private Double doubleType;

    @Hidden
    VueTemplate convertedBean;

    @Override
    @Hidden
    public void setConvertedBean(VueTemplate convertedBean) {
        throw new BaseException("not support!");
    }

    @Override
    public VueTemplate bean(String... ignoreProperties) {
        VueTemplate bean;
        if ((bean = getConvertedBean()) != null) {
            return bean;
        }
        this.convertedBean = (bean = BeanUtil.copyProperties(this, beanClass(), ignoreProperties));
        return bean;
    }

}