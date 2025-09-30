package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.lf.domain.DesignPermissions;
import io.github.mangocrisp.spring.taybct.module.lf.enums.DesignPermissionsType;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.DesignPermissionsMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IDesignPermissionsService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.enums.DataScopeGetNotDealType;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.interceptor.DataScopeData;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.DataScopeUtil;
import io.github.mangocrisp.spring.taybct.tool.core.request.SqlQueryParams;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin
 * <br>description 针对表【lf_design_permissions(流程图权限表)】的数据库操作Service实现
 * @since 2023-07-06 10:21:37
 */
public class DesignPermissionsServiceImpl extends ServiceImpl<DesignPermissionsMapper, DesignPermissions>
        implements IDesignPermissionsService {

    @Autowired(required = false)
    protected ISecurityUtil securityUtil;

    @Autowired(required = false)
    protected DataScopeUtil dataScopeUtil;

    @Autowired(required = false)
    protected DataSource dataSource;

    @Override
    public boolean checkPermission(Collection<Long> designIdList, DesignPermissionsType[] permissionsTypes) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        String conditionSql = dataScopeUtil.getConditionSql(new DataScopeData()
                        .setAlias("lf_design_permissions")
                        .setField("dept_id")
                        .setNotExistDealType(DataScopeGetNotDealType.ALLOW)
                , DataScopeUtil.getDbType());
        LambdaQueryWrapper<DesignPermissions> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(DesignPermissions::getDesignId, designIdList);
        // 先判断是当前用户可以操作的，当然，也有可能不是绑定当前用户，而是绑定的当前用户的部门
        queryWrapper.and(i -> i.isNull(DesignPermissions::getUserId).or().eq(DesignPermissions::getUserId, loginUser.getUserId()));
        queryWrapper.and(i -> i.isNull(DesignPermissions::getDeptId).or().exists(conditionSql));
        if (ArrayUtil.contains(permissionsTypes, DesignPermissionsType.EDIT)) {
            queryWrapper.eq(DesignPermissions::getPermEdit, 1);
        }
        if (ArrayUtil.contains(permissionsTypes, DesignPermissionsType.DELETE)) {
            queryWrapper.eq(DesignPermissions::getPermDelete, 1);
        }
        if (ArrayUtil.contains(permissionsTypes, DesignPermissionsType.SHARE)) {
            queryWrapper.eq(DesignPermissions::getPermShare, 1);
        }
        if (ArrayUtil.contains(permissionsTypes, DesignPermissionsType.PUBLISH)) {
            queryWrapper.eq(DesignPermissions::getPermPublish, 1);
        }
        return getBaseMapper().exists(queryWrapper);
    }

    @Override
    public boolean shareDesign(List<DesignPermissions> list) {
        Set<Long> idSet = list.stream()
                .map(DesignPermissions::getDesignId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Assert.isTrue(CollectionUtil.isNotEmpty(idSet),
                "至少要知道是在操作哪个流程图，可能要传，例：[{\"designId\":\"xxx\"}]");
        if (!checkPermission(idSet, new DesignPermissionsType[]{DesignPermissionsType.SHARE})) {
            throw new BaseException("没有分享权限！");
        }
        // 先把之前关联的删除
        remove(Wrappers.<DesignPermissions>lambdaQuery().in(DesignPermissions::getDesignId, idSet));
        // 过滤掉没有需要关联的关联关系
        Set<DesignPermissions> wallSaveSet = list.stream().filter(entity ->
                        // 这里要过滤掉没有值的数据
                        entity.getDesignId() != null && (entity.getDeptId() != null || entity.getUserId() != null))
                .collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            return true;
        }
        return super.saveBatch(wallSaveSet);
    }

    @Override
    public List<DesignPermissions> getPermissions(Long designId, Long userId, SqlQueryParams sqlQueryParams) {
        if (ObjectUtil.isNull(designId) && ObjectUtil.isNull(userId)) {
            throw new BaseException("请指定查询条件，按流程图查询，或者是按用户查询");
        }
        Page<DesignPermissions> page = MyBatisUtil.genPage(sqlQueryParams);
        // 这里不查询页数
        page.setSearchCount(false);
        LambdaQueryWrapper<DesignPermissions> queryWrapper = Wrappers.lambdaQuery();
        if (ObjectUtil.isNotNull(designId)) {
            queryWrapper.eq(DesignPermissions::getDesignId, designId);
        }
        if (ObjectUtil.isNotNull(userId)) {
            queryWrapper.eq(DesignPermissions::getUserId, userId);
        }
        return page(page, queryWrapper).getRecords();
    }

}




