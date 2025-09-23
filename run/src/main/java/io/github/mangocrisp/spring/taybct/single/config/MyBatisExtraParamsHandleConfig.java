package io.github.mangocrisp.spring.taybct.single.config;

import com.baomidou.mybatisplus.annotation.DbType;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.handle.DeleteLogicExtraHandle;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.interceptor.MyBatisExtraParamsInterceptor;
import io.github.mangocrisp.spring.taybct.tool.core.mybatis.util.JDBCFieldUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@AutoConfiguration
public class MyBatisExtraParamsHandleConfig {

    @Bean
    public DeleteLogicExtraHandle deleteLogicExtraHandle(MyBatisExtraParamsInterceptor myBatisExtraParamsInterceptor) {
        DeleteLogicExtraHandle deleteLogicExtraHandle = new DeleteLogicExtraHandle();

        // 类型判断
        ConcurrentHashMap<Class<?>, BiFunction<DbType, Object, String>> typeMap = new ConcurrentHashMap<>();
        typeMap.put(String.class, JDBCFieldUtil.J2D::varchar);
        typeMap.put(Long.class, JDBCFieldUtil.J2D::number);
        typeMap.put(Integer.class, JDBCFieldUtil.J2D::number);
        typeMap.put(Double.class, JDBCFieldUtil.J2D::number);
        typeMap.put(Float.class, JDBCFieldUtil.J2D::number);
        typeMap.put(Date.class, JDBCFieldUtil.J2D::dateTime);
        typeMap.put(LocalDateTime.class, JDBCFieldUtil.J2D::dateTime);
        typeMap.put(LocalDate.class, JDBCFieldUtil.J2D::dateTime);

        // TODO 类型还需要再继续添加

        deleteLogicExtraHandle.getAssembleFieldValueMap().putAll(typeMap);
        myBatisExtraParamsInterceptor.addHandler(deleteLogicExtraHandle);
        return deleteLogicExtraHandle;
    }

}
