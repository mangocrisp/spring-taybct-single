package io.github.mangocrisp.spring.taybct.module.system.dto.query.body;

import io.github.mangocrisp.spring.taybct.module.system.domain.VueTemplate;
import io.github.mangocrisp.spring.taybct.module.system.dto.query.dto.VueTemplateQueryDTO;
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
 * 前端通用模板 列表多条件查询对象
 * TableName: t_vue_template 列表多条件查询对象
 * </pre>
 *
 * @author SuMuYue
 * @see VueTemplate
 * @since 2025-08-15 11:12:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "【前端通用模板】列表多条件查询对象")
public class VueTemplateQueryBody implements Serializable, ModelConvertible<VueTemplate> {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 【前端通用模板】查询 dto
     */
    @Schema(description = "【前端通用模板】查询 dto")
    private VueTemplateQueryDTO vueTemplateQueryDTO = new VueTemplateQueryDTO();

    // TODO 还可以继续添加 dto 进来

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
        this.convertedBean = (bean = BeanUtil.copyProperties(vueTemplateQueryDTO, beanClass(), ignoreProperties));
        return bean;
    }

}