package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysParams;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysParamsMapper;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysParamsService;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.CacheTimeOut;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 针对表【sys_params(系统参数)】的数据库操作Service实现
 *
 * @author 24154
 */
public class SysParamsServiceImpl extends BaseServiceImpl<SysParamsMapper, SysParams>
        implements ISysParamsService {

    @Autowired(required = false)
    protected RedisTemplate<String, String> redisTemplate;

    /**
     * 一个一个的获取存缓存
     *
     * @param paramsKey 键
     * @return 参数
     */
    @CacheTimeOut(cacheName = CacheConstants.System.PARAMS, key = "#paramsKey")
    public String cache(String paramsKey) {
        Assert.hasLength(paramsKey, "参数键不能为空");
        SysParams sysParams = getBaseMapper().selectOne(Wrappers.<SysParams>lambdaQuery().eq(SysParams::getParamsKey, paramsKey));
        if (sysParams != null) {
            return sysParams.getParamsVal();
        }
        return null;
    }

    @Override
    public boolean save(SysParams entity) {
        checkKeyCharts(entity.getParamsKey());
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<SysParams> entityList) {
        entityList.stream().map(SysParams::getParamsKey).forEach(this::checkKeyCharts);
        return super.saveBatch(entityList);
    }

    @Override
    public boolean updateById(SysParams entity) {
        checkKeyCharts(entity.getParamsKey());
        boolean b = super.updateById(entity);
        if (b) {
            cleanCache(Set.of(entity.getParamsKey()));
        }
        return b;
    }

    @Override
    public boolean updateBatchById(Collection<SysParams> entityList) {
        Set<String> paramsKeySet = entityList.stream().map(SysParams::getParamsKey).peek(this::checkKeyCharts).collect(Collectors.toSet());
        boolean b = super.updateBatchById(entityList);
        if (b) {
            cleanCache(paramsKeySet);
        }
        return b;
    }

    @Override
    public boolean removeById(Serializable id) {
        cleanCache(getParamsKeySet(Collections.singletonList(id)));
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        cleanCache(getParamsKeySet(idList));
        return super.removeByIds(idList);
    }

    private Set<String> getParamsKeySet(Collection<?> idList) {
        return getBaseMapper().selectList(Wrappers.<SysParams>lambdaQuery()
                        .select(SysParams::getParamsKey)
                        .in(SysParams::getId, idList))
                .stream().map(SysParams::getParamsKey).collect(Collectors.toSet());
    }


    @Override
    public boolean cleanCache(Set<String> paramsKeySet) {
        if (CollectionUtil.isNotEmpty(paramsKeySet)) {
            redisTemplate.delete(paramsKeySet.stream()
                    .map(paramsKey -> String.format("%s::%s", CacheConstants.System.PARAMS, paramsKey))
                    .collect(Collectors.toSet()));
            return true;
        }
        Set<String> keys = redisTemplate.keys(CacheConstants.System.PARAMS + "*");
        if (CollectionUtil.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        return true;
    }

    /**
     * 检查特殊字符
     *
     * @param key 参数键
     */
    private void checkKeyCharts(String key) {
        Assert.hasLength(key, "参数 Key 不能为空");
        Assert.isTrue(!ReUtil.contains("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。 ，、？]", key)
                , "参数 Key 不能包含特殊字符");
    }

}




