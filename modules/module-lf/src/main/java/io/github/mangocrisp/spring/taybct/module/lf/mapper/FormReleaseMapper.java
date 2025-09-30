package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.FormRelease;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleaseQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author admin
 * <br>description 针对表【lf_form_release(流程表单发布表)】的数据库操作Mapper
 * @see FormRelease
 * @since 2023-07-21 15:18:29
 */
public interface FormReleaseMapper extends BaseMapper<FormRelease> {

    /**
     * 发布流程图
     *
     * @param dto 数据
     * @return 影响行数
     */
    int publish(@Param("dto") Collection<FormReleasePublishDTO> dto);

    /**
     * 分页查询
     *
     * @param page 分页参数
     * @param dto  查询参数
     * @param <P>  分页参数
     * @return 分页数据
     */
    <P extends IPage<FormRelease>> P page(P page, @Param("dto") FormReleaseQueryDTO dto);

}




