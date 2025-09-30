package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserRole;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserTenant;
import io.github.mangocrisp.spring.taybct.api.system.dto.OAuth2UserDTO;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysUserQueryDTO;
import io.github.mangocrisp.spring.taybct.api.system.dto.UserPassCheckDTO;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserRoleMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserTenantMapper;
import io.github.mangocrisp.spring.taybct.api.system.tool.PermissionsValidityCheckTool;
import io.github.mangocrisp.spring.taybct.api.system.vo.UserInfoVO;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.dict.SysDict;
import io.github.mangocrisp.spring.taybct.common.message.sysfile.FileLinkDTO;
import io.github.mangocrisp.spring.taybct.common.message.sysfile.FileSendDTO;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserService;
import io.github.mangocrisp.spring.taybct.module.system.util.RedisPageUtil;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.core.support.IEncryptedPassable;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.ObjectUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM3Coder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author xijieyin
 */
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser>
        implements ISysUserService {

    @Autowired(required = false)
    protected RedisTemplate<Object, Object> redisTemplate;

    @Autowired(required = false)
    protected RedisPageUtil redisPageUtil;

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;

    @Autowired(required = false)
    protected SysUserRoleMapper sysUserRoleMapper;

    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;

    @Autowired(required = false)
    protected SysUserTenantMapper sysUserTenantMapper;

    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;

    @Autowired(required = false)
    protected IEncryptedPassable encryptedPassable;

    @Autowired(required = false)
    protected IMessageSendService messageSendService;

    @Override
    public List<SysUser> customizeList(Map<String, Object> params) {
        AtomicReference<List<SysUser>> result = new AtomicReference<>(Collections.emptyList());
        IPage<SysUser> page = customizeQueryPage(params);
        SysUserQueryDTO dto = JSONObject.parseObject(JSONObject.toJSONString(params), SysUserQueryDTO.class);
        getSysUsers(dto, params, page.getCurrent(), page.getSize(), (total, sysUsers) -> {
            mergeQueryExpansion(sysUsers);
            result.set(sysUsers);
        });
        return result.get();
    }

    @Override
    public IPage<SysUser> customizePage(Map<String, Object> params) {
        IPage<SysUser> page = customizeQueryPage(params);
        SysUserQueryDTO dto = JSONObject.parseObject(JSONObject.toJSONString(params), SysUserQueryDTO.class);
        getSysUsers(dto, params, page.getCurrent(), page.getSize(), (total, sysUsers) -> {
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
    private void getSysUsers(SysUserQueryDTO dto, Map<String, Object> sqlQueryParams, Long current, Long size, BiConsumer<Long, List<SysUser>> result) {
        String pageOrder = MyBatisUtil.getPageOrder(sqlQueryParams);
        SysUser convert = Convert.convert(SysUser.class, dto);
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(convert));
        params.remove("expansion");
        params.remove("filterByUser");
        params.remove("filterByRole");
        params.remove("isCreateByLoginUser");
        params.remove("includeParents");
        params.remove("includeChildren");
        params.remove("deptId");
        ILoginUser loginUser = securityUtil.getLoginUser();
        List<SysUser> list = Collections.emptyList();
        long count = baseMapper.countQuery(loginUser.getTenantId()
                , loginUser.getUserId()
                , Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID))
                , MyBatisUtil.humpToUnderline(params)
                , dto
                , loginUser.checkAuthorities()
                , loginUser.checkRoot());
        if (count > 0) {
            list = baseMapper.listQuery(loginUser.getTenantId()
                    , loginUser.getUserId()
                    , Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID))
                    , MyBatisUtil.humpToUnderline(params)
                    , Optional.ofNullable(current).map(c -> (c - 1) * size).orElse(null)
                    , size
                    , pageOrder
                    , dto
                    , loginUser.checkAuthorities()
                    , loginUser.checkRoot());
        }
        result.accept(count, list);
    }

    /**
     * 新增用户
     *
     * @param entity 实体对象
     */
    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysUser entity) {
        PermissionsValidityCheckTool.checkOperateUser(() -> securityUtil, () -> sysParamsObtainService, Collections.singletonList(entity));
        // 这里不能修改 root 用户
        if (entity.getId() != null && Objects.equals(entity.getId(), Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID)))) {
            return true;
        }
        ILoginUser loginUser = securityUtil.getLoginUser();
        LambdaQueryWrapper<SysUser> eq = Wrappers.lambdaQuery();
        if (StringUtil.isNotEmpty(entity.getUsername())) {
            eq.or().eq(SysUser::getUsername, entity.getUsername());
            Assert.isTrue(getBaseMapper().selectCount(eq) == 0, "用户名已存在");
        }
        // 新增的时候，如果用户密码为空，就给个默认的用户密码
        if (StringUtil.isEmpty(entity.getPassword())) {
            entity.setPassword(SM3Coder.getSM3().digestHex(sysParamsObtainService.get(CacheConstants.Params.USER_PASSWD)));
        } else {
            // 这里解密出明文来,如果不能解密成功会报错
            // 然后 SM3 加密一次之后 对称 加密
            entity.setPassword(SM3Coder.getSM3().digestHex(encryptedPassable.apply(entity.getPassword())));
        }
        userPassableCheck(entity);
        return super.save(entity) &&
                (sysUserTenantMapper.exists(Wrappers.<SysUserTenant>lambdaQuery()
                        .eq(SysUserTenant::getUserId, entity.getId())
                        .eq(SysUserTenant::getTenantId, loginUser.getTenantId())) ||
                        sysUserTenantMapper.insert(new SysUserTenant(entity.getId(), loginUser.getTenantId())) > 0);
    }

    /**
     * 检查用户有效性
     *
     * @param entity 用户
     */
    private void userPassableCheck(SysUser entity) {
        LambdaQueryWrapper<SysUser> eq;
        if (StringUtil.isNotEmpty(entity.getUsername())) {
            eq = Wrappers.lambdaQuery();
            if (entity.getId() != null) {
                eq.ne(SysUser::getId, entity.getId());
            } else {
                eq.or();
            }
            eq.eq(SysUser::getUsername, entity.getUsername());
            Assert.isTrue(!getBaseMapper().exists(eq), "用户名已存在");
        }
        if (StringUtil.isNotEmpty(entity.getPhone())) {
            eq = Wrappers.lambdaQuery();
            if (entity.getId() != null) {
                eq.ne(SysUser::getId, entity.getId());
            } else {
                eq.or();
            }
            eq.eq(SysUser::getPhone, entity.getPhone());
            Assert.isTrue(!getBaseMapper().exists(eq), "手机号已存在");
        }
        // 因为邮件可以不为空，所以，这里做非空判断
        if (StringUtil.isNotEmpty(entity.getEmail())) {
            eq = Wrappers.lambdaQuery();
            if (entity.getId() != null) {
                eq.ne(SysUser::getId, entity.getId());
            } else {
                eq.or();
            }
            eq.eq(SysUser::getEmail, entity.getEmail());
            Assert.isTrue(!getBaseMapper().exists(eq), "邮箱已存在");
        }
    }

    @Override
    public boolean userPassableCheck(UserPassCheckDTO entity) {
        SysUser sysUser = new SysUser();
        sysUser.setId(entity.getId());
        sysUser.setUsername(entity.getUsername());
        sysUser.setPhone(entity.getPhone());
        sysUser.setEmail(entity.getEmail());
        userPassableCheck(sysUser);
        return true;
    }

    @Override
    public boolean updateMyInfo(SysUser entity) {
        userPassableCheck(entity);
        if (entity.getStatus() != null && entity.getStatus() == 0) {
            // 只有修改了用户状态才给强制下线
            sysUserOnlineService.forceAllClientUser(entity.getUsername(), "用户信息发生改变，被强制登出！");
        }
        // 更新用户之后需要刷新用户作为登录的缓存
        sysUserOnlineService.clearCache(Collections.singletonList(entity));

        // 登录用户信息
        Long userId = securityUtil.getLoginUser().getUserId();
        String avatar = entity.getAvatar();
        String existedAvatar = Optional.ofNullable(getOne(Wrappers.<SysUser>lambdaQuery()
                        .select(SysUser::getId, SysUser::getAvatar)
                        .eq(SysUser::getId, userId)))
                .map(SysUser::getAvatar)
                .orElse(null);

        if (StringUtil.isNotBlank(avatar) && (StringUtil.isBlank(existedAvatar) || !existedAvatar.equals(avatar))) {
            // 如果修改了头像
            if (messageSendService != null) {
                // 更新头像做关联了，原来的头像要删除
                List<FileLinkDTO> updateList = new ArrayList<>();
                updateList.add(FileLinkDTO.builder()
                        .path(avatar)
                        .updateUser(userId)
                        .fileType(avatar.substring(avatar.lastIndexOf(".")))
                        .linked(1)
                        .linkedTable("sys_user")
                        .linkedTableId(entity.getId())
                        .build());
                if (StringUtil.isNotEmpty(existedAvatar)) {
                    updateList.add(FileLinkDTO.builder()
                            .path(existedAvatar)
                            .updateUser(userId)
                            .linked(0)
                            .build());
                }
                updateLinkedAvatar(updateList);
            }
        }

        return super.updateById(entity);
    }

    /**
     * 更新关联的头像的文件
     *
     * @param updateList 更新列表
     */
    @Async
    public void updateLinkedAvatar(List<FileLinkDTO> updateList) {
        messageSendService.send(new FileSendDTO(updateList));
    }

    @Override
    public OAuth2UserDTO getUserByFiled(String field, String value) {
        return getBaseMapper().getUserByFiled(field, value);
    }

    @Override
    public UserInfoVO getUserInfoByUserId(Long userId) {
        UserInfoVO userInfo = getBaseMapper().getUserInfoByUserId(userId);
        userInfo.setPasswdRequire(Long.valueOf(Optional.ofNullable(sysParamsObtainService.get(CacheConstants.Params.PASSWD_REQUIRE)).orElse("0")));
        return userInfo;
    }

    @SneakyThrows
    @Override
    public boolean modifyPasswd(String old, String now) {
        // 这两个密码只是经过 RSA 加密，里面没有 SM3 加密的
        String oldDecrypt = SM3Coder.getSM3().digestHex(encryptedPassable.apply(old));
        String nowDecrypt = SM3Coder.getSM3().digestHex(encryptedPassable.apply(now));
        Long userId = securityUtil.getLoginUser().getUserId();
        String dbUserPassword = getBaseMapper().selectById(userId).getPassword();
        Assert.isTrue(dbUserPassword.equals(oldDecrypt), "原密码不正确！");
        Assert.isTrue(!oldDecrypt.equals(nowDecrypt), "新密码不能和原密码相同！");
        return resetPasswd(nowDecrypt, userId);
    }

    @SneakyThrows
    @Override
    public boolean resetPasswd(Long... userId) {
        PermissionsValidityCheckTool.checkOperateUser(() -> securityUtil, () -> sysParamsObtainService
                , getBaseMapper().selectList(Wrappers.<SysUser>lambdaQuery()
                        .select(SysUser::getId, SysUser::getCreateUser)
                        .in(SysUser::getId, Arrays.asList(userId))));
        return resetPasswd(SM3Coder.getSM3().digestHex(sysParamsObtainService.get(CacheConstants.Params.USER_PASSWD)), userId);
    }

    @Override
    public boolean resetPasswd(String password, Long... userId) {
        SysUser modifyDto = new SysUser();
        modifyDto.setPassword(password);
        modifyDto.setPasswdTime(LocalDateTime.now());
        if ((getBaseMapper().update(modifyDto, Wrappers.<SysUser>lambdaUpdate()
                .in(SysUser::getId, Arrays.asList(userId))) > 0)) {
            sysUserOnlineService.forceAllClientUserById("密码重置，强制登出！", userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(SysUser entity) {
        PermissionsValidityCheckTool.checkOperateUser(() -> securityUtil, () -> sysParamsObtainService
                , getBaseMapper().selectList(Wrappers.<SysUser>lambdaQuery()
                        .select(SysUser::getId, SysUser::getCreateUser)
                        .eq(SysUser::getId, entity.getId())));
        userPassableCheck(entity);
        if (entity.getStatus() != null && entity.getStatus() == 0) {
            // 只有修改了用户状态才给强制下线
            sysUserOnlineService.forceAllClientUser(entity.getUsername(), "用户信息发生改变，被强制登出！");
        }
        // 更新用户之后需要刷新用户作为登录的缓存
        sysUserOnlineService.clearCache(Collections.singletonList(entity));
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<SysUser> entityList) {
        PermissionsValidityCheckTool.checkOperateUser(() -> securityUtil, () -> sysParamsObtainService
                , getBaseMapper().selectList(Wrappers.<SysUser>lambdaQuery()
                        .select(SysUser::getId, SysUser::getCreateUser)
                        .in(SysUser::getId, entityList.stream().map(SysUser::getId).collect(Collectors.toSet()))));
        sysUserOnlineService.clearCache(entityList);
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean removeById(Serializable id) {
        SysUser sysUser = getBaseMapper().selectById(id);
        PermissionsValidityCheckTool.checkOperateUser(() -> securityUtil, () -> sysParamsObtainService, Collections.singletonList(sysUser));
        if (ObjectUtil.isNotEmpty(sysUser)) {
            // 用户删除后，需要清除缓存
            sysUserOnlineService.clearCache(Collections.singletonList(sysUser));
            sysUserOnlineService.forceAllClientUser(sysUser.getUsername(), "用户被删除，被强制登出！");
        }
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        idList.forEach(id -> removeById((Serializable) id));
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2UserDTO addWechatUser(JSONObject wechatUserInfo) {
        // 创建用户
        SysUser sysUser = createDefault(sysParamsObtainService.get(CacheConstants.Params.USER_PASSWD));
        // 保存用的用户信息
        JSONObject user = JSONObject.parseObject(JSONObject.toJSONString(sysUser));
        user.put("nickname", wechatUserInfo.getString("nickname"));
        // 性别，微信这里没有使用国家标准
        Integer sex = wechatUserInfo.getInteger("sex");
        if (sex.equals(0)) {
            sex = SysDict.Gender.MALE.getIntValue();
        } else {
            sex = SysDict.Gender.FEMALE.getIntValue();
        }
        user.put("gender", sex);
        // 头像
        user.put("avatar", wechatUserInfo.getString("headimgurl"));
        // openid
        user.put("openid", wechatUserInfo.getString("openid"));
        int r = getBaseMapper().addWechatUser(user);
        if (r == 0) {
            throw new RuntimeException("创建用户失败！");
        }
        // 用户和角色关系
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROLE_ID)));
        int i = sysUserRoleMapper.insert(sysUserRole);
        if (i == 0) {
            throw new BaseException("用户分配默认角色【" + sysParamsObtainService.get(CacheConstants.Params.USER_ROLE) + "】失败！");
        }
        return getUserByFiled("username", sysUser.getUsername());
    }

    /**
     * 创建默认用户
     *
     * @param password 密码
     * @return 默认用户
     */
    @SneakyThrows
    private SysUser createDefault(String password) {
        SysUser sysUser = new SysUser();
        sysUser.setId(IdWorker.getId());
        sysUser.setUsername(UUID.fastUUID().toString(true));
        sysUser.setPassword(SM3Coder.getSM3().digestHex(password));
        sysUser.setCreateTime(LocalDateTime.now());
        return sysUser;
    }

}




