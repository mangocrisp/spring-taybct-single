package io.github.mangocrisp.spring.taybct.api.system.vo;

import cn.hutool.core.convert.Convert;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysParams;
import io.github.mangocrisp.spring.taybct.common.enums.ParamType;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 系统参数扩展
 *
 * @author xijieyin <br> 2022/8/5 10:17
 * @since 1.0.0
 */
@Schema(description = "系统参数扩展")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysParamsVO extends SysParams {

    @Serial
    private static final long serialVersionUID = -740944893290642789L;
    @Schema(description = "真值，带类型转换的值")
    private Object realValue;

    public Object getRealValue() {
        ParamType paramType = ParamType.valueOf(getType());
        if (paramType.equals(ParamType.DATE_TIME)) {
            return LocalDateTime.parse(getParamsVal(), DateTimeFormatter.ofPattern(DateConstants.format.YYYY_MM_DD_HH_mm_ss, Locale.CHINA));
        }
        return Convert.convert(paramType.getClazz(), getParamsVal(), null);
    }

}
