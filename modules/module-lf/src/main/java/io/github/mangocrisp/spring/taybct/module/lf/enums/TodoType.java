package io.github.mangocrisp.spring.taybct.module.lf.enums;

import java.io.Serializable;

/**
 * <pre>
 * 任务待办
 * </pre>
 *
 * @param code        编码
 * @param codeInt     编码（int）
 * @param description 描述
 * @author XiJieYin
 * @since 2025/9/4 14:47
 */
public record TodoType(String code, int codeInt, String description) implements Serializable {

    /**
     * 编码
     */
    public interface Code {
        /**
         * 任务待办
         */
        String TASK = "1";
        /**
         * 任务待办
         */
        int TASK_INT = 1;
        /**
         * 抄送待办
         */
        String CC = "2";
        /**
         * 抄送待办
         */
        int CC_INT = 2;
    }

    /**
     * 任务待办
     */
    public static final TodoType TASK = new TodoType(Code.TASK, Code.TASK_INT, "任务待办");
    /**
     * 抄送待办
     */
    public static final TodoType CC = new TodoType(Code.CC, Code.CC_INT, "抄送待办");

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        TodoType that = (TodoType) obj;
        return this.codeInt() == that.codeInt;
    }

    @Override
    public int hashCode() {
        return this.code().hashCode();
    }

}
