package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.lf.domain.ReleasePermissions;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.ReleasePermissionsMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IReleasePermissionsService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin
 * <br>description 针对表【lf_release_permissions(流程发布权限表，用于关联指定流程，可以被哪些角色或者用户看到)】的数据库操作Service实现
 * @since 2023-07-03 11:32:23
 */
public class ReleasePermissionsServiceImpl extends ServiceImpl<ReleasePermissionsMapper, ReleasePermissions>
        implements IReleasePermissionsService {

    @Override
    public boolean permissions(List<ReleasePermissions> list) {
        Set<Long> idSet = list.stream()
                .map(ReleasePermissions::getReleaseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Assert.isTrue(CollectionUtil.isNotEmpty(idSet),
                "至少要知道是在操作哪个发行流程，可能要传，例：[{\"releaseId\":\"xxx\"}]");
        // 先把之前关联的删除
        remove(Wrappers.<ReleasePermissions>lambdaQuery().in(ReleasePermissions::getReleaseId, idSet));
        // 过滤掉没有需要关联的关联关系
        Set<ReleasePermissions> wallSaveSet = list.stream().filter(entity ->
                        // 这里要过滤掉没有值的数据
                        entity.getReleaseId() != null && (entity.getDeptId() != null || entity.getUserId() != null))
                .collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            return true;
        }
        return super.saveBatch(wallSaveSet);
    }

    @Override
    public List<ReleasePermissions> getPermissions(Long releaseId, Long userId, SqlQueryParams sqlQueryParams) {
        if (ObjectUtil.isNull(releaseId) && ObjectUtil.isNull(userId)) {
            throw new BaseException("请指定查询条件，按发布流程查询，或者是按用户查询");
        }
        Page<ReleasePermissions> page = MyBatisUtil.genPage(sqlQueryParams);
        // 这里不查询页数
        page.setSearchCount(false);
        LambdaQueryWrapper<ReleasePermissions> queryWrapper = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotNull(releaseId)) {
            queryWrapper.eq(ReleasePermissions::getReleaseId, releaseId);
        }
        if (ObjectUtil.isNotNull(userId)) {
            queryWrapper.eq(ReleasePermissions::getUserId, userId);
        }
        return page(page, queryWrapper).getRecords();
    }

}




