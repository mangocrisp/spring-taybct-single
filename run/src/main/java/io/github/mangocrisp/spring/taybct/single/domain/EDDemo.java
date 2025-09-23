package io.github.mangocrisp.spring.taybct.single.domain;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.single.handle.DeFunction;
import io.github.mangocrisp.spring.taybct.single.handle.EnFunction;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElement;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElementIgnore;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElementMap;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElements;
import lombok.Data;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author XiJieYin <br> 2024/4/24 10:54
 */
@Data
@EnhanceElements(value = {"name", "address"}, parameterHandler = {
        EnFunction.class
        , EnFunction.class
}, resultHandler = {
        DeFunction.class
})
@ToString
public class EDDemo implements Serializable {

    @Serial
    private static final long serialVersionUID = 4004316763926767567L;

    private String id;

    private String name;

    @EnhanceElement(parameterHandler = {
            EnFunction.class
    }, resultHandler = {
            DeFunction.class
    })
    private String idNumber;

    @EnhanceElement(parameterHandler = {
            EnFunction.class
    })
    private String phone;

    @EnhanceElement(resultHandler = {
            DeFunction.class
    })
    private String realName;

    @EnhanceElement(parameterHandler = {
            EnFunction.class
    }, resultHandler = {
            DeFunction.class
            , DeFunction.class
    })
    private String address;

    private EDDemo children;

    @EnhanceElementIgnore
    private EDDemo childrenIgnore;

    @EnhanceElement(map = {@EnhanceElementMap("ek3")}
            , parameterHandler = {
            EnFunction.class
            , EnFunction.class
    }, resultHandler = {
            DeFunction.class
            , DeFunction.class
    })
    private Map<String, String> map;

    @EnhanceElement(map = {@EnhanceElementMap("jk2")}
            , parameterHandler = {
            EnFunction.class
            , EnFunction.class
    }, resultHandler = {
            DeFunction.class
    })
    private JSONObject jsonObject;

    @EnhanceElement(parameterHandler = {
            EnFunction.class
            , EnFunction.class
    }, resultHandler = {
            DeFunction.class
            , DeFunction.class
    })
    @EnhanceElementIgnore
    private Set<String> set;

    @EnhanceElement(parameterHandler = {
            EnFunction.class
            , EnFunction.class
    }, resultHandler = {
            DeFunction.class
            , DeFunction.class
    })
    private JSONArray jsonArray;

}
