package io.github.mangocrisp.spring.taybct.module.system.service;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;

import java.util.List;
import java.util.Set;

/**
 * 针对表【sys_dict(字典)】的数据库操作Service
 *
 * @author 24154
 * @see SysDict
 */
public interface ISysDictService extends IBaseService<SysDict> {

    /**
     * 获取字典并存缓存
     *
     * @param dictCode 字典类型（多个）
     * @return 字典（集合）
     */
    List<SysDict> cache(String dictCode);

    /**
     * 清除缓存
     *
     * @param dictCodes 需要清除缓存的字典 code
     */
    boolean cleanCache(Set<String> dictCodes);

}
