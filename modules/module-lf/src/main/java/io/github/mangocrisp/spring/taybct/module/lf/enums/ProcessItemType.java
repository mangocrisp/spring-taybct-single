package io.github.mangocrisp.spring.taybct.module.lf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程对象类型
 *
 * @author XiJieYin <br> 2023/7/14 15:20
 */
@AllArgsConstructor
@Getter
public enum ProcessItemType {
    /**
     * 节点
     */
    NODE("node"),
    /**
     * 线
     */
    EDGE("edge");

    private final String alias;
}
