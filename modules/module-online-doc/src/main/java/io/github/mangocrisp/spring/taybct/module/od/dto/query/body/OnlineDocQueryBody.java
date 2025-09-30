package io.github.mangocrisp.spring.taybct.module.od.dto.query.body;

import io.github.mangocrisp.spring.taybct.module.od.domain.OnlineDoc;
import io.github.mangocrisp.spring.taybct.module.od.dto.query.dto.OnlineDocQueryDTO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ModelConvertible;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.util.BeanUtil;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 在线文档 列表多条件查询对象
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 02:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【在线文档】列表多条件查询对象")
public class OnlineDocQueryBody implements Serializable, ModelConvertible<OnlineDoc> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 【在线文档】查询 dto
     */
    @Schema(description = "【在线文档】查询 dto")
    private OnlineDocQueryDTO onlineDocQueryDTO = new OnlineDocQueryDTO();

    // TODO 还可以继续添加 dto 进来

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
        this.convertedBean = (bean = BeanUtil.copyProperties(onlineDocQueryDTO, beanClass(), ignoreProperties));
        return bean;
    }

}