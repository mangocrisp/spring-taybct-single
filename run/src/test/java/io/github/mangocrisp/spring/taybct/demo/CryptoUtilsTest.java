package io.github.mangocrisp.spring.taybct.demo;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Properties;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM3Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class CryptoUtilsTest {

    @SneakyThrows
    @Test
    public void test1() {
        String[] strings = CryptoUtils.genKeyPair(512);
        System.out.println("私钥：" + strings[0]);
        System.out.println("公钥：" + strings[1]);
        String password = CryptoUtils.encrypt(strings[0], "password");
        System.out.println(password);
        String decrypt = CryptoUtils.decrypt(strings[1], password);
        System.out.println(decrypt);
    }

    @SneakyThrows
    @Test
    public void test2() {
        SM2Properties properties = new SM2Properties();
        SM2Coder.ini(properties);
        String password = SM2Coder.encryptWebData("123456");
        System.out.println(password);
        String decrypted = SM2Coder.decryptWebData(password);
        System.out.println(decrypted);
    }

    @SneakyThrows
    @Test
    public void test3() throws IOException {
        SymmetricCrypto sm4 = SM4Coder.getSM4();
        //String url = sm4.encryptBase64("jdbc:postgresql://192.168.18.254:5432/taybct?currentSchema=public");
        String url = sm4.encryptBase64("jdbc:postgresql://127.0.0.1:5432/taybct?currentSchema=public");
        String username = sm4.encryptBase64("postgres");
        String password = sm4.encryptBase64("password");
        String driverClass = sm4.encryptBase64("org.postgresql.Driver");
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
        System.out.println(driverClass);
        System.out.println(sm4.decryptStr(url));
        System.out.println(sm4.decryptStr(username));
        System.out.println(sm4.decryptStr(password));
        System.out.println(sm4.decryptStr(driverClass));
    }

    @SneakyThrows
    @Test
    public void userPassword() {
        SM3Coder.En sm3 = new SM3Coder.En();
        SM4Coder.En sm4 = new SM4Coder.En();
        SM2Coder.ini(new SM2Properties());
        // 前端登录
        System.out.println(SM2Coder.encryptWebData("123456"));
        // 047cf8e34845d1af2e7070a13838f57ea8c3965b9fb6da7a9a86787c90cbe24b47640f0e5cb328096cc22af0df9168d15c923c6e7cdac089827c52f8b0534f52566843612af121f18a7ea0c7cd5dc88359104524cc164b3de508fae8fe5f93b74e5e8685674bba
        String sm3En = sm3.apply("123456");
        System.out.println(sm3En);
        // 9b8e883d8cd671a466c3fce1df202554819adf51ab3b39caca161e01f2049dc6
        String apply = sm4.apply(sm3En);
        // 数据库存储
        System.out.println(apply);
        // 3fdMgY4y5FGuLkP2W/hCqk0EHGpjW8PS8g380muddkNFhmmnSlorKuKsZKvR+v1Zys2lzjP0+Qf2MFrzZ+5GyHrH6EumGlRo7oE7edUMTZw=
    }

    @Test
    public void headerAuthorization(){
        String clientId = "taybct_pc";
        String secret = "e10adc3949ba59abbe56e057f20f883e";
        System.out.printf("Basic %s", Base64.encode(String.format("%s:%s", clientId, secret)));
    }
}
