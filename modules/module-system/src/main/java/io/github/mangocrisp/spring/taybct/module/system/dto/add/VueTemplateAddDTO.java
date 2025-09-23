package io.github.mangocrisp.spring.taybct.module.system.dto.add;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 * 前端通用模板 新增对象
 * TableName: t_vue_template 新增对象
 * </pre>
 *
 * @author SuMuYue
 * @since 2025-08-15 11:12:11
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【前端通用模板】新增对象")
public class VueTemplateAddDTO implements Serializable, ModelConvertible<VueTemplate> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字符串类型
     */
    @NotBlank(message = "[字符串类型]不能为空")
    @Size(max = 200, message = "[字符串类型]长度不能超过200")
    @Length(max = 200, message = "[字符串类型]长度不能超过200")
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
     * 日期类型
     */
    @Schema(description = "日期类型")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD)
    private LocalDate date;
    /**
     * 日期时间类型
     */
    @Schema(description = "日期时间类型")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime dateTime;
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
    private String jsonType;
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