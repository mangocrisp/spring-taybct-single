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
 * 角色部门关联表，可以知道角色有多少部门，也可以知道部门有多少角色
 * sys_role_dept
 */
@TableName(value = "sys_role_dept")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "角色部门关联表")
public class SysRoleDept implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 3665644340984093464L;

    /**
     * 主键
     */
    @NotNull(message = "[主键]不能为空")
    @Schema(description = "主键")
    @TableId
    @TableFieldDefault(handler = TableFieldDefaultPKHandler.class)
    private Long id;
    /**
     * 角色 id
     */
    @NotNull(message = "[角色 id]不能为空")
    @Schema(description = "角色 id")
    private Long roleId;
    /**
     * 部门 id
     */
    @NotNull(message = "[部门 id]不能为空")
    @Schema(description = "部门 id")
    private Long deptId;

}