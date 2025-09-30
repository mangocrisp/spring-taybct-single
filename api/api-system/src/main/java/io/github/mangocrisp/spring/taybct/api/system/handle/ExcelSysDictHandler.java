package io.github.mangocrisp.spring.taybct.api.system.handle;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysDict;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <pre>
 * EasyPOI 字典翻译
 * 你可以在 Spring 容器中注册这个 bean，然后就可以在 EasyPOI 中使用
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 00:53
 */
@RequiredArgsConstructor
public class ExcelSysDictHandler implements IExcelDictHandler {

    final Function<String, List<SysDict>> dictFinder;

    @Override
    public List<Map> getList(String dict) {
        List<SysDict> cache = dictFinder.apply(dict);
        if (CollectionUtil.isEmpty(cache)) {
            return IExcelDictHandler.super.getList(dict);
        }
        return new ArrayList<>(cache.stream().map(JSONObject::from).toList());
    }

    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        List<SysDict> cache = dictFinder.apply(dict);
        String str = Convert.toStr(value, "");
        if (CollectionUtil.isEmpty(cache)) {
            return str;
        }
        return cache.stream().filter(c -> c.getDictKey().equals(str)).map(SysDict::getDictVal).findFirst().orElse("");
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        List<SysDict> cache = dictFinder.apply(dict);
        String str = Convert.toStr(value, "");
        if (CollectionUtil.isEmpty(cache)) {
            return str;
        }
        return cache.stream().filter(c -> c.getDictVal().equals(str)).map(SysDict::getDictKey).findFirst().orElse("");
    }
}
