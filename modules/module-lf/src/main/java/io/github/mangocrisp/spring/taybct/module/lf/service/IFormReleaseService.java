package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.FormRelease;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.FormReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;

/**
 * @author admin
 * <br>description 针对表【lf_form_release(流程表单发布表)】的数据库操作Service
 * @since 2023-07-21 15:18:29
 */
public interface IFormReleaseService extends IBaseService<FormRelease> {

    /**
     * 发布表单
     *
     * @param dto 数据
     * @return 是否发布成功
     */
    boolean publish(FormReleasePublishDTO dto);

    /**
     * 发布表单列表
     *
     * @param dto            查询参数
     * @param sqlQueryParams 分页参数
     * @return page
     */
    IPage<? extends FormRelease> publishList(FormReleaseQueryDTO dto, SqlQueryParams sqlQueryParams);

}
