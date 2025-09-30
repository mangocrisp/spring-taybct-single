package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Release;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleasePublishDTO;
import io.github.mangocrisp.spring.taybct.module.lf.dto.ReleaseQueryDTO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;

/**
 * @author admin
 * <br>description 针对表【lf_release(流程发布表)】的数据库操作Service
 * @since 2023-07-03 11:31:36
 */
public interface IReleaseService extends IBaseService<Release> {

    /**
     * 发布流程图
     *
     * @param dto 数据
     * @return 是否发布成功
     */
    boolean publish(ReleasePublishDTO dto);

    /**
     * 发布列表
     *
     * @param dto            查询参数
     * @param sqlQueryParams 分页参数
     * @return page
     */
    IPage<? extends Release> publishList(ReleaseQueryDTO dto, SqlQueryParams sqlQueryParams);

    /**
     * 发布详情
     *
     * @param id 发布 id
     * @return Release
     */
    Release detail(Long id);

}
