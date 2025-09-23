package io.github.mangocrisp.spring.taybct.single.service;

import io.github.mangocrisp.spring.taybct.single.domain.EDDemo;
import io.github.mangocrisp.spring.taybct.single.handle.*;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElement;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElementMap;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElements;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author XiJieYin <br> 2024/4/24 11:17
 */
@Service
@Slf4j
public class EDDemoServiceImpl implements IEDDemoService {
    @Override
    @EnhanceMethod
    public EDDemo add(EDDemo demo) {
        System.out.println("进方法后:" + demo.toString());
        return demo;
    }

    @Override
    @EnhanceMethod
    public List<EDDemo> addBatch(List<EDDemo> list) {
        System.out.println("进方法后:");
        list.forEach(System.out::println);
        return list;
    }

    @Override
    @EnhanceMethod
    @EnhanceElements(value = {"str1", "str2", "str3"}, parameterHandler = {
            EnFunction.class
    }, resultHandler = {
            DeFunction.class
            , DeFunction.class
    })
    public List<String> edStr(String str1, @EnhanceElement(parameterHandler = {
            EnFunction.class
            , EnFunction.class
    }, resultHandler = {
            DeFunction.class
    }) String str2, String str3) {
        log.info("进方法后:{},{},{}", str1, str2, str3);
        return Arrays.asList(str1, str2, str3);
    }

    @Override
    @EnhanceMethod
    @EnhanceElements(
            value = {"strCollection"}
            , parameterHandler = {EnFunction.class}
            , enDecryptedElements = {
            @EnhanceElement(value = "str2", parameterHandler = {EnFunction.class, EnFunction.class}),
            @EnhanceElement(value = "map", map = {@EnhanceElementMap(value = "k2")}, parameterHandler = {EnFunction.class, EnFunction.class, EnFunction.class}),
    })
    public Map<String, String> edMap(@EnhanceElement(parameterHandler = {EnFunction.class}) String str1
            , String str2
            , List<String> strCollection
            , Map<String, String> map
            , EDDemo edDemo) {
        log.info("进方法后:");
        log.info("str1:{}", str1);
        log.info("str2:{}", str2);
        log.info("strCollection:");
        strCollection.forEach(log::info);
        log.info("map:");
        map.forEach((k, v) -> log.info("{}->{}", k, v));
        log.info("edDemo:");
        log.info(edDemo.toString());
        return map;
    }

    @Override
    @EnhanceMethod
    @EnhanceElements(
            enDecryptedElements =
            @EnhanceElement(value = "map", map = {
                    @EnhanceElementMap(value = "k1"
                            , parameterHandler = {EnFunction3.class, EnFunction3.class}),
                    @EnhanceElementMap(value = {"k2", "k3"}, parameterHandler = {EnFunction2.class, EnFunction2.class}),
                    @EnhanceElementMap(value = {"k4", "k5"}, parameterHandler = {EnFunction3.class, EnFunction3.class}),
            }
                    , parameterHandler = {EnFunction2.class, EnFunction2.class}
            )
            , parameterHandler = {EnFunction.class, EnFunction.class}
            , map = {@EnhanceElementMap("k1"), @EnhanceElementMap(value = {"k2", "k3"}, resultHandler = {DeFunction2.class, DeFunction2.class}),}
            , resultHandler = {DeFunction2.class, DeFunction2.class})
    public Map<String, String> edMapStr(Map<String, String> map) {
        log.info("进方法后:");
        map.forEach((k, v) -> log.info("{}->{}", k, v));
        return map;
    }

}
