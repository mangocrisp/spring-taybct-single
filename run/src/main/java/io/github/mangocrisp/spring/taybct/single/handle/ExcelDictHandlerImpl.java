package io.github.mangocrisp.spring.taybct.single.handle;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysDictService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * EasyPOI 字典翻译
 * </pre>
 *
 * @author XiJieYin
 * @since 2024/10/11 11:25
 */
@RequiredArgsConstructor
public class ExcelDictHandlerImpl implements IExcelDictHandler {

    final ISysDictService sysDictService;

    @Override
    public List<Map> getList(String dict) {
        List<SysDict> cache = sysDictService.cache(dict);
        if (CollectionUtil.isEmpty(cache)) {
            return IExcelDictHandler.super.getList(dict);
        }
        return new ArrayList<>(cache.stream().map(JSONObject::from).toList());
    }

    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        List<SysDict> cache = sysDictService.cache(dict);
        String str = Convert.toStr(value, "");
        if (CollectionUtil.isEmpty(cache)) {
            return str;
        }
        return cache.stream().filter(c -> c.getDictKey().equals(str)).map(SysDict::getDictVal).findFirst().orElse("");
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        List<SysDict> cache = sysDictService.cache(dict);
        String str = Convert.toStr(value, "");
        if (CollectionUtil.isEmpty(cache)) {
            return str;
        }
        return cache.stream().filter(c -> c.getDictVal().equals(str)).map(SysDict::getDictKey).findFirst().orElse("");
    }
}
