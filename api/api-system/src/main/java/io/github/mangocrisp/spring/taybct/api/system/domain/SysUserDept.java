package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.handle.TableFieldDefaultPKHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户部门关联表，可以知道用户有多少部门，也可以知道部门有多少用户
 * sys_user_dept
 */
@TableName(value = "sys_user_dept")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户部门关联表")
public class SysUserDept implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 3222884209243012860L;

    /**
     * 主键
     */
    @NotNull(message = "[主键]不能为空")
    @Schema(description = "主键")
    @TableId
    @TableFieldDefault(handler = TableFieldDefaultPKHandler.class)
    private Long id;
    /**
     * 用户 id
     */
    @NotNull(message = "[用户 id]不能为空")
    @Schema(description = "用户 id")
    private Long userId;
    /**
     * 部门 id
     */
    @NotNull(message = "[部门 id]不能为空")
    @Schema(description = "部门 id")
    private Long deptId;

}