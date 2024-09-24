package io.github.mangocrisp.spring.taybct.demo;

//import io.github.mangocrisp.spring.taybct.tool.rsa.config.RSAProperties;
//import io.github.mangocrisp.spring.taybct.tool.rsa.util.RSACoder;

import com.alibaba.druid.filter.config.ConfigTools;
import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author XiJieYin <br> 2024/5/17 19:39
 */
@SpringBootTest
public class DBTest {

//    @Test
//    public void test1(){
//        String password = "password";
//        RSAProperties properties = new RSAProperties();
//        RSACoder.ini(properties);
//        String publicKey = RSACoder.getPublicKey();
//        System.out.println(publicKey);
//        String enPassword = RSACoder.encryptBase64StringByPublicKey(password);
//        System.out.println(enPassword);
//        String dePassword = RSACoder.decryptBase64StringByPrivateKey(enPassword);
//        System.out.println(dePassword);
//    }

    @SneakyThrows
    @Test
    public void test2() {
        String password = "password";
        String[] arr = ConfigTools.genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
        System.out.println("password:" + ConfigTools.encrypt(password));
    }

    @SneakyThrows
    @Test
    public void test3() {
        String password = "password";
        String encrypt = CryptoUtils.encrypt(password);
        System.out.println(encrypt);
    }
}
