package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.lf.domain.DesignPermissions;
import io.github.mangocrisp.spring.taybct.module.lf.enums.DesignPermissionsType;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;

import java.util.Collection;
import java.util.List;

/**
 * @author admin
 * <br>description 针对表【lf_design_permissions(流程图权限表)】的数据库操作Service
 * @since 2023-07-06 10:21:37
 */
public interface IDesignPermissionsService extends IService<DesignPermissions> {
    /**
     * 检查是否有操作设计图的权限
     *
     * @param designIdList     流程图 id 列表
     * @param permissionsTypes 要检查的权限类型
     * @return boolean
     */
    boolean checkPermission(Collection<Long> designIdList, DesignPermissionsType[] permissionsTypes);

    /**
     * 分享设计
     *
     * @param list 设计列表
     * @return boolean
     */
    boolean shareDesign(List<DesignPermissions> list);

    /**
     * 获取流程设计权限分配
     *
     * @param designId       流程 id
     * @param userId         用户 id
     * @param sqlQueryParams 分布查询条件
     * @return list
     */
    List<DesignPermissions> getPermissions(Long designId
            , Long userId
            , SqlQueryParams sqlQueryParams);

}
