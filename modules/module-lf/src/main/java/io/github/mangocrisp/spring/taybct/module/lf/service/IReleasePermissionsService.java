package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.lf.domain.ReleasePermissions;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;

import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_release_permissions(流程发布权限表，用于关联指定流程，可以被哪些角色或者用户看到)】的数据库操作Service
 * @since 2023-07-03 11:32:23
 */
public interface IReleasePermissionsService extends IService<ReleasePermissions> {

    /**
     * 分配发布查看权限
     *
     * @param list 权限列表
     * @return boolean
     */
    boolean permissions(List<ReleasePermissions> list);

    /**
     * 获取流程发布权限分配
     *
     * @param releaseId      流程发布 id
     * @param userId         用户 id
     * @param sqlQueryParams 分布查询条件
     * @return list
     */
    List<ReleasePermissions> getPermissions(Long releaseId
            , Long userId
            , SqlQueryParams sqlQueryParams);
}
