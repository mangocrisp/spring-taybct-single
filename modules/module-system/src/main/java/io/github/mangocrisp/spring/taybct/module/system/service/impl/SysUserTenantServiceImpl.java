package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserTenant;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserTenantMapper;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserTenantService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xijieyin
 */
public class SysUserTenantServiceImpl extends ServiceImpl<SysUserTenantMapper, SysUserTenant>
        implements ISysUserTenantService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<SysUserTenant> entityList, Integer primaryBy) {
        if (primaryBy == null) {
            primaryBy = 1;
        }
        // 返回结果
        Optional<SysUserTenant> first = entityList.stream().findFirst();
        // 需要保存的列表
        Set<SysUserTenant> wallSaveSet;
        if (primaryBy.equals(1)) {
            Assert.isTrue(first.isPresent() && first.get().getUserId() != null,
                    "至少要知道是在操作哪个用户，可能要传，例：[{\"userId\":\"xxx\"}]");
            // 先把之前关联的删除
            remove(Wrappers.<SysUserTenant>lambdaQuery().in(SysUserTenant::getUserId,
                    entityList.stream().map(SysUserTenant::getUserId).collect(Collectors.toSet())));
            // 过滤掉没有 tenantId 的关系
            wallSaveSet = entityList.stream().filter(entity ->
                    entity.getTenantId() != null).collect(Collectors.toSet());
        } else {
            Assert.isTrue(first.isPresent() && first.get().getTenantId() != null,
                    "至少要知道是在操作哪个租户，可能要传，例：[{\"tenantId\":\"xxx\"}]");
            // 先把之前关联的删除
            remove(Wrappers.<SysUserTenant>lambdaQuery().in(SysUserTenant::getTenantId,
                    entityList.stream().map(SysUserTenant::getTenantId).collect(Collectors.toSet())));
            // 过滤掉没有 UserId 的关系
            wallSaveSet = entityList.stream().filter(entity ->
                    entity.getUserId() != null).collect(Collectors.toSet());
        }
        // 如果已经没有关联就直接返回了
        if (CollectionUtil.isEmpty(wallSaveSet)) {
            return true;
        }
        // 然后再批量插入
        return super.saveBatch(wallSaveSet);
    }
}




