package io.github.mangocrisp.spring.taybct.demo;

import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSACoder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM3Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;
import org.bouncycastle.asn1.x500.X500Name;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <pre>
 *
 * </pre>
 *
 * @author XiJieYin
 * @since 2024/8/31 02:07
 */
@SpringBootTest
public class GenKeys {

    @SneakyThrows
    @Test
    public void jwtKey() {
        X500Name build = RSACoder.createStdBuilder().build();
        RSACoder.genRSACACert("jwt", "taybct", build, build, 7776000, certificateBuilder -> {
        }, new String[]{RSACoder.CER_PATH + RSACoder.PRIVATE_KEY_NAME.replace("rsa", "jwt")
                , RSACoder.CER_PATH + RSACoder.CER_NAME.replace("rsa", "jwt")
                , RSACoder.CER_PATH + RSACoder.KEY_STORE_NAME.replace("rsa", "jwt")});
    }

    @SneakyThrows
    @Test
    public void rsaKey() {
        RSACoder.genRSACACert("rsa", "taybct", 7776000, certificateBuilder -> {
        });
    }

    @SneakyThrows
    @Test
    public void rsaKeyLimited() {
        X500Name build = RSACoder.createStdBuilder().build();
        RSACoder.genRSACACert("limited", "taybct", build, build, 7776000, certificateBuilder -> {
        }, new String[]{RSACoder.CER_PATH + RSACoder.PRIVATE_KEY_NAME.replace("rsa", "limited")
                , RSACoder.CER_PATH + RSACoder.CER_NAME.replace("rsa", "limited")
                , RSACoder.CER_PATH + RSACoder.KEY_STORE_NAME.replace("rsa", "limited")});
    }

    @SneakyThrows
    @Test
    public void sm2Key() {
        SM2Coder.genSM2CACert("sm2", "taybct", 7776000, certificateBuilder -> {
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

    @Test
    public void all() {
        jwtKey();
        rsaKey();
        rsaKeyLimited();
        sm2Key();
        sm3Key();
        sm4Key();
    }
}
