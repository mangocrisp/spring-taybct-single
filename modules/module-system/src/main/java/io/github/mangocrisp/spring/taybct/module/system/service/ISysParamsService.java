package io.github.mangocrisp.spring.taybct.module.system.service;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysParams;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

import java.util.Set;

/**
 * 针对表【sys_params(系统参数)】的数据库操作Service
 *
 * @author 24154
 * @see SysParams
 */
public interface ISysParamsService extends IBaseService<SysParams> {
    /**
     * 获取系统参数并存缓存
     *
     * @param paramsKey 系统参数键
     * @return 系统参数
     */
    String cache(String paramsKey);

    /**
     * 清除缓存
     *
     * @param paramsKeySet 需要清除缓存的字典 code
     */
    boolean cleanCache(Set<String> paramsKeySet);
}
