package io.github.mangocrisp.spring.taybct.module.lf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.module.lf.domain.ReleasePermissions;
import org.apache.ibatis.annotations.Param;

/**
 * @author admin
 * <br>description 针对表【lf_release_permissions(流程发布权限表，用于关联指定流程，可以被哪些角色或者用户看到)】的数据库操作Mapper
 * @see ReleasePermissions
 * @since 2023-07-03 11:32:23
 */
public interface ReleasePermissionsMapper extends BaseMapper<ReleasePermissions> {

    /**
     * 发布流程设计权限复制
     *
     * @param id        主键
     * @param releaseId 发布 id
     * @param designId  设计图 id
     * @return 影响行数
     */
    int publishPermission(@Param("id") Long id
            , @Param("releaseId") Long releaseId
            , @Param("designId") Long designId);

}




