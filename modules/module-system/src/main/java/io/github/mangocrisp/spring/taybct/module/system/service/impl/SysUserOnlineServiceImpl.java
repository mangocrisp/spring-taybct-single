package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.api.system.domain.*;
import io.github.mangocrisp.spring.taybct.api.system.mapper.*;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.common.constants.LoginConstants;
import io.github.mangocrisp.spring.taybct.module.system.config.IForceAllClientUserByRole;
import io.github.mangocrisp.spring.taybct.module.system.config.ILoginCacheClear;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysParamsService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AuthHeaderConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import io.github.mangocrisp.spring.taybct.tool.core.constant.OperateType;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import io.github.mangocrisp.spring.taybct.tool.core.enums.OperateStatus;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.message.IMessageSendService;
import io.github.mangocrisp.spring.taybct.tool.core.message.apilog.ApiLogDTO;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.handle.ITenantSupplier;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.util.MyBatisUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.ServletUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import io.github.mangocrisp.spring.taybct.tool.security.util.LoginUser;
import lombok.RequiredArgsConstructor;
import org.apache.http.util.Asserts;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 针对表【sys_user_online(在线用户)】的数据库操作Service实现
 *
 * @author 24154
 */
@Service
@AutoConfiguration
@RequiredArgsConstructor
public class SysUserOnlineServiceImpl extends ServiceImpl<SysUserOnlineMapper, SysUserOnline>
        implements ISysUserOnlineService {

    @Autowired(required = false)
    protected RedisTemplate<Object, Object> redisTemplate;

    @Autowired(required = false)
    protected SysOauth2ClientMapper sysOauth2ClientMapper;

    @Autowired(required = false)
    protected IMessageSendService messageSendService;

    @Autowired(required = false)
    protected SysUserMapper sysUserMapper;

    @Autowired(required = false)
    protected SysUserRoleMapper sysUserRoleMapper;

    @Autowired(required = false)
    protected ITenantSupplier tenantSupplier;

    @Autowired(required = false)
    protected SysTenantMapper sysTenantMapper;

    @Autowired(required = false)
    protected SysRoleMapper sysRoleMapper;

    @Autowired(required = false)
    protected ISysParamsService sysParamsService;

    @Autowired(required = false)
    protected ISysParamsObtainService sysParamsObtainService;

    @Autowired(required = false)
    protected ISecurityUtil securityUtil;

    @Autowired(required = false)
    protected JdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "oauth2_authorization";
    private static final String PK_FILTER = "access_token_value = ?";
    private static final String DELETE_OAUTH2_AUTHORIZATION_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PK_FILTER;

    @Nullable
    @Autowired(required = false)
    protected ILoginCacheClear loginCacheClear;

    @Nullable
    @Autowired(required = false)
    protected IForceAllClientUserByRole forceAllClientUserByRole;

    @Value("${spring.application.name}")
    private String module;
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Override
    public boolean chooseTenant(String tenantId) {
        Asserts.notBlank(tenantId, "租户 id 不能为空");
        JSONObject jwtPayload = securityUtil.getLoginUser().getPayload();
        jwtPayload.put("ip", ServletUtil.getIpAddr());
        jwtPayload.put(TokenConstants.TENANT_ID_KEY, tenantId);
        jwtPayload.put(LoginConstants.ACCESS_TOKEN, getAccessTokenValue());
        return login(jwtPayload);
    }

    /**
     * 获取到当前登录用户的 access token
     */
    private String getAccessTokenValue() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(requestAttributes -> requestAttributes.getRequest().getHeader(AuthHeaderConstants.AUTHORIZATION_KEY))
                .filter(token -> StrUtil.isNotBlank(token) && StrUtil.startWithIgnoreCase(token, AuthHeaderConstants.JWT_PREFIX))
                .map(token -> StrUtil.replaceIgnoreCase(token, AuthHeaderConstants.JWT_PREFIX, Strings.EMPTY))
                .orElse(null);
    }

    @Override
    public boolean login(JSONObject token, boolean sendLoginLog) {
        ILoginUser loginUser = new LoginUser(token, sysParamsObtainService);
        // jwt id
        String jti = loginUser.getJti();
        // 登录的 id
        String ip = token.getString("ip");
        // 客户端标识
        String clientId = loginUser.getClientId();
        // 用户 id
        Long userId = loginUser.getUserId();
        // 用户名
        String username = loginUser.getUsername();
        // 租户 id
        String tenantId = loginUser.getTenantId();
        // 认证方式,可以知道是使用什么形式登录的,比如是使用的用户名,或者是使用的手机号,或者其他
        String authMethod = token.getString(TokenConstants.AUTHENTICATION_METHOD);
        // 访问 token 的值
        String accessToken = token.getString(LoginConstants.ACCESS_TOKEN);
        // 默认租户
        String defaultTenantId = sysParamsObtainService.get(CacheConstants.Params.TENANT_ID);
        // 超级管理员 id
        Long userRootId = Long.valueOf(sysParamsObtainService.get(CacheConstants.Params.USER_ROOT_ID));
        if (tenantId == null) {
            // 如果没有开启租户模式，就使用默认的租户 id 用来做登录，或者使用的用户是 root 用户也是用默认租户
            if (!tenantSupplier.getEnable() || userId == null || userId.equals(userRootId)) {
                tenantId = defaultTenantId;
            } else {
                // 无效租户
                List<SysTenant> unusual = new ArrayList<>();
                tenantId = sysTenantMapper.listUserTenant(userId, defaultTenantId, 0)
                        // 这里找到不是默认租户的第一个租户来登录
                        .stream().filter(tenant -> !tenant.getTenantId().equals(defaultTenantId))
                        .peek(tenant -> {
                            if (!tenant.getStatus().equals((byte) 1)) {
                                unusual.add(tenant);
                            }
                        })
                        // 过滤出有角色的第一个租户，如果一个租户有角色，另一个没有，但是没有角色的排在第一个就登录不了，这是很不好的
                        .filter(tenant -> sysRoleMapper.countByUserIdTenantId(userId, tenant.getTenantId()) > 0)
                        .findFirst()
                        .map(SysTenant::getTenantId).orElse(null);
                if (tenantId == null) {
                    if (!unusual.isEmpty()) {
                        throw new BaseException(ResultCode.USER_LOGIN, "租户状态异常！登录失败！");
                    }
                    tenantId = defaultTenantId;
                }
            }
        } else {
            //  如果是租户模式，而且不是超级管理员，而且不是选择默认租户就需要判断有没有在租户里面
            if (tenantSupplier.getEnable()
                    && userId != null
                    && !userId.equals(userRootId)
                    && !tenantId.equals(defaultTenantId)) {
                if (sysTenantMapper.listUserTenant(userId, defaultTenantId, 0)
                        .stream().map(SysTenant::getTenantId)
                        .noneMatch(id -> token.getString(TokenConstants.TENANT_ID_KEY).equals(id))) {
                    throw new BaseException(ResultCode.USER_LOGIN, "租户选择失败！当前用户不属于选择的租户！");
                }
            }
        }
        // 超时时间
        Long exp = token.getLong(TokenConstants.JWT_EXP);
        // 只有租户 id 不为空，才登录在线状态
        if (StringUtil.isNotBlank(tenantId)) {

            // 是否有配置 不许同一个客户端有多个 token 即重复登录同一个客户端 就要把当前客户端已经登录的这个用户名强制下线
            if (!Optional.ofNullable(sysParamsObtainService.get(CacheConstants.Params.ALLOW_MULTIPLE_TOKEN_ONE_CLIENT))
                    .map(Boolean::parseBoolean)
                    .orElse(true)) {
                // 这里因为有选择租户的登录，所以如果是判断多处登录，也只能是用 jti 来判断
                // force(clientId, username);
                // 当前客户端类型的客户端，不能出现其他的 jti 登录
                getBaseMapper().selectList(Wrappers.<SysUserOnline>lambdaQuery()
                                .eq(SysUserOnline::getClientId, clientId)
                                .eq(SysUserOnline::getUserId, userId)
                                .ne(SysUserOnline::getJti, jti))
                        .stream()
                        .map(user -> JSONObject.parseObject(JSONObject.toJSONString(user)))
                        .forEach(user -> this.offline(user, "当前账号在其他地方登录！当前被强制登出！"));
            }

            // 在线用户
            SysUserOnline onlineUser = new SysUserOnline();
            onlineUser.setIp(ip);
            onlineUser.setJti(jti);
            onlineUser.setClientId(clientId);
            onlineUser.setAuthMethod(authMethod);
            // 将会在什么时候掉线
            onlineUser.setExp(exp * 1000);
            long expSeconds = exp - System.currentTimeMillis() / 1000;
            onlineUser.setExpTime(LocalDateTime.now().plusSeconds(expSeconds));
            onlineUser.setUserName(username);
            onlineUser.setUserId(userId);
            onlineUser.setTenantId(tenantId);

            // 从在线列表删除，一个 token 只能同时在线一个租户
            getBaseMapper().delete(Wrappers.<SysUserOnline>lambdaQuery().eq(SysUserOnline::getJti, jti));

            // 登录时间
            onlineUser.setLoginTime(LocalDateTime.now());
            onlineUser.setUpdateTime(LocalDateTime.now());

            onlineUser.setCreateTime(LocalDateTime.now());
            onlineUser.setCreateUser(userId);
            onlineUser.setUpdateUser(userId);
            onlineUser.setAccessTokenValue(accessToken);

            getBaseMapper().insert(onlineUser);

            redisTemplate.opsForValue().set(CacheConstants.OAuth.TENANT_RELATION + jti, tenantId, expSeconds, TimeUnit.SECONDS);
        }
        if (sendLoginLog) {
            // 发送登录日志，这个不管有没有登录租户，都会知道，他已经在登录了
            sendLogin(clientId, tenantId, username, module, ip);
        }
        return true;
    }

    /**
     * 发送登录日志
     *
     * @param client   客户端
     * @param tenantId 租户
     * @param username 用户名
     * @param module   模块
     */
    public void sendLogin(String client, String tenantId, String username, String module, String ip) {
        ApiLogDTO apiLog = new ApiLogDTO();

        // 创建时间
        apiLog.setCreateTime(LocalDateTime.now());
        // 设置标题
        apiLog.setTitle("登录成功");
        // 设置描述
        apiLog.setDescription("登录成功/或者刷新获取到 token");
        // 获取当前的用户
        apiLog.setUsername(username);
        // 客户端类型
        apiLog.setClient(client);
        // 租户 id
        apiLog.setTenantId(tenantId);
        // 模块
        apiLog.setModule(module);
        // 请求 主机地址
        apiLog.setIp(ip);
        // 业务类型
        apiLog.setType(OperateType.GRANT);
        // 登录成功状态码
        apiLog.setCode(OperateStatus.SUCCESS.code());
        // 设置请求方式
        try {
            apiLog.setMethod(ServletUtil.getRequest().getMethod());
        } catch (Exception e) {
            apiLog.setMethod("POST");
        }
        try {
            // 请求 的 url
            apiLog.setUrl(Objects.requireNonNull(ServletUtil.getRequest()).getRequestURL().toString());
        } catch (Exception e) {
            apiLog.setUrl("/auth/oauth/login");
        }
        messageSendService.send(apiLog);
    }

    @Override
    public boolean logoff(JSONObject token, String message) {
        // jwt id
        String jti = token.getString(TokenConstants.JWT_JTI);
        // 还有多久过期
        long exp = token.getLong(TokenConstants.JWT_EXP);
        long expiresIn = (exp - System.currentTimeMillis() / 1000);
        if (expiresIn > 0) {
            // 存入到 redis 黑名单，表示已经注销登录的 token
            redisTemplate.opsForValue().set(CacheConstants.OAuth.BLACKLIST + jti, message, expiresIn, TimeUnit.SECONDS);
            redisTemplate.delete(CacheConstants.OAuth.TENANT_RELATION + jti);
        }

        SysUserOnline sysUserOnline = getBaseMapper().selectOne(Wrappers.<SysUserOnline>lambdaQuery()
                .eq(SysUserOnline::getJti, jti));
        if (sysUserOnline != null) {
            String accessTokenValue = sysUserOnline.getAccessTokenValue();
            if (StringUtil.isNotBlank(accessTokenValue)) {
                jdbcTemplate.update(DELETE_OAUTH2_AUTHORIZATION_SQL, accessTokenValue);
            }
        }

        // 从在线列表删除
        getBaseMapper().delete(Wrappers.<SysUserOnline>lambdaQuery().eq(SysUserOnline::getJti, jti));
        return true;
    }

    @Override
    public List<SysUserOnline> onlineList() {
        // 比当前时间要小的，说明已经到期了，需要删除
        getBaseMapper().delete(Wrappers.<SysUserOnline>lambdaQuery().le(SysUserOnline::getExp, System.currentTimeMillis()));
        // 比当前时间要大的，说明还没有掉线，是在线用户
        return getBaseMapper().selectList(Wrappers.<SysUserOnline>lambdaQuery().gt(SysUserOnline::getExp, System.currentTimeMillis())
                .eq(SysUserOnline::getTenantId, securityUtil.getLoginUser().getTenantId()));
    }

    /**
     * 过滤租户
     */
    private LambdaQueryWrapper<SysUserOnline> filterTenant(QueryWrapper<SysUserOnline> queryWrapper) {
        LambdaQueryWrapper<SysUserOnline> lambda = queryWrapper.lambda();
        // 租户在线用户过滤
        lambda.eq(SysUserOnline::getTenantId, securityUtil.getLoginUser().getTenantId());
        return lambda;
    }

    @Override
    public IPage<SysUserOnline> onlinePage(Map<String, Object> sqlQueryParams) {
        IPage<SysUserOnline> page = MyBatisUtil.genPage(sqlQueryParams);
        return getBaseMapper().selectPage(page,
                filterTenant(
                        (QueryWrapper<SysUserOnline>) MyBatisUtil.genQueryWrapper(sqlQueryParams, SysUserOnline.class)));
    }

    /**
     * 下线
     */
    private void offline(JSONObject jsonObject, String message) {
        logoff(jsonObject, message);
    }

    @Override
    public boolean force(String message, String clientId, String... username) {
        getBaseMapper().selectList(Wrappers.<SysUserOnline>lambdaQuery()
                        .eq(SysUserOnline::getClientId, clientId)
                        .in(SysUserOnline::getUserName, Arrays.asList(username)))
                .stream()
                .peek(online -> online.setExp(online.getExp() / 1000))
                .map(user -> JSONObject.parseObject(JSONObject.toJSONString(user)))
                .forEach(user -> this.offline(user, message));
        return true;
    }

    @Override
    public boolean force(String[] jti, String message) {
        getBaseMapper().selectList(Wrappers.<SysUserOnline>lambdaQuery()
                        .in(SysUserOnline::getJti, Arrays.asList(jti)))
                .stream()
                .peek(online -> online.setExp(online.getExp() / 1000))
                .map(user -> JSONObject.parseObject(JSONObject.toJSONString(user)))
                .forEach(user -> this.offline(user, message));
        return true;
    }

    @Override
    public void forceAllClientUsers(String message, String clientId) {
        // 这里考虑到，用户量如果比较多，所以这里新开一个线程去删除
        // 客户端被删除后，所有在登录的客户端的状态都需要掉线
        cachedThreadPool.execute(() -> sysUserMapper.selectList(Wrappers.lambdaQuery()).stream().parallel()
                .map(SysUser::getUsername).forEach(uname -> force(message, clientId, uname)));
    }

    @Override
    public void forceAllClientUserByRole(String message, Long... roleId) {
        if (forceAllClientUserByRole != null) {
            forceAllClientUserByRole.accept(message, Arrays.asList(roleId));
            return;
        }
        // 这里考虑到，用户量如果比较多，所以这里新开一个线程去删除
        // 这里已经不用关心是否会被退掉，直接新开一个线程去处理
        cachedThreadPool.execute(() -> sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery()
                        .in(SysUserRole::getRoleId, Arrays.asList(roleId)))
                .stream().map(SysUserRole::getUserId).forEach(userId -> this.forceAllClientUserById(message, userId)));
    }

    @Override
    public void forceAllClientUserById(String message, Long... userId) {
        // 修改密码成功后，需要强退这个用户在所有登录的客户端
        List<SysUser> sysUsers = sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, Arrays.asList(userId)));
        // 清除缓存
        clearCache(sysUsers);
        // 强制掉线
        sysUsers.stream().map(SysUser::getUsername).forEach(username -> this.forceAllClientUser(username, message));
    }

    /**
     * 强退所有当前用户登录的客户端
     *
     * @param username 用户 id
     */
    @Override
    public void forceAllClientUser(String username, String message) {
        // 用户被删除后，所有在登录的客户端的状态都需要掉线
        cachedThreadPool.execute(() -> sysOauth2ClientMapper.selectList(Wrappers.lambdaQuery()).stream()
                .map(SysOauth2Client::getClientId).forEach(clientId -> force(message, clientId, username)));
    }

    /**
     * 清除缓存
     */
    @Override
    public void clearCache(Collection<SysUser> entityList) {
        if (loginCacheClear != null) {
            loginCacheClear.accept(entityList);
        }
    }

    @Override
    public void clearExpires() {
        getBaseMapper().delete(Wrappers.<SysUserOnline>lambdaQuery().lt(SysUserOnline::getExpTime, LocalDateTime.now()));
    }

}




