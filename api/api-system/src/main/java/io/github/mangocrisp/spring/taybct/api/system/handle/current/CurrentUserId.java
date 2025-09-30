package io.github.mangocrisp.spring.taybct.api.system.handle.current;

import io.github.mangocrisp.spring.taybct.tool.core.bean.ILoginUser;
import io.github.mangocrisp.spring.taybct.tool.core.bean.ISecurityUtil;
import io.github.mangocrisp.spring.taybct.tool.core.config.TableFieldDefaultHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * <pre>
 * 当前登录用户的用户 id
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 00:27
 */
@RequiredArgsConstructor
public class CurrentUserId implements TableFieldDefaultHandler<Long> {

    @Nullable
    final ISecurityUtil securityUtil;

    @Override
    public Long get(Object entity) {
        ILoginUser loginUser = securityUtil.getLoginUser();
        return loginUser.getUserId();
    }

}
