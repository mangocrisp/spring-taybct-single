package io.github.mangocrisp.spring.taybct.single.handle;

import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.config.DataScopeCondition;
import org.springframework.stereotype.Component;

/**
 * @author XiJieYin <br> 2023/6/29 11:33
 */
@Component("dataScopeCondition")
public class UserDataScopeCondition implements DataScopeCondition {
    @Override
    public boolean test(ILoginUser securityUtil) {
        // 这里如果不是 ROOT 才需要过滤
        return securityUtil.checkRoot() == 0;
    }
}
