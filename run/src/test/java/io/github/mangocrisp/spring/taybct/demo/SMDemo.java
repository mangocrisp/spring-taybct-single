package io.github.mangocrisp.spring.taybct.demo;

import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM3Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author XiJieYin <br> 2024/6/13 17:50
 */
@SpringBootTest
public class SMDemo {

    @SneakyThrows
    @Test
    public void sm2Key() {
        SM2Coder.genSM2CACert("sm2", "taybct", 7776000
                , certificateBuilder -> {
                });
    }

    @SneakyThrows
    @Test
    public void sm3Key() {
        SM3Coder.genSM3SecretKey();
    }

    @SneakyThrows
    @Test
    public void sm4Key() {
        SM4Coder.genSM4SecretKey();
    }
}
