package io.github.mangocrisp.spring.taybct.api.system.tool;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysRole;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.ROLE;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 权限有效性检查工具
 *
 * @author XiJieYin <br> 2023/6/1 16:54
 */
public class PermissionsValidityCheckTool {

    /**
     * 检查操作的角色有效性
     *
     * @param roleList                       操作的角色列表
     * @param securityUtilSupplier           提供获取当前用户的工具
     * @param sysParamsObtainServiceSupplier 提供获取系统参数
     */
    public static void checkOperateRole(Supplier<ISecurityUtil> securityUtilSupplier
            , Supplier<ISysParamsObtainService> sysParamsObtainServiceSupplier
            , Collection<SysRole> roleList) {
        checkOperateRole(securityUtilSupplier, sysParamsObtainServiceSupplier, roleList, false);
    }

    /**
     * 检查操作的角色有效性
     *
     * @param roleList                       操作的角色列表
     * @param securityUtilSupplier           提供获取当前用户的工具
     * @param sysParamsObtainServiceSupplier 提供获取系统参数
     * @param allowGrant                     是否允许把角色授权给其他用户，可以查询出自己创建的和自己拥有的角色，用来分配给用户的
     */
    public static void checkOperateRole(Supplier<ISecurityUtil> securityUtilSupplier
            , Supplier<ISysParamsObtainService> sysParamsObtainServiceSupplier
            , Collection<SysRole> roleList
            , boolean allowGrant) {
        ISecurityUtil securityUtil = securityUtilSupplier.get();
        ILoginUser loginUser = securityUtil.getLoginUser();
        ISysParamsObtainService sysParamsObtainService = sysParamsObtainServiceSupplier.get();
        Set<String> checkAuthorities = loginUser.checkAuthorities();
        for (SysRole sysRole : roleList) {
            if (sysRole.getId() != null) {
                if (Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.ROLE_ROOT_ID)).equals(sysRole.getId())) {
                    throw new BaseException("不允许操作 ROOT 角色", HttpStatus.BAD_REQUEST);
                }
            }
            if (sysRole.getCode() != null) {
                if (ROLE.ROOT.equalsIgnoreCase(sysRole.getCode())) {
                    throw new BaseException("不允许操作 ROOT 角色", HttpStatus.BAD_REQUEST);
                }
                // 如果不是 ROOT 不能修改 ADMIN
                if (ROLE.ADMIN.equals(sysRole.getCode())) {
                    if (checkAuthorities.stream().noneMatch(r -> r.equals(ROLE.ROOT))) {
                        throw new BaseException("不允许操作 ADMIN 角色", HttpStatus.BAD_REQUEST);
                    }
                }
            }
            // 不能操作自己的角色
            if (!allowGrant && checkAuthorities.stream().anyMatch(r -> r.equals(sysRole.getCode()))) {
                throw new BaseException(String.format("不允许操作自己的角色[%s]", sysRole.getCode()), HttpStatus.BAD_REQUEST);
            }
            if (!checkRoleIsAdmin(checkAuthorities)) {
                // 如果不是 ROOT 也不是 ADMIN 不允许操作不是自己创建的角色
                if (!allowGrant
                        && sysRole.getCreateUser() != null
                        && !sysRole.getCreateUser().equals(loginUser.getUserId())) {
                    throw new BaseException(String.format("如果角色不是 ROOT 也不是 ADMIN 不允许操作不是自己创建的角色[%s]", sysRole.getCode()), HttpStatus.BAD_REQUEST);
                }
            }
        }
    }

    /**
     * 检查操作的用户有效性
     *
     * @param list                           操作的用户列表
     * @param securityUtilSupplier           提供获取当前用户的工具
     * @param sysParamsObtainServiceSupplier 提供获取系统参数
     */
    public static void checkOperateUser(Supplier<ISecurityUtil> securityUtilSupplier
            , Supplier<ISysParamsObtainService> sysParamsObtainServiceSupplier
            , Collection<SysUser> list) {
        ISecurityUtil securityUtil = securityUtilSupplier.get();
        ILoginUser loginUser = securityUtil.getLoginUser();
        ISysParamsObtainService sysParamsObtainService = sysParamsObtainServiceSupplier.get();
        Set<String> checkAuthorities = loginUser.checkAuthorities();
        for (SysUser sysUser : list) {
            if (sysUser.getId() != null) {
                if (Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID)).equals(sysUser.getId())) {
                    throw new BaseException("不允许操作 ROOT 用户", HttpStatus.BAD_REQUEST);
                }
                if (sysUser.getStatus() != null && sysUser.getStatus() == 0) {
                    if (loginUser.getUserId().equals(sysUser.getId())) {
                        throw new BaseException("用户自己不能禁用自己！", HttpStatus.BAD_REQUEST);
                    }
                }
            }
            if (sysUser.getCreateUser() != null) {
                if (!checkRoleIsAdmin(checkAuthorities)) {
                    // 如果不是 ROOT 也不是 ADMIN 不允许操作不是自己创建的用户
                    if (sysUser.getCreateUser() != null && !sysUser.getCreateUser().equals(loginUser.getUserId())) {
                        throw new BaseException("如果角色不是 ROOT 也不是 ADMIN 不允许操作不是自己创建的用户", HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }
    }

    /**
     * 检查权限是否是管理员权限
     *
     * @param checkAuthorities 角色列表
     * @return boolean
     */
    public static boolean checkRoleIsAdmin(Set<String> checkAuthorities) {
        return checkAuthorities.stream().anyMatch(r -> r.equals(ROLE.ROOT) || r.equals(ROLE.ADMIN));
    }

    /**
     * 检查只允许管理员级别的角色操作
     *
     * @param securityUtilSupplier 登录用户提供
     */
    public static void checkOperateOnlineAdmin(Supplier<ISecurityUtil> securityUtilSupplier) {
        ISecurityUtil securityUtil = securityUtilSupplier.get();
        ILoginUser loginUser = securityUtil.getLoginUser();
        if (!checkRoleIsAdmin(loginUser.checkAuthorities())) {
            // 如果不是 ROOT 也不是 ADMIN 不允许操作不是自己创建的角色
            throw new BaseException(String.format("该功能只允许管理员[%s,%s]操作", ROLE.ROOT, ROLE.ADMIN));
        }
    }

    /**
     * 检查只允许超级管理员级别的角色操作
     *
     * @param securityUtilSupplier 登录用户提供
     */
    public static void checkOperateOnlineRoot(Supplier<ISecurityUtil> securityUtilSupplier) {
        ISecurityUtil securityUtil = securityUtilSupplier.get();
        ILoginUser loginUser = securityUtil.getLoginUser();
        if (loginUser.checkAuthorities().stream().noneMatch(r -> r.equals(ROLE.ROOT))) {
            // 如果不是 ROOT 也不是 ADMIN 不允许操作不是自己创建的角色
            throw new BaseException(String.format("该功能只允许管理员[%s]操作", ROLE.ROOT));
        }
    }

}
