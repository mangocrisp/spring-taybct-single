package io.github.mangocrisp.spring.taybct.demo;

import io.github.mangocrisp.spring.taybct.single.RunApplication;
import io.github.mangocrisp.spring.taybct.tool.core.constant.ISysParamsObtainService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author XiJieYin <br> 2023/5/18 10:41
 */
@SpringBootTest(classes = RunApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    ISysParamsObtainService sysParamsObtainService;

    @Test
    public void test1() {
        String s = sysParamsObtainService.get("user_role_id");
        Object userRoleId = sysParamsObtainService.getObject("user_role_id");
        System.out.println(userRoleId);
        System.out.println(s);
    }

}
