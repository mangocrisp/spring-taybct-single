package io.github.mangocrisp.spring.taybct.module.system.config;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * 登录缓存清除
 *
 * @author XiJieYin <br> 2023/6/19 14:28
 */
@FunctionalInterface
public interface ILoginCacheClear extends Consumer<Collection<SysUser>> {
}
