package io.github.mangocrisp.spring.taybct.module.system.poi.imp;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.module.system.domain.VueTemplate;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * 前端通用模板上传对象
 * </pre>
 *
 * @author SuMuYue
 * @see VueTemplate
 * @since 2025-08-15 11:12:11
 */
@Data
@EqualsAndHashCode
@HeadRowHeight(25)
@ContentRowHeight(20)
@ColumnWidth(20)
@HeadFontStyle(fontName = "微软雅黑", fontHeightInPoints = 14)
@ContentFontStyle(fontName = "微软雅黑", fontHeightInPoints = 12)
public class VueTemplateImpDTO implements Serializable, ModelConvertible<VueTemplate> {

    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @NotNull(message = "[创建人]不能为空")
    @ExcelProperty(value = "创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;
    /**
     * 创建时间
     */
    @NotNull(message = "[创建时间]不能为空")
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime createTime;
    /**
     * 修改人
     */
    @ExcelProperty(value = "修改人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;
    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime updateTime;
    /**
     * 是否已删除
     */
    @NotNull(message = "[是否已删除]不能为空")
    @ExcelProperty(value = "是否已删除")
    private Integer isDeleted;
    /**
     * 字符串类型
     */
    @NotBlank(message = "[字符串类型]不能为空")
    @Size(max = 200, message = "[字符串类型]长度不能超过200")
    @Length(max = 200, message = "[字符串类型]长度不能超过200")
    @ExcelProperty(value = "字符串类型")
    private String string;
    /**
     * 整数类型
     */
    @ExcelProperty(value = "整数类型")
    private Integer numberInt;
    /**
     * 长整数类型
     */
    @ExcelProperty(value = "长整数类型")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long numberLong;
    /**
     * 日期类型
     */
    @ExcelProperty(value = "日期类型")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)
    private LocalDate date;
    /**
     * 日期时间类型
     */
    @ExcelProperty(value = "日期时间类型")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime dateTime;
    /**
     * 字节类型
     */
    @ExcelProperty(value = "字节类型")
    private Integer numberByte;
    /**
     * 布尔类型
     */
    @ExcelProperty(value = "布尔类型")
    private Boolean boolType;
    /**
     * 长文本类型
     */
    @ExcelProperty(value = "长文本类型")
    private String textType;
    /**
     * JSON 类型
     */
    @ExcelProperty(value = "JSON 类型")
    private Object jsonType;
    /**
     * 单精度浮点类型
     */
    @ExcelProperty(value = "单精度浮点类型")
    private Float floatType;
    /**
     * 双精度浮点类型
     */
    @ExcelProperty(value = "双精度浮点类型")
    private Double doubleType;

    @Hidden
    @ExcelIgnore
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
