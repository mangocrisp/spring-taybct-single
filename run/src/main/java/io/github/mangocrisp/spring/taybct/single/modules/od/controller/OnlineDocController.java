package io.github.mangocrisp.spring.taybct.single.modules.od.controller;

import io.github.mangocrisp.spring.taybct.module.od.controller.OnlineDocControllerRegister;
import io.github.mangocrisp.spring.taybct.module.od.service.IOnlineDocService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 在线文档控制器
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/20 04:54
 */
@RestController
public class OnlineDocController extends OnlineDocControllerRegister {

    public OnlineDocController(IOnlineDocService onlineDocService) {
        super(onlineDocService);
    }

}
