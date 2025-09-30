package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典<br>
 * sys_dict
 *
 * @author xijieyin <br> 2022/8/5 10:02
 * @since 1.0.0
 */
@TableName(value = "sys_dict")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典")
public class SysDict extends UniqueDeleteLogic<Long, Long> implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 5085569757024030299L;

    /**
     * 类型
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "类型")
    @Length(max = 128, message = "编码长度不能超过128")
    private String dictCode;

    /**
     * 键
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "键 例：1")
    @Length(max = 128, message = "编码长度不能超过128")
    private String dictKey;

    /**
     * 值
     */
    @TableField(condition = SqlCondition.LIKE)
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "值 例：男")
    @Length(max = 128, message = "编码长度不能超过128")
    private String dictVal;

    /**
     * 是否可用 1可用、0不可用
     */
    @Schema(description = "是否可用")
    @TableFieldDefault("1")
    private Byte status;

    /**
     * 备注
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "备注")
    @Length(max = 255, message = "编码长度不能超过255")
    private String remark;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @TableFieldDefault("0")
    private Integer sort;
    /**
     * 样式名
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "样式名")
    @Length(max = 128, message = "编码长度不能超过128")
    @TableFieldDefault(" ")
    private String cssClass;
    /**
     * 状态类型
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "状态类型")
    @Length(max = 128, message = "编码长度不能超过128")
    @TableFieldDefault(" ")
    private String statusClass;

}