package io.github.mangocrisp.spring.taybct.module.lf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程图权限表
 *
 * <br>TableName lf_design_permissions
 */
@TableName(value = "lf_design_permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程图设计")
public class DesignPermissions implements Serializable {

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = -8363440381522514865L;

    /**
     * 主键
     */
    @NotNull(message = "[主键]不能为空")
    @Schema(description = "主键")
    @TableId
    @TableFieldDefault(expression = "T(com.baomidou.mybatisplus.core.toolkit.IdWorker).getId()")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 设计图 id
     */
    @NotNull(message = "[设计图 id]不能为空")
    @Schema(description = "设计图 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long designId;
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    /**
     * 编辑权限
     */
    @Schema(description = "编辑权限")
    @TableFieldDefault("1")
    private Integer permEdit;
    /**
     * 删除权限
     */
    @Schema(description = "删除权限")
    @TableFieldDefault("1")
    private Integer permDelete;
    /**
     * 发布权限
     */
    @Schema(description = "发布权限")
    @TableFieldDefault("1")
    private Integer permPublish;
    /**
     * 分享权限
     */
    @Schema(description = "分享权限")
    @TableFieldDefault("1")
    private Integer permShare;

}
