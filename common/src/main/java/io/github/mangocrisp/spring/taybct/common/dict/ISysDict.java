package io.github.mangocrisp.spring.taybct.common.dict;

import java.io.Serializable;

/**
 * 系统字典
 *
 * @author xijieyin <br> 2022/12/20 17:48
 */
public interface ISysDict extends Serializable {

    /**
     * 字典的键
     *
     * @return String
     */
    String getKey();

    /**
     * 字典的值
     *
     * @return String
     */
    String getVal();

}
