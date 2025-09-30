package io.github.mangocrisp.spring.taybct.api.system.handle.current;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysDept;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.handle.MyBatisExtraParamsHandle;
import io.github.mangocrisp.spring.taybct.tool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * <pre>
 * 已登录用户的部门获取器
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 00:53
 */
@Slf4j
@RequiredArgsConstructor
public class LoginUserDeptHandler implements MyBatisExtraParamsHandle {
    /**
     * 常量
     */
    public final static String loginUserDeptConstant = "_login_user_dept_";

    final Supplier<SysDept> getUserDeptRealInfoVOSupplier;

    /**
     * 加个锁，防止重复获取
     */
    private final static AtomicBoolean lock = new AtomicBoolean(false);

    @Override
    public Map<String, Object> apply(MappedStatement mappedStatement, Map<String, Object> stringObjectMap) {
        stringObjectMap.put(loginUserDeptConstant, null);
        SysDept longUserDept = null;
        if (!LoginUserDeptHandler.lock.get()) {
            LoginUserDeptHandler.lock.set(true);
            try {
                longUserDept = getUserDeptRealInfoVOSupplier.get();
                LoginUserDeptHandler.lock.set(false);
            } catch (Exception e) {
                log.trace("获取部门失败！", e);
                LoginUserDeptHandler.lock.set(false);
            }
        }
        if (ObjectUtil.isNotEmpty(longUserDept)) {
            stringObjectMap.put(loginUserDeptConstant, longUserDept);
        } else {
            stringObjectMap.put(loginUserDeptConstant, null);
        }
        return stringObjectMap;
    }

}
