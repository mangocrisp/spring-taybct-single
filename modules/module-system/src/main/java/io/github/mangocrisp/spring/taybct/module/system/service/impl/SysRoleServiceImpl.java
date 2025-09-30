package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserRole;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysRoleQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.ROLE;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRolePermissionService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysRoleService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author xijieyin
 */
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole>
        implements ISysRoleService {

    @Resource
    ISysRolePermissionService sysRolePermissionService;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Resource
    ISysParamsObtainService sysParamsObtainService;

    @Override
    public List<SysRole> customizeList(Map<String, Object> params) {
        AtomicReference<List<SysRole>> result = new AtomicReference<>(Collections.emptyList());
        IPage<SysRole> page = customizeQueryPage(params);
        SysRoleQueryDTO dto = JSONObject.parseObject(JSONObject.toJSONString(params), SysRoleQueryDTO.class);
        getSysRoles(dto, params, page.getCurrent(), page.getSize(), (total, sysUsers) -> {
            mergeQueryExpansion(sysUsers);
            result.set(sysUsers);
        });
        return result.get();
    }

    @Override
    public IPage<SysRole> customizePage(Map<String, Object> params) {
        IPage<SysRole> page = customizeQueryPage(params);
        SysRoleQueryDTO dto = JSONObject.parseObject(JSONObject.toJSONString(params), SysRoleQueryDTO.class);
        getSysRoles(dto, params, page.getCurrent(), page.getSize(), (total, sysUsers) -> {
            page.setTotal(total);
            page.setRecords(sysUsers);
            mergeQueryExpansion(page.getRecords());
        });
        return page;
    }

    /**
     * 获取用户，如果不传分页参数，就不分页
     *
     * @param dto            查询参数
     * @param sqlQueryParams sql 查询参数
     * @param current        当前页码
     * @param size           页面大小
     * @param result         返回结果，因为会返回 总数 和 列表，所以这里是一个 BiConsumer
     * @author xijieyin <br> 2022/9/30 15:37
     * @see BiConsumer
     * @since 1.0.4
     */
    private void getSysRoles(SysRoleQueryDTO dto, Map<String, Object> sqlQueryParams, Long current, Long size, BiConsumer<Long, List<SysRole>> result) {
        String pageOrder = MyBatisUtil.getPageOrder(sqlQueryParams);
        SysRole convert = Convert.convert(SysRole.class, dto);
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(convert));
        params.remove("expansion");
        params.remove("isCreateByLoginUser");
        params.remove("filterByUser");
        params.remove("filterByRole");
        params.remove("includeParents");
        params.remove("includeChildren");
        params.remove("deptId");
        ILoginUser loginUser = securityUtil.getLoginUser();
        List<SysRole> list = Collections.emptyList();
        long count = baseMapper.countQuery(loginUser.getTenantId()
                , loginUser.getUserId()
                , ROLE.ROOT
                , ROLE.ADMIN
                , params
                , dto
                , loginUser.checkAuthorities()
                , loginUser.hasRootRole()
                , loginUser.hasAdminRole());
        if (count > 0) {
            list = baseMapper.listQuery(loginUser.getTenantId()
                    , loginUser.getUserId()
                    , ROLE.ROOT
                    , ROLE.ADMIN
                    , MyBatisUtil.humpToUnderline(params)
                    , Optional.ofNullable(current).map(c -> (c - 1) * size).orElse(null)
                    , size
                    , pageOrder
                    , dto
                    , loginUser.checkAuthorities()
                    , loginUser.hasRootRole()
                    , loginUser.hasAdminRole());
        }
        result.accept(count, list);
    }

    @Override
    public boolean save(SysRole entity) {
        try {
            PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                    , () -> sysParamsObtainService
                    , Collections.singletonList(entity));
            return super.save(entity);
        } finally {
            // 添加角色的同时有赋予角色权限，这里需要初始化到缓存里面去
            sysRolePermissionService.iniConfig();
        }
    }


    @Override
    public boolean saveBatch(Collection<SysRole> entityList) {
        PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                , () -> sysParamsObtainService
                , entityList);
        return super.saveBatch(entityList);
    }

    @Override
    public boolean updateById(SysRole entity) {
        try {
            PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                    , () -> sysParamsObtainService
                    , getBaseMapper().selectList(Wrappers.<SysRole>lambdaQuery()
                            .select(SysRole::getId, SysRole::getCode, SysRole::getCreateUser)
                            .eq(SysRole::getId, entity.getId())));
            return super.updateById(entity);
        } finally {
            // 修改角色的同时有赋予角色权限，这里需要初始化到缓存里面去
            sysRolePermissionService.iniConfig();
        }
    }

    @Override
    public boolean updateBatchById(Collection<SysRole> entityList) {
        PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                , () -> sysParamsObtainService
                , getBaseMapper().selectList(Wrappers.<SysRole>lambdaQuery()
                        .select(SysRole::getId, SysRole::getCode, SysRole::getCreateUser)
                        .in(SysRole::getId, entityList.stream().map(SysRole::getId).collect(Collectors.toSet()))));
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean removeById(Serializable id) {
        return removeByIds(Collections.singletonList(id));
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        PermissionsValidityCheckTool.checkOperateRole(() -> securityUtil
                , () -> sysParamsObtainService
                , getBaseMapper().selectList(Wrappers.<SysRole>lambdaQuery()
                        .select(SysRole::getId, SysRole::getCode, SysRole::getCreateUser)
                        .in(SysRole::getId, idList)));
        boolean deleted = true;
        try {
            return super.removeByIds((idList = checkRemoveCondition(idList)));
        } catch (Exception ex) {
            deleted = false;
            throw ex;
        } finally {
            if (deleted && idList.size() > 0) {
                // 删除角色的同时删除权限与角色的关联
                //sysRolePermissionService.remove(Wrappers.<SysRolePermission>lambdaQuery().in(SysRolePermission::getRoleId, idList));
                // 删除角色的同时删除角色与用户的关联
                //sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getRoleId, idList));
                // 删除权限与角色的关联之后初始化权限角色配置
                sysRolePermissionService.iniConfig();
            }
        }
    }

    public <E> Collection<E> checkRemoveCondition(Collection<E> idList) {
        return idList.stream()
                // 排除掉 ROOT 角色
                .filter(id -> !id.equals(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.ROLE_ROOT_ID))))
                .peek(id -> {
                    // 查询出，如果还有用户在关联使用这个角色，就得提示，说不能删除，因为还有人在使用这个角色
                    if (sysUserRoleMapper.selectCount(Wrappers.<SysUserRole>lambdaQuery()
                            .eq(SysUserRole::getRoleId, id)
                            .exists("select id from sys_user where id = sys_user_role.user_id and is_deleted = 0")) > 0) {
                        SysRole sysRole = getBaseMapper().selectById((Serializable) id);
                        throw new BaseException(ResultCode.VALIDATE_ERROR, String.format("角色[%s]还有用户在使用不能删除！", sysRole.getName()));
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<SysRole> listFilterRole(SysRole dto) {
        QueryWrapper<SysRole> queryWrapper = (QueryWrapper<SysRole>) MyBatisUtil.genQueryWrapper(dto, null);
        LambdaQueryWrapper<SysRole> lambda = queryWrapper.lambda();
        // 不能查询 root 角色
        lambda.ne(SysRole::getCode, ROLE.ROOT);
        Set<String> checkAuthorities = securityUtil.getLoginUser().checkAuthorities();
        if (checkAuthorities.stream().noneMatch(r -> r.equals(ROLE.ROOT))) {
            // 如果不是 ROOT 角色，不能查询 ADMIN 角色
            lambda.ne(SysRole::getCode, ROLE.ADMIN);
        }
        if (!checkAuthorities.contains(ROLE.ROOT) && !checkAuthorities.contains(ROLE.ADMIN)) {
            Long userId = securityUtil.getLoginUser().getUserId();
            // 如果不是 ROOT 或者 ADMIN 角色，就需要过滤只能查询出自己拥有的角色，或者自己创建的角色
            lambda.and(i -> i.eq(SysRole::getCreateUser, securityUtil.getLoginUser().getUserId())
                    .or().in(SysRole::getCode, new ArrayList<>(checkAuthorities)));
        }
        return super.list(queryWrapper);
    }

}




