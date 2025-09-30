package io.github.mangocrisp.spring.taybct.module.system.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserOnline;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 针对表【sys_user_online(在线用户)】的数据库操作Service
 *
 * @author 24154
 * @see SysUserOnline
 */
public interface ISysUserOnlineService extends IBaseService<SysUserOnline> {

    /**
     * 登录选择租户
     *
     * @param tenantId 租户 id
     * @return boolean
     * @author xijieyin <br> 2022/8/16 18:48
     * @since 1.0.1
     */
    boolean chooseTenant(String tenantId);

    /**
     * version 1.0.1 <br>
     * 登录成功之后，需要选择租户才给登录进入首页，
     * 选择了租户之后，就需要再调用一下这个接口来记录是使用租户登录了。<br>
     * 选择租户的时候，会把 jti 和 tenant id 作为 kv 存入 redis，在网关
     * 做过滤的时候使用 jti 查询到 tenant id ,然后存入到 Payload 里面，然后就可以使用
     * {@code SecurityUtil#getTenantId()} 去获取这个 tenant id 了，如果没有在 redis
     * 里面获取到 tenant id，就使用默认的 租户 id
     *
     * <br>
     * <p>
     * 登录成功后记录操作
     *
     * @param dto          用户登录信息
     * @param sendLoginLog 是否发送登录日志
     * @return boolean
     */
    boolean login(JSONObject dto, boolean sendLoginLog);

    /**
     * version 1.0.1 <br>
     * 登录成功之后，需要选择租户才给登录进入首页，
     * 选择了租户之后，就需要再调用一下这个接口来记录是使用租户登录了。<br>
     * 选择租户的时候，会把 jti 和 tenant id 作为 kv 存入 redis，在网关
     * 做过滤的时候使用 jti 查询到 tenant id ,然后存入到 Payload 里面，然后就可以使用
     * {@code SecurityUtil#getTenantId()} 去获取这个 tenant id 了，如果没有在 redis
     * 里面获取到 tenant id，就使用默认的 租户 id
     *
     * <br>
     * <p>
     * 登录成功后记录操作
     *
     * @param dto 用户登录信息
     * @return boolean
     */
    default boolean login(JSONObject dto) {
        return login(dto, false);
    }

    /**
     * 登出操作
     *
     * @param dto     用户登录信息
     * @param message 被登出的原因
     * @return boolean
     */
    boolean logoff(JSONObject dto, String message);

    /**
     * 获取在线用户列表
     *
     * @return {@code List<SysUserOnline>}
     */
    List<SysUserOnline> onlineList();

    /**
     * 在线分页
     *
     * @param sqlQueryParams 分页信息
     * @return {@code <E extends IPage<SysUserOnline>> E}
     */
    IPage<SysUserOnline> onlinePage(Map<String, Object> sqlQueryParams);

    /**
     * 强制登出
     *
     * @param message  被登出的原因
     * @param clientId 客户端标识
     * @param username 用户名
     * @return 是否操作成功
     */
    boolean force(String message, String clientId, String... username);

    /**
     * 根据 jti 来删除来踢掉线
     *
     * @param jti     JWT Token id
     * @param message 被登出的原因
     * @return boolean
     */
    boolean force(String[] jti, String message);

    /**
     * 强退所有和这个客户端有相关登录操作的用户
     *
     * @param message  被登出的原因
     * @param clientId 客户端 id
     */
    void forceAllClientUsers(String message, String clientId);

    /**
     * 根据角色 id 强退相关用户登录的所有客户端
     *
     * @param message 被登出的原因
     * @param roleId  角色 id
     */
    void forceAllClientUserByRole(String message, Long... roleId);

    /**
     * 根据用户id强退所有当前用户登录的状态
     *
     * @param message 被登出的原因
     * @param userId  用户 id
     */
    void forceAllClientUserById(String message, Long... userId);

    /**
     * 强退所有当前用户登录的客户端
     *
     * @param message  被登出的原因
     * @param username 用户名
     */
    void forceAllClientUser(String username, String message);

    /**
     * 清除缓存
     *
     * @param entityList 用户集合
     */
    void clearCache(Collection<SysUser> entityList);

    /**
     * 清理超时的登录
     *
     * @author xijieyin <br> 2022/8/15 12:13
     * @since 1.0.0
     */
    void clearExpires();
}
