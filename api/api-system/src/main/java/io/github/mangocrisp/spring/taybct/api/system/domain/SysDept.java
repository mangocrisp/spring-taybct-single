package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 部门
 * sys_dept
 */
@TableName(value = "sys_dept")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "部门")
public class SysDept extends UniqueDeleteLogic<Long, Long> implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 8114910768177489997L;

    /**
     * 部门名
     */
    @NotBlank(message = "[部门名]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @Schema(description = "部门名")
    @Length(max = 64, message = "编码长度不能超过64")
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    /**
     * 排序
     */
    @Schema(description = "排序")
    private Object sort;
    /**
     * 备注
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "备注")
    @Length(max = 255, message = "编码长度不能超过255")
    private String remark;
    /**
     * 全称
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "全称")
    @Length(max = 100, message = "编码长度不能超过100")
    @TableField(condition = SqlCondition.LIKE_LEFT)
    private String fullName;
    /**
     * 部门，组织机构代码
     */
    @NotBlank(message = "[部门，组织机构代码]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "部门，组织机构代码")
    @Length(max = 100, message = "编码长度不能超过100")
    @TableField(condition = SqlCondition.LIKE_LEFT)
    private String code;
    /**
     * 父 id
     */
    @Schema(description = "父 id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableFieldDefault("0")
    private Long pid;
    /**
     * 所有的父 id（可以多级）
     */
    @Size(max = 1000, message = "编码长度不能超过1000")
    @Schema(description = "所有的父 id（可以多级）")
    @Length(max = 1000, message = "编码长度不能超过1,000")
    @TableFieldDefault("0")
    private String pidAll;
    /**
     * 部门类型
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "部门类型")
    @Length(max = 100, message = "编码长度不能超过100")
    private String type;

}