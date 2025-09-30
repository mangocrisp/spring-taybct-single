package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysDictMapper;
import io.github.mangocrisp.spring.taybct.common.constants.CacheConstants;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 针对表【sys_dict(字典)】的数据库操作Service实现
 *
 * @author 24154
 */
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict>
        implements ISysDictService {

    @Autowired(required = false)
    protected RedisTemplate<Object, Object> redisTemplate;

    /**
     * 一个一个的获取存缓存
     *
     * @param dictCode 字典类型
     * @return 字典
     */
    @Cacheable(cacheNames = CacheConstants.System.DICT, key = "#dictCode")
    public List<SysDict> cache(String dictCode) {
        return getBaseMapper().selectList(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getDictCode, dictCode)
                .eq(SysDict::getStatus, 1)
                .orderByAsc(SysDict::getSort));
    }

    @Override
    public boolean save(SysDict entity) {
        boolean save = super.save(entity);
        if (save) {
            cleanCache(getDictCodes(Collections.singletonList(entity.getId())));
        }
        return save;
    }

    @Override
    public boolean updateById(SysDict entity) {
        cleanCache(getDictCodes(Collections.singletonList(entity.getId())));
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<SysDict> entityList) {
        cleanCache(getDictCodes(entityList.stream().map(SysDict::getId).collect(Collectors.toList())));
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean removeById(Serializable id) {
        cleanCache(getDictCodes(Collections.singletonList(id)));
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        cleanCache(getDictCodes(idList));
        return super.removeByIds(idList);
    }

    private Set<String> getDictCodes(Collection<?> idList) {
        return getBaseMapper().selectList(Wrappers.<SysDict>lambdaQuery()
                        .select(SysDict::getDictCode)
                        .in(SysDict::getId, idList))
                .stream().map(SysDict::getDictCode)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean cleanCache(Set<String> dictCodes) {
        if (CollectionUtil.isNotEmpty(dictCodes)) {
            redisTemplate.delete(dictCodes.stream()
                    .map(dictCode -> String.format("%s::%s", CacheConstants.System.DICT, dictCode))
                    .collect(Collectors.toSet()));
            return true;
        }
        Set<Object> keys = redisTemplate.keys(CacheConstants.System.DICT + "*");
        if (CollectionUtil.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        return true;
    }
}




