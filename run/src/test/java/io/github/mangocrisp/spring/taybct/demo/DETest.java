package io.github.mangocrisp.spring.taybct.demo;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.mangocrisp.spring.taybct.single.RunApplication;
import io.github.mangocrisp.spring.taybct.single.domain.EDDemo;
import io.github.mangocrisp.spring.taybct.single.service.IEDDemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @author XiJieYin <br> 2024/4/24 11:33
 */
@SpringBootTest(classes = RunApplication.class)
@RunWith(SpringRunner.class)
public class DETest {

    @Autowired
    private IEDDemoService edDemoService;

    @Test
    public void test1() {
        EDDemo demo = new EDDemo();
        demo.setName("小明");
        demo.setAddress("小明地址");
        demo.setIdNumber("小明身份证");
        demo.setPhone("小明手机号");
        demo.setRealName("小明真实姓名");
        EDDemo children = new EDDemo();
        children.setName("小张");
        children.setAddress("小张地址");
        demo.setChildren(children);
        EDDemo childrenIgnore = new EDDemo();
        childrenIgnore.setName("小李");
        childrenIgnore.setAddress("小李地址");
        demo.setChildrenIgnore(childrenIgnore);
        EDDemo edDemo = edDemoService.add(demo);
        System.out.println("返回结果:" + edDemo);
    }

    @Test
    public void test2() {

        ArrayList<EDDemo> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            EDDemo demo = new EDDemo();
            demo.setName("小明" + i);
            demo.setAddress("小明地址" + i);
            demo.setIdNumber("小明身份证" + i);
            demo.setPhone("小明手机号" + i);
            demo.setRealName("小明真实姓名" + i);
            EDDemo children = new EDDemo();
            children.setName("小张" + i);
            children.setAddress("小张地址" + i);
            demo.setChildren(children);
            EDDemo childrenIgnore = new EDDemo();
            childrenIgnore.setName("小李" + i);
            childrenIgnore.setAddress("小李地址" + i);
            demo.setChildrenIgnore(childrenIgnore);

            list.add(demo);
        }
        List<EDDemo> edDemos = edDemoService.addBatch(list);
        System.out.println("返回结果:");
        edDemos.forEach(System.out::println);
    }

    @Test
    public void test3() {
//        List<String> s = edDemoService.edStr("a", "b", "c");
//        System.out.println("返回结果:");
//        s.forEach(System.out::println);

        HashMap<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");

        EDDemo demo = new EDDemo();
        demo.setName("小明");
        demo.setAddress("小明地址");
        demo.setIdNumber("小明身份证");
        demo.setPhone("小明手机号");
        demo.setRealName("小明真实姓名");
        EDDemo children = new EDDemo();
        children.setName("小张");
        children.setAddress("小张地址");
        demo.setChildren(children);
        EDDemo childrenIgnore = new EDDemo();
        childrenIgnore.setName("小李");
        childrenIgnore.setAddress("小李地址");
        demo.setChildrenIgnore(childrenIgnore);

        HashMap<String, String> mapEntity = new HashMap<>();
        mapEntity.put("ek1", "ev1");
        mapEntity.put("ek2", "ev2");
        mapEntity.put("ek3", "ev3");
        demo.setMap(mapEntity);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jk1", "jv1");
        jsonObject.put("jk2", "jv2");
        jsonObject.put("jk3", "jv3");

        demo.setJsonObject(jsonObject);


        JSONArray jsonArray = new JSONArray();
        jsonArray.add("jr1");
        jsonArray.add("jr2");
        jsonArray.add("jr3");

        EDDemo jd = new EDDemo();
        jd.setName("小赵");
        jsonArray.add(jd);

        jsonArray.add(new ArrayList<>(Arrays.asList("jl1", "jl2")));

        HashMap<String, String> e = new HashMap<>();
        e.put("jmk1", "jmv1");
        e.put("jmk2", "jmv2");
        e.put("jmk3", "jmv3");
        jsonArray.add(e);

        demo.setJsonArray(jsonArray);

        new ArrayList<>();

        demo.setSet(new LinkedHashSet<>(Arrays.asList("es1", "es2", "es3")));

        Map<String, String> stringStringMap = edDemoService.edMap("a"
                , "b"
                , new ArrayList<>(Arrays.asList("a", "b", "c"))
                , map
                , demo);

        System.out.println("方法调用后:");
        stringStringMap.forEach((k, v) -> System.out.printf("%s->%s\r\n", k, v));

    }

    @Test
    public void test4() {
        HashMap<String, String> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k4", "v4");
        map.put("k5", "v5");
        Map<String, String> stringStringMap = edDemoService.edMapStr(map);
        stringStringMap.forEach((k, v) -> System.out.printf("%s->%s\r\n", k, v));
    }

}
