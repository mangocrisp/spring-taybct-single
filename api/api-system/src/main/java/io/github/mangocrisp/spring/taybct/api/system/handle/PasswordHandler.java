package io.github.mangocrisp.spring.taybct.api.system.handle;

import io.github.mangocrisp.spring.taybct.tool.core.util.sm.SM4Coder;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * <pre>
 * 密码处理器
 * </pre>
 *
 * @author XiJieYin
 * @since 2024/12/20 15:15
 */
public class PasswordHandler {
    /**
     * 加密
     */
    public static class En implements Function<Object, Object> {

        @SneakyThrows
        @Override
        public Object apply(Object s) {
            return SM4Coder.getSM4().encryptBase64((String) s, StandardCharsets.UTF_8);
        }
    }

    /**
     * 解密
     */
    public static class De implements Function<Object, Object> {

        @SneakyThrows
        @Override
        public String apply(Object s) {
            return SM4Coder.getSM4().decryptStr((String) s, StandardCharsets.UTF_8);
        }
    }
}
