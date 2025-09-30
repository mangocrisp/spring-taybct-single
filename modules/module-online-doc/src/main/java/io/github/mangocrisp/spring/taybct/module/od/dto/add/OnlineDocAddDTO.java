package io.github.mangocrisp.spring.taybct.module.od.dto.add;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDocPermit;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * <pre>
 * 在线文档 新增对象
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 02:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【在线文档】新增对象")
public class OnlineDocAddDTO implements Serializable, ModelConvertible<OnlineDoc> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 文档名称
     */
    @NotBlank(message = "[文档名称]不能为空")
    @Size(max = 255, message = "[文档名称]长度不能超过255")
    @Length(max = 255, message = "[文档名称]长度不能超过255")
    @Schema(description = "文档名称")
    private String name;
    /**
     * 文档是否共享
     */
    @Schema(description = "文档是否共享")
    @NotNull(message = "需要设置是否共享")
    private Integer share;
    /**
     * 文档共享范围
     */
    @Schema(description = "文档共享范围")
    private Set<OnlineDocPermit> onlineDocPermitSet;

    @Hidden
    OnlineDoc convertedBean;

    @Override
    @Hidden
    public void setConvertedBean(OnlineDoc convertedBean) {
        throw new BaseException("not support!");
    }

    @Override
    public OnlineDoc bean(String... ignoreProperties) {
        OnlineDoc bean;
        if ((bean = getConvertedBean()) != null) {
            return bean;
        }
        this.convertedBean = (bean = BeanUtil.copyProperties(this, beanClass(), ignoreProperties));
        return bean;
    }
}