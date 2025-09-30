package io.github.mangocrisp.spring.taybct.auth.controller;

import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.rsa.RSACoder;
import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM2Coder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秘钥相关<br>
 * 用于获取公钥，加/解密密文等操作，主要用于做加密登录操作，因为客户端 id 和 密钥这些东西，默认是通过
 * base64 加密传输，这样是很不安全的，所以可以多做一层非对称加密来解决这种安全问题
 *
 * @author xijieyin <br> 2022/8/5 10:30
 * @since 1.0.0
 */
@RestController
@AutoConfiguration
@AllArgsConstructor
public class KeyPairController {

    /**
     * 获取公钥
     *
     * @author xijieyin <br> 2022/8/5 10:32
     * @since 1.0.0
     */
    @GetMapping("/rsa/publicKey")
    public R<?> getKey(HttpServletResponse response) {
        return R.data(RSACoder.getPublicKey());
    }

    /**
     * 获取sm2公钥
     *
     * @since 2.4.0
     */
    @SneakyThrows
    @GetMapping("/sm2/publicKey")
    public R<?> getSM2Key(HttpServletResponse response) {
        return R.data(SM2Coder.getWebPublicKey());
    }

}