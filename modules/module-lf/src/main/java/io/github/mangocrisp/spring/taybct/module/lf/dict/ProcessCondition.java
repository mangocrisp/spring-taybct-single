package io.github.mangocrisp.spring.taybct.module.lf.dict;

import io.github.mangocrisp.spring.taybct.common.dict.AbstractSysDict;
import lombok.Getter;

import java.io.Serial;

/**
 * 流程判断条件
 *
 * @author XiJieYin <br> 2023/7/13 15:56
 */
@Getter
public final class ProcessCondition extends AbstractSysDict {

    @Serial
    private static final long serialVersionUID = 6994419157274562359L;

    public ProcessCondition(String key, String val) {
        super(key, val);
    }

    /**
     * 主题（spring bean），主题订阅者计算结果，或者处理业务
     */
    public static final ProcessCondition topic = new ProcessCondition("topic", "主题（spring bean），主题订阅者计算结果，或者处理业务");
    /**
     * SpEL 表达式计算结果，或者处理业务
     */
    public static final ProcessCondition SpEL = new ProcessCondition("SpEL", "SpEL 表达式计算结果，或者处理业务");
}
