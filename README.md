# Spring TayBct Single

#### 介绍
Spring TayBct Single 是一个 Spring 业务组件基础集成的基础业务，对一些常用的系统管理，用户体系等基础功能做了基础的常用的简易的集成，并且提供一些业务开发过程中常用的功能模块集成，开箱即用。

#### 软件架构

- 基于 spring boot 开发，版本和 spring boot 大版本基本同步，例如 spring-taybct 3.2.x -> spring-boot 3.2.x
- 适配一些 spring 相关的基础组件的简单基础集成
- 系统管理等一些基础通用业务模块的基础简易集成
- 本项目是单体架构，后续可以使用 dubbo 变成微服务什么的

![img](img.png)

#### 安装教程

1.  mvn clean
2.  mvn install
3.  mvn deploy

#### 使用说明

1.  是运行的代码
2. **启动 VM 参数**

```bash
-Dmaven.wagon.http.ssl.insecure=true
-Dmaven.wagon.http.ssl.allowall=true
--add-opens
java.base/java.lang=ALL-UNNAMED
--add-opens
java.base/java.util=ALL-UNNAMED
--add-opens
java.base/java.nio=ALL-UNNAMED
--add-opens
java.base/sun.nio.ch=ALL-UNNAMED
--add-opens
java.base/java.lang.reflect=ALL-UNNAMED
```

3. 你可以在 modules 模块下看得到这些手动注册的

```java
@RestController
public class SysUserController extends SysUserControllerRegister {
}
```

#### 参与贡献

1.  本项目是基于 [Spring Taybct](https://github.com/mangocrisp/spring-taybct) 开发的单体架构业务项目，参与贡献就去这里面

#### 免责声明

本项目所有依赖包都是互联网能找到的，不能保证没有漏洞，源码也不能百分百保证没有 BUG，谁都不能保证，所以，如果用于生产环境，出现了什么问题，本人不负任何责任，但是可以提供友情技术帮助和支持，你可以在项目的 Issues 提出你的问题