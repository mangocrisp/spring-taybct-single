package io.github.mangocrisp.spring.taybct.module.system.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 程序关闭监听器
 *
 * @author xijieyin <br> 2022/8/5 21:44
 * @since 1.0.0
 */
@Slf4j
public class CloseListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        log.info("\033[40;32;0m" + "程序停止！" + "\033[0m");
    }
}
