package io.github.mangocrisp.spring.taybct.module.system.config;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * 根据角色掉线所有用户，并且，可以自定义该如何处理角色相关的用户掉线问题，这里会传掉线信息和角色 Id 集合过去
 *
 * @author XiJieYin <br> 2023/6/19 15:05
 */
@FunctionalInterface
public interface IForceAllClientUserByRole extends BiConsumer<String, Collection<Long>> {
}
