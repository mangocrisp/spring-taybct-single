package io.github.mangocrisp.spring.taybct.module.lf.pojo;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 业务属性表字段
 *
 * @author XiJieYin <br> 2023/7/11 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BusinessField implements Serializable {

    @Serial
    private static final long serialVersionUID = 4312742936001877397L;

    /**
     * 字段唯一键
     */
    private String key;
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段标题
     */
    private String title;
    /**
     * 字典类型
     * <br>
     * STRING: 字符串
     * NUMBER: 数字
     * BOOLEAN: 布尔 (0,1)
     * DATE_TIME: 时间
     * TEXT: 长文本
     * DICT: 字典
     * FILE: 文件
     */
    private String type;
    /**
     * 字段值
     */
    private Object value;
    /**
     * 字段排序
     */
    private Integer sort;
    /**
     * 字段状态
     */
    private String status;
    /**
     * 是否只读
     */
    private Boolean readonly;
    /**
     * 是否不可用
     */
    private Boolean disabled;
    /**
     * 是否必填
     */
    private Boolean required;
    /**
     * 如果类型是 FILE，这个需要指定需要上传哪些类型的文件
     */
    private String accept;
    /**
     * 是否允许多选文件上传（上传多个文件）
     */
    private Boolean multiple;
    /**
     * 占位符
     */
    private String placeholder;
    /**
     * 绑定组件
     */
    private String components;

}
