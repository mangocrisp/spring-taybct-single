package io.github.mangocrisp.spring.taybct.common.enums;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 系统参数类型
 *
 * @author xijieyin <br> 2022/12/6 14:56
 * @since 2.0.2
 */
@Getter
public enum ParamType {
    /**
     * 字符串
     */
    STRING("STRING", String.class),
    /**
     * 数字
     */
    NUMBER("NUMBER", Double.class),
    /**
     * 布尔
     */
    BOOLEAN("BOOLEAN", Boolean.class),
    /**
     * 日期
     */
    DATE_TIME("DATE_TIME", LocalDateTime.class);
    private final String name;
    private final Class<?> clazz;

    ParamType(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }
}