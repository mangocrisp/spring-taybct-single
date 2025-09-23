package io.github.mangocrisp.spring.taybct.demo;

import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * 无鉴权访问测试
 * </pre>
 *
 * @author XiJieYin
 * @since 2024/12/2 15:05
 */
@SpringBootTest
@Slf4j
public class TestNoAuthAccess {

    @SneakyThrows
    @Test
    public void test1() {
        String jsonStr = """
                {
                    "uid":"1",
                    "nbf":1732851466,
                    "grant_type":"gx_cloud",
                    "user_name":"root",
                    "scope":["all"],
                    "atm":"username",
                    "exp":1732855066,
                    "iat":1732851466,
                    "jti":"ae8109f4257847b68747c644d31c43f4",
                    "client_id":"gx_cloud_pc",
                    "authorities":["ROOT"],
                    "tni":"000000"
                }""";
        String payload = SM4Coder.getSM4().encryptBase64(jsonStr, StandardCharsets.UTF_8);
        System.out.printf("payload:%s", payload);

        //可以直接使用 SM4 加密成一个字符串，然后加入到请求头：

        //payload: xxx
    }
}
