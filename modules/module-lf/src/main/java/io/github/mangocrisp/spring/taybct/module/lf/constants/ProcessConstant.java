package io.github.mangocrisp.spring.taybct.module.lf.constants;

/**
 * <pre>
 * 流程常量
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/3 17:45
 */
public class ProcessConstant {

    /**
     * 节点
     */
    public static final String NODES = "nodes";

    /**
     * 节点属性
     */
    public interface Nodes {
        /**
         * id
         */
        String ID = "id";
        /**
         * 属性
         */
        String PROPERTIES = "properties";
        /**
         * 节点类型
         */
        String TYPE = "type";
        /**
         * 节点文本
         */
        String TEXT = "text";
        /**
         * 值
         */
        String VALUE = "value";
        /**
         * 是否激活
         */
        String IS_ACTIVATED = "isActivated";
    }

    /**
     * 节点配置属性
     */
    public interface NodeProperties {
        /**
         * 是否会签
         */
        String IS_COUNTERSIGN = "isCountersign";
        /**
         * 是否抄送
         */
        String IS_CC = "isCC";
        /**
         * 是否自动处理
         */
        String AUTO_EXECUTE = "autoExecute";
        /**
         * 角色
         */
        String ROLES = "roles";
        /**
         * 用户id列表
         */
        String USER_ID_LIST = "userIdList";
        /**
         * 部门id列表
         */
        String DEPT_ID_LIST = "deptIdList";
        /**
         * 字段列表
         */
        String FIELDS = "fields";
    }

    public interface NodesType {
        /**
         * 开始节点
         */
        String START = "custom-node-start";
        /**
         * 结束节点
         */
        String END = "custom-node-end";
        /**
         * 用户节点
         */
        String USER = "custom-node-user";
        /**
         * 系统任务节点
         */
        String SERVICE = "custom-node-service";
        /**
         * 条件判断节点
         */
        String JUDGMENT = "custom-node-judgment";
    }

    /**
     * 线
     */
    public static final String EDGES = "edges";

    /**
     * 线属性
     */
    public interface Edges {
        /**
         * id
         */
        String ID = "id";
        /**
         * 节点类型
         */
        String TYPE = "type";
        /**
         * 目标节点id
         */
        String TARGET_NODE_ID = "targetNodeId";
        /**
         * 源节点id
         */
        String SOURCE_NODE_ID = "sourceNodeId";
        /**
         * 属性
         */
        String PROPERTIES = "properties";
        /**
         * 节点文本
         */
        String TEXT = "text";
        /**
         * 值
         */
        String VALUE = "value";
        /**
         * 是否激活
         */
        String IS_ACTIVATED = "isActivated";
    }

    /**
     * 自动处理结果
     */
    public static final String AUTO_RESULT = "auto_result";
    /**
     * 自动处理
     */
    public static final String AUTO_EXECUTE = "autoExecute";
    /**
     * 判断条件
     */
    public static final String CONDITION = "condition";
    /**
     * 主题(Bean)
     */
    public static final String TOPIC = "topic";
    /**
     * SpEL表达式
     */
    public static final String EXPRESSION = "expression";
    /**
     * 历史记录id占位符
     */
    public static final String HISTORY_ID_PLACEHOLDER = "$$historyId$$";

}
