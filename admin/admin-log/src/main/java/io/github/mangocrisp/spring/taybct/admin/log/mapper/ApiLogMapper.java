package io.github.mangocrisp.spring.taybct.admin.log.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.mangocrisp.spring.taybct.admin.log.domain.ApiLog;

/**
 * 日志管理 Mapper 接口
 *
 * @author xijieyin <br> 2022/8/4 19:09
 * @since 1.0.0
 */
public interface ApiLogMapper extends BaseMapper<ApiLog> {
    /**
     * 因为 mybatis plus 有配置租户条件，然后如果要记录登录日志，这里插入日志的时候就不能判断当然登录用户是否有租户信息
     * 所以要重写一下 mybatis plus 的默认插入方法
     *
     * @param entity 日志实体类
     * @return int
     * @author xijieyin <br> 2022/8/4 19:09
     * @since 1.0.0
     */
    @Override
    @InterceptorIgnore(tenantLine = "true")
    int insert(ApiLog entity);

}




