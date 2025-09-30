package io.github.mangocrisp.spring.taybct.common.constants;

import io.github.mangocrisp.spring.taybct.tool.core.constant.PropertiesPrefixConstants;

/**
 * 服务常量
 *
 * @author xijieyin <br> 2022/8/5 18:19
 * @since 1.0.0
 */
public interface ServeConstants {

    /**
     * 基础服务前缀
     */
    String BASE_PREFIX = "taybct-";
    /**
     * 鉴权
     */
    String AUTH = BASE_PREFIX + "auth";
    /**
     * 网关
     */
    String GATEWAY = BASE_PREFIX + "gateway";
    /**
     * 系统模块
     */
    String SYSTEM = BASE_PREFIX + "system";
    /**
     * 在线文档模块
     */
    String ONLINE_DOC = BASE_PREFIX + "online-doc";
    /**
     * 任务调度模块
     */
    String SCHEDULING = BASE_PREFIX + "scheduling";
    /**
     * Admin 管理模块前缀
     */
    String ADMIN_PREFIX = BASE_PREFIX + "admin-";
    /**
     * 日志管理
     */
    String ADMIN_LOG = ADMIN_PREFIX + "log";
    /**
     * 文件管理
     */
    String ADMIN_FILE = ADMIN_PREFIX + "file";
    /**
     * 单机鉴权
     */
    String ADMIN_SECURITY = ADMIN_PREFIX + "security";
    /**
     * 请求代理
     */
    String ADMIN_PROXY = ADMIN_PREFIX + "proxy";
    /**
     * 运维模块
     */
    String ADMIN_OAM = ADMIN_PREFIX + "oam";

    /**
     * 服务相关
     */
    String SERVE = PropertiesPrefixConstants.TAYBCT + ".serve";

    /**
     * 请求前缀常量
     * 这种配置类主要是为了解决，兼容多种类型的框架对于请求前缀的要求不同的问题，例如：<br>
     * 网关请求的是需要按过滤器，过滤不同的前缀去注册中心找对应的微服务，如果是单体架构的话，就没有说要用
     * 过滤器这种来请求不同的服务，因为他只有一个服务，就是他自己，这个时候，这个请求前缀就可以实现了网关
     * 过滤器的功能，一切是因为只有一个前端的情况。
     * <br>
     * —— 文件管理
     *
     * @since 1.0.0
     */
    String CONTEXT_PATH_ADMIN_FILE = "${" + SERVE + "." + ServeConstants.ADMIN_FILE + ".context-path}";
    /**
     * —— 日志管理
     */
    String CONTEXT_PATH_ADMIN_LOG = "${" + SERVE + "." + ServeConstants.ADMIN_LOG + ".context-path}";
    /**
     * —— 系统管理
     */
    String CONTEXT_PATH_SYSTEM = "${" + SERVE + "." + ServeConstants.SYSTEM + ".context-path}";
    /**
     * —— 在线文档
     */
    String CONTEXT_PATH_ONLINE_DOC = "${" + SERVE + "." + ServeConstants.ONLINE_DOC + ".context-path}";
    /**
     * —— 任务调度
     */
    String CONTEXT_PATH_SCHEDULING = "${" + SERVE + "." + ServeConstants.SCHEDULING + ".context-path}";
    /**
     * —— 鉴权管理
     */
    String CONTEXT_PATH_AUTH = "${" + SERVE + "." + ServeConstants.AUTH + ".context-path}";

    /*
     * 服务 id 常量
     * @since 1.0.1
     */
    /**
     * 运维管理
     */
    String SERVE_ID_ADMIN_OAM = "${" + SERVE + "." + ServeConstants.ADMIN_OAM + ".service-id}";
    /**
     * 日志管理
     */
    String SERVE_ID_ADMIN_LOG = "${" + SERVE + "." + ServeConstants.ADMIN_LOG + ".service-id}";
    /**
     * 系统管理
     */
    String SERVE_ID_SYSTEM = "${" + SERVE + "." + ServeConstants.SYSTEM + ".service-id}";
    /**
     * 在线文档
     */
    String SERVE_ID_ONLINE_DOC = "${" + SERVE + "." + ServeConstants.ONLINE_DOC + ".service-id}";
    /**
     * 鉴权
     */
    String SERVE_ID_AUTH = "${" + SERVE + "." + ServeConstants.AUTH + ".service-id}";
    /**
     * 代理模块
     */
    String SERVE_ID_ADMIN_PROXY = "${" + SERVE + "." + ServeConstants.ADMIN_PROXY + ".service-id}";

}
