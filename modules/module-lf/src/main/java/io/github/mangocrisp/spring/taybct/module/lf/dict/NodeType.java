package io.github.mangocrisp.spring.taybct.module.lf.dict;

import io.github.mangocrisp.spring.taybct.common.dict.AbstractSysDict;
import lombok.Getter;

import java.io.Serial;

/**
 * 节点类型
 *
 * @author XiJieYin <br> 2023/7/11 14:31
 */
@Getter
public final class NodeType extends AbstractSysDict {

    @Serial
    private static final long serialVersionUID = -6446684472971026548L;

    /**
     * 开始节点
     */
    public static final NodeType CUSTOM_NODE_START = new NodeType("custom-node-start", "开始节点");
    /**
     * 用户任务
     */
    public static final NodeType CUSTOM_NODE_USER = new NodeType("custom-node-user", "用户任务");
    /**
     * 系统任务
     */
    public static final NodeType CUSTOM_NODE_SERVICE = new NodeType("custom-node-service", "系统任务");
    /**
     * 条件判断
     */
    public static final NodeType CUSTOM_NODE_JUDGMENT = new NodeType("custom-node-judgment", "条件判断");
    /**
     * 结束节点
     */
    public static final NodeType CUSTOM_NODE_END = new NodeType("custom-node-end", "结束节点");
    /**
     * 分组
     */
    public static final NodeType CUSTOM_GROUP = new NodeType("custom-group", "分组");

    public NodeType(String key, String val) {
        super(key, val);
    }

}
