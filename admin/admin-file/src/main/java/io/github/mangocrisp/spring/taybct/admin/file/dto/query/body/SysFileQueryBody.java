package io.github.mangocrisp.spring.taybct.admin.file.dto.query.body;

import io.github.mangocrisp.spring.taybct.admin.file.domain.SysFile;
import io.github.mangocrisp.spring.taybct.admin.file.dto.query.dto.SysFileQueryDTO;
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
 * 文件管理 列表多条件查询对象
 * TableName: sys_file 列表多条件查询对象
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
@Schema(description = "【文件管理】列表多条件查询对象")
public class SysFileQueryBody implements Serializable, ModelConvertible<SysFile> {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 【文件管理】查询 dto
     */
    @Schema(description = "【文件管理】查询 dto")
    private SysFileQueryDTO sysFileQueryDTO = new SysFileQueryDTO();

    // TODO 还可以继续添加 dto 进来

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
        this.convertedBean = (bean = BeanUtil.copyProperties(sysFileQueryDTO, beanClass(), ignoreProperties));
        return bean;
    }

}