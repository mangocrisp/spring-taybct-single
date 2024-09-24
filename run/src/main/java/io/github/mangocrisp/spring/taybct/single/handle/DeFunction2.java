package io.github.mangocrisp.spring.taybct.single.handle;

import java.util.function.Function;

/**
 * @author XiJieYin <br> 2024/4/24 11:49
 */
public class DeFunction2 implements Function<String, String> {
    @Override
    public String apply(String s) {
        return s.replace("加密", "") + "解密2";
    }
}
