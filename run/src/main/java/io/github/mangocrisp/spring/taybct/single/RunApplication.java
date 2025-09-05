package io.github.mangocrisp.spring.taybct.single;

import io.github.mangocrisp.spring.taybct.tool.core.config.ApplicationConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author xijieyin
 * @createTime 2022/7/28 15:41
 * @description
 */
// 表示通过 aop 框架暴露该代理对象, AopContext 能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 开启线程异步执行
@EnableAsync
// 自动加载应用配置
@Import({ApplicationConfig.class})
@SpringBootApplication
@EnableCaching
@MapperScan({"io.github.mangocrisp.spring.taybct.**.mapper"})
public class RunApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
    }

}
