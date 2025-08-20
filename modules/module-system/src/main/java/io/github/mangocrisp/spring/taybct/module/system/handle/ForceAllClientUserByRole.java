package io.github.mangocrisp.spring.taybct.module.system.handle;

import io.github.mangocrisp.spring.taybct.module.system.config.IForceAllClientUserByRole;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.Collection;

/**
 * 是否清理角色
 *
 * @author XiJieYin <br> 2023/6/19 15:17
 */
@AutoConfiguration
public class ForceAllClientUserByRole implements IForceAllClientUserByRole {

    @Override
    public void accept(String s, Collection<Long> longs) {
        // 这里默认不处理按角色掉线用户，因为角色关联的用户可能太多了
    }
}
