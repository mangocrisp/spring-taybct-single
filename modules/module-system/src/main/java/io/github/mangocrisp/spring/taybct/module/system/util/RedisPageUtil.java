package io.github.mangocrisp.spring.taybct.module.system.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.tool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Redis 分页工具，待完善
 *
 * @author xijieyin
 */
@AutoConfiguration
@RequiredArgsConstructor
public class RedisPageUtil {

    final RedisTemplate<Object, Object> redisTemplate;

    public <E extends IPage<JSONObject>> E getPage(E page, Set<String> queryKeySet, String cacheIndexKey, String cacheKey) {
        long total = redisTemplate.opsForZSet().count(cacheIndexKey, 0, System.currentTimeMillis());
        if (total == 0) {
            page.setTotal(0);
            page.setRecords(Collections.emptyList());
            return page;
        }
        // 总记录数
        page.setTotal(total);
        // 页面大小
        long size = page.getSize();
        // 当前页码
        long current = page.getCurrent();
        // 开始位置 = （当前页码 - 1） * 页面大小
        // 结束位置 = 开始位置 + 页面大小 - 1 (因为下标从0开始)
        long offsetStart = (current - 1) * size;
        long offsetEnd = offsetStart + size - 1;
        // 差值，默认是 10，还差 10 个
        long difference = 10;
        // 如果比总数还大就只是=总数
        if (offsetEnd > total - 1) {
            offsetEnd = total - 1;
        }
        // 在线用户的数据
        LinkedHashSet<Object> onlineUsers = new LinkedHashSet<>();
        // 脏数据索引
        Set<Object> dirtyDataIndex = new LinkedHashSet<>();
        // size - difference = 意思就是可以拿到，获取到了几个了
        try {
            // 只要查询条件里面的客户端用户名组成的 key 的
            if (CollectionUtil.isNotEmpty(queryKeySet)) {
                Set<Object> objects = queryKeySet.stream()
                        .map(key -> redisTemplate.opsForHash().get(cacheKey, key))
                        .filter(ObjectUtil::isNotEmpty)
                        .collect(Collectors.toSet());
                setRecords(page, new LinkedHashSet<>(objects));
                page.setTotal(page.getRecords().size());
                return page;
            } else {
                do {
                    // 获取到索引列表（也就是客户端和用户id列表）
                    Set<Object> index = redisTemplate.opsForZSet().range(cacheIndexKey, offsetStart, offsetEnd);
                    if (index == null || index.size() == 0) {
                        // 如果为空就直接返回了
                        setRecords(page, onlineUsers);
                        return page;
                    }
                    // 然后根据这些条件去查询 hash 里面的在线用户列表
                    for (Object i : index) {
                        // 如果有数据说明索引是正常的，就把数据加入到最终结果里面，如果没有数据说明索引脏了，需要删除，但是这个要在最后循环完了再删除
                        Object o = redisTemplate.opsForHash().get(cacheKey, i);
                        if (o == null) {
                            dirtyDataIndex.add(i);
                        } else {
                            onlineUsers.add(o);
                        }
                    }
                    // 差值，就是说，离找满 size 个数据，还差多少个，如果没满就继续找，直接到找满，或者找不到 null 再返回
                    difference = size - onlineUsers.size();
                    if (difference == 0) {
                        setRecords(page, onlineUsers);
                        return page;
                    }
                    // 如果没找满，就继续从新开始找，但是是新一轮的查找，直接翻页，然后继续查差的。
                    offsetStart = offsetEnd + 1;
                    offsetEnd = offsetStart + difference;
                } while (true);
            }
        } finally {
            // 删除脏索引
            if (dirtyDataIndex.size() > 0) {
                redisTemplate.opsForZSet().remove(cacheIndexKey, dirtyDataIndex);
            }
        }
    }

    /**
     * 设置记录
     */
    private <E extends IPage<JSONObject>> void setRecords(E page, Set<Object> onlineUsers) {
        page.setRecords(onlineUsers.stream()
                .map(o -> JSONObject.parseObject(JSONObject.toJSONString(o)))
                .collect(Collectors.toList()));
    }

}
