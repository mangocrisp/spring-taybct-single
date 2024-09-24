package io.github.mangocrisp.spring.taybct.single.service;

import io.github.mangocrisp.spring.taybct.single.domain.EDDemo;

import java.util.List;
import java.util.Map;

/**
 * @author XiJieYin <br> 2024/4/24 10:54
 */
public interface IEDDemoService {

    EDDemo add(EDDemo demo);

    List<EDDemo> addBatch(List<EDDemo> list);

    List<String> edStr(String str1, String str2, String str3);

    Map<String, String> edMap(String str1
            , String str2, List<String> strCollection, Map<String, String> map, EDDemo edDemo);

    Map<String, String> edMapStr(Map<String, String> map);
}
