package io.github.mangocrisp.spring.taybct.module.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDictType;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysDictMapper;
import io.github.mangocrisp.spring.taybct.api.system.mapper.SysDictTypeMapper;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictTypeService;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 针对表【sys_dict_type(字典类型)】的数据库操作Service实现
 *
 * @author 24154
 */
@Transactional(rollbackFor = Exception.class)
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType>
        implements ISysDictTypeService {

    @Autowired(required = false)
    protected SysDictMapper sysDictMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(SysDictType entity) {
        SysDictType old = getBaseMapper().selectById(entity.getId());
        // 如果是字典类型 code 发生了变化，需要同时
        if (!old.getDictCode().equals(entity.getDictCode())) {
            // 更新之前的那个 dictCode 字典的 dictCode 为现在要更新的值
            SysDict sysDict = new SysDict();
            sysDict.setDictCode(entity.getDictCode());
            sysDictMapper.update(sysDict, Wrappers.<SysDict>lambdaUpdate().eq(SysDict::getDictCode, old.getDictCode()));
        }
        return super.updateById(entity);
    }
}




