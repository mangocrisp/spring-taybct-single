package io.github.mangocrisp.spring.taybct.api.system.constant;

/**
 * <pre>
 * 消息通知主题
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/8/22 18:09
 */
public interface NoticeTopic {

    /**
     * 系统通知
     */
    String SYSTEM_NOTICE = "system_notice";
    /**
     * 个人通知
     */
    String PERSONAL_NOTICE = "personal_notice";
    /**
     * 个人消息
     */
    String PERSONAL_MESSAGE = "personal_message";
    /**
     * 待办
     */
    String TODO_MESSAGE = "todo";

}
