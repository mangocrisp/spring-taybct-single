package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.convert.Convert;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysOauth2ClientMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysUserMapper;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.handle.AuthServeClientHandle;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysOauth2ClientService;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.util.ObjectUtil;
import io.github.mangocrisp.spring.taybct.tool.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Types;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 针对表【sys_oauth2_client(客户端)】的数据库操作Service实现
 *
 * @author 24154
 */
public class SysOauth2ClientServiceImpl extends BaseServiceImpl<SysOauth2ClientMapper, SysOauth2Client>
        implements ISysOauth2ClientService {

    @Autowired(required = false)
    protected RedisTemplate<Object, Object> redisTemplate;

    @Autowired(required = false)
    protected SysUserMapper sysUserMapper;

    @Autowired(required = false)
    protected ISysUserOnlineService sysUserOnlineService;
    @Autowired(required = false)
    protected JdbcTemplate jdbcTemplate;
    @Nullable
    @Autowired(required = false)
    protected AuthServeClientHandle authServeClientHandle;
    private static final String TABLE_NAME = "oauth2_registered_client";
    private static final String PK_FILTER = "id = ?";
    private static final String DELETE_REGISTERED_CLIENT_SQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PK_FILTER;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysOauth2Client entity) {
        if (StringUtil.isEmpty(entity.getClientName())) {
            entity.setClientName(entity.getClientId());
        }
        boolean success = super.save(entity);
        if (success) {
            saveAuthServeClient(entity);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(SysOauth2Client entity) {
        checkClientInfo(Collections.singletonList(entity));
        clearCache(Collections.singletonList(entity));
        if (StringUtil.isEmpty(entity.getClientName())) {
            entity.setClientName(entity.getClientId());
        }
        boolean success = super.updateById(entity);
        if (success) {
            saveAuthServeClient(entity);
        }
        return success;
    }

    @Override
    public boolean updateBatchById(Collection<SysOauth2Client> entityList) {
        checkClientInfo(entityList);
        // 如果对客户端有做修改，需要更刷新 redis 的缓存
        clearCache(entityList);
        return super.updateBatchById(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        SysOauth2Client sysOauth2Client = getBaseMapper().selectById(id);
        if (ObjectUtil.isNotEmpty(sysOauth2Client)) {
            // 客户端删除后，需要清除缓存
            String clientId = sysOauth2Client.getClientId();
            clearCache(Collections.singletonList(sysOauth2Client));
            sysUserOnlineService.forceAllClientUsers("您的客户端信息发生变更，请重新登录！", clientId);
        }
        SysOauth2Client client = getById(id);
        boolean success = super.removeById(id);
        if (success) {
            deleteAuthServeClient(client);
        }
        return success;
    }


    @Override
    public boolean removeByIds(Collection<?> idList) {
        idList.forEach(id -> removeById((Serializable) id));
        return true;
    }

    /**
     * 清除缓存
     */
    private void clearCache(Collection<SysOauth2Client> entityList) {
        redisTemplate.delete(entityList.stream()
                .map(SysOauth2Client::getClientId)
                .map(clientId -> String.format("%s::%s", CacheConstants.OAuth.CLIENT, clientId))
                .collect(Collectors.toSet()));
    }

    /**
     * 鉴权服务器保存客户端
     *
     * @param client 客户端信息
     */
    private void saveAuthServeClient(SysOauth2Client client) {
        if (authServeClientHandle != null) {
            if (client.getId() != null) {
                client = getById(client.getId());
            }
            Map<String, Object> resultMap = getAuthServeClientId(client);
            if (resultMap != null && resultMap.get("id") != null) {
                client.setId(Convert.toLong(resultMap.get("id")));
            }
            authServeClientHandle.save(client);
        }
    }

    /**
     * 获取客户端信息
     *
     * @param client 客户端
     * @return 客户端信息
     */
    private Map<String, Object> getAuthServeClientId(SysOauth2Client client) {
        try {
            return jdbcTemplate.queryForObject(String.format("select id from %s where client_id = ?", TABLE_NAME)
                    , new Object[]{client.getClientId()}
                    , new int[]{Types.VARCHAR}
                    , new ColumnMapRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // 没找到结果就没找到嘛,报什么错呀...
            return null;
        }
    }

    /**
     * 检查客户端信息
     *
     * @param entityList 客户端
     */
    private void checkClientInfo(Collection<SysOauth2Client> entityList) {
        if (entityList.stream().anyMatch(client -> client.getClientId() != null && !Optional.ofNullable(getBaseMapper()
                        .selectById(client.getId()))
                .orElseThrow(() -> new BaseException("客户端查询失败！"))
                .getClientId()
                .equals(client.getClientId()))) {
            // 如果数据库查询回来的客户端信息的客户端 id 与将要修改的客户端 id 对不上
            throw new BaseException("客户端 id 不允许修改");
        }
    }

    /**
     * 删除客户端
     *
     * @param client 客户端信息
     * @return 影响的行数
     */
    private int deleteAuthServeClient(SysOauth2Client client) {
        Map<String, Object> resultMap = getAuthServeClientId(client);
        if (resultMap != null && resultMap.get("id") != null) {
            client.setId(Convert.toLong(resultMap.get("id")));
        }
        return jdbcTemplate.update(DELETE_REGISTERED_CLIENT_SQL, Convert.toStr(client.getId()));
    }
}




