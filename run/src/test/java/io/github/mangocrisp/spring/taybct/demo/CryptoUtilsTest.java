package io.github.mangocrisp.spring.taybct.demo;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;
import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSACoder;
import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSAProperties;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Properties;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM3Coder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigInteger;

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
    public void headerAuthorization() {
        String clientId = "taybct_pc";
        String secret = "e10adc3949ba59abbe56e057f20f883e";
        System.out.printf("Basic %s", Base64.encode(String.format("%s:%s", clientId, secret)));
    }

    @SneakyThrows
    @Test
    public void testSM2() {
        String webPrivateKey = "4f485ecdadbd6526530834e203017c15cddfbeb0665f3fb08cf5ad26d6ddb3b6";
        String webPublicKey = "04204acd81fe5d374f31812905681600981054215ab4f4670c9d488a54033fd1fdbfce801af66672403c52ad6dbe734e6539bd1b959ae35449fec8ce29914121df";
        BCECPublicKey ecPublicKeyByPublicKeyHex = getECPublicKeyByPublicKeyHex(webPublicKey);
        BCECPrivateKey ecPrivateKeyByPrivateKeyHex = getECPrivateKeyByPrivateKeyHex(webPrivateKey);
        SM2 sm2 = new SM2();
        sm2.setPublicKey(ecPublicKeyByPublicKeyHex);
        sm2.setPrivateKey(ecPrivateKeyByPrivateKeyHex);
        String s = sm2.encryptBase64("123456", KeyType.PublicKey);
        System.out.println(s);
        String s1 = sm2.decryptStr(s, KeyType.PrivateKey);
        System.out.println(s1);

        // 使用 SM2Coder 解密
        SM2Coder.ini(new SM2Properties());
        String s2 = SM2Coder.decryptWebData(s);
        System.out.println(s2);
    }

    public static BCECPublicKey getECPublicKeyByPublicKeyHex(String pubKeyHex) {
        if (pubKeyHex.length() > 128) {
            pubKeyHex = pubKeyHex.substring(pubKeyHex.length() - 128);
        }
        String stringX = pubKeyHex.substring(0, 64);
        String stringY = pubKeyHex.substring(stringX.length());
        BigInteger x = new BigInteger(stringX, 16);
        BigInteger y = new BigInteger(stringY, 16);
        X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
        ECParameterSpec ecDomainParameters = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecDomainParameters);
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    public static BCECPrivateKey getECPrivateKeyByPrivateKeyHex(String privateKeyHex) {
        BigInteger d = new BigInteger(privateKeyHex, 16);
        X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
        ECParameterSpec ecDomainParameters = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecDomainParameters);
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    @Test
    public void testRSA() {
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDQyuzo2lENtE1gjeMFmAc325dGknCjF4SBNrDraS9scxD905GPp63O/8mPPlqC/y0sPuuWJWAx0VzwBBChTo2qJ3nBFArXwB2fa/ISmZgA983EIMBLefmm8aRp0zD0nl4z9zzftftIot7SnhZKMj+cdFNWLxp17qTslfJ5/cgWBKm0eQIrc9J1BNE+yrlM5KRGq1eb5+EzDyV2mJhBOyQw9EgNRPKPb7zHN/Vlq/M9H+CqDh6ubZNTbyiARepBz59UMZiRX+YSVSsCwtO6PVhOq1tYzXphljtk4/x2ayQOEUFIiCqU1YkfEruXeP1SQ3WN1FYXncfwWIw0arsIGnCPAgMBAAECggEAVIntjcSjr+hSsicRBjlNpuS/JiByAd8qVAU4ckDnJkKa3SNWWzOZ8OfpjRnCnSrD+BfL3MHqZpFuT3Jd52o+eug76Jz8XOoZNG0JGVXeUXoEcwaNxaGwH1PU7dJIjqs22lSfbbcp9PmZPe8reERLNEZ3Xbx5uslzNqXqBVHnu13RPXi28IvI4lAhZ3cCRy9CjdcDI2x9RpvL8+QjaawJvQdywfX3sLfzAIoe6tLzeOwplC/60lD9/i3qW/g+NDfdhzOqX6IJTQAHZHynZzmXrO0fnJsdtmRo04Rfy3O7NR3PNt22XQnSUIGK/Gyeb5DiUby0vqfOLyYBAG6TLa4njQKBgQDW29U6hGtJks/mCKx5Yr8T0g+3so1W2WaHMLz6/3pXzB3XUgOv06lCfPydoWZCieFM3/YKlsDyWIWH8xowbdOGV4aNo6Pr4JMn1gkM1MfiRTbO6Wq6u6K6m0zwk2pgTOCKIj7HTKzIZSGJuk0J2p1HcK50gtauWi/ceibThhztEwKBgQD4xb2nvHaGp3Qd/qLxdumQgLXCg0NZ0VleYECexLywgKFS00KsLamCU49JJr1/xP6UtlNrfaAm1QI2m+p7uk/tQSJd0k60waf14pvSmY/2rkPAWwDX28N05wqxOL1Z/vQANUFiDx3pF8tBmWlqk+/tJszNNQMFlnwxfwFJn+7KFQKBgGv0num9GS8pfh5F42tGS13JIS6an1uw9kwdGAdBkJZGsAO9aK7Suev3YvJM41Wxc6utqDFf1+isw8MwSctlk2+f0LKgMz/UYfwVFg0FaL1vK3/BVmgm2TfLylfiqgNi/TtKw6JeCz7RWY2bNxQpbPc1TeKqzvVhyUSNd662ZxhTAoGAAOwixuNxARDP4miWTBTnysxfYChCcKvRDAOJWc1a8cCYWIlF/wNwVHZg2qGVJEsjsoN2jbh6hopVpsQvO2Q2dae/K9/iwNLS/5tmfoJpQT3mLGlp+GLtN5q/tGaEdTf3yaRo6KcrsjiPXV/nPyjrQ9LYAY7HwxaKwVg6Nf6s0P0CgYAY+0FtECdpus8gBt+c6712kh5wF5YskE5zyXXLpQ4buOSi2yivl9LFUbyKKuZCyXC7fqyOIkwLiBhM7oRdN6e+72SYqQCWz8IQ6MVWeiGAgeBqTNREEK7PxXiLVanXSnRuH73iIbHfLjwJwIIu/pxYnXC0i1B+00+5OeIP8s31hw==";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Mrs6NpRDbRNYI3jBZgHN9uXRpJwoxeEgTaw62kvbHMQ/dORj6etzv/Jjz5agv8tLD7rliVgMdFc8AQQoU6Nqid5wRQK18Adn2vyEpmYAPfNxCDAS3n5pvGkadMw9J5eM/c837X7SKLe0p4WSjI/nHRTVi8ade6k7JXyef3IFgSptHkCK3PSdQTRPsq5TOSkRqtXm+fhMw8ldpiYQTskMPRIDUTyj2+8xzf1ZavzPR/gqg4erm2TU28ogEXqQc+fVDGYkV/mElUrAsLTuj1YTqtbWM16YZY7ZOP8dmskDhFBSIgqlNWJHxK7l3j9UkN1jdRWF53H8FiMNGq7CBpwjwIDAQAB";
        RSA rsa = new RSA(privateKey, publicKey);
        String s = rsa.encryptBase64("123456", KeyType.PublicKey);
        System.out.println(s);
        String s1 = rsa.decryptStr(s, KeyType.PrivateKey);
        System.out.println(s1);

        // 使用 RSACoder 解密
        RSACoder.ini(new RSAProperties());
        String s2 = RSACoder.decryptBase64StringByPrivateKey(s);
        System.out.println(s2);
    }
}
