package io.github.mangocrisp.spring.taybct.single.handle;

import java.util.function.Function;

/**
 * @author XiJieYin <br> 2024/4/24 11:48
 */
public class EnFunction3 implements Function<Object, Object> {
    @Override
    public Object apply(Object s) {
        return ((String) s).replace("解密", "") + "加密3";
    }
}
