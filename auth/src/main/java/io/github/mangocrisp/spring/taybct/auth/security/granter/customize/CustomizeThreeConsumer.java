package io.github.mangocrisp.spring.taybct.auth.security.granter.customize;

import java.util.Objects;

/**
 * 自定义的 3 个参数的消费者
 *
 * @author xijieyin <br> 2022/12/30 12:35
 */
@FunctionalInterface
public interface CustomizeThreeConsumer<U, E, A> {

    void check(U t, E u, A a);

    default CustomizeThreeConsumer<U, E, A> andThen(CustomizeThreeConsumer<? super U, ? super E, ? super A> after) {
        Objects.requireNonNull(after);
        return (v, f, b) -> {
            check(v, f, b);
            after.check(v, f, b);
        };
    }
}
