package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通知用户关联表
 * <br> sys_notice_user
 *
 * @author xijieyin <br> 2022/10/10 15:44
 * @since 1.0.5
 */
@TableName(value = "sys_notice_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "通知用户关联表")
public class SysNoticeUser implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = -2740491778756129687L;
    /**
     * 通知 id
     */
    @NotNull(message = "[通知 id]不能为空")
    @Schema(description = "通知 id")
    private Long noticeId;
    /**
     * 用于关联的 id，可能是用户，角色，租户 等 id
     */
    @NotNull(message = "[用于关联的 id，可能是用户，角色，租户 等 id]不能为空")
    @Schema(description = "用于关联的 id，可能是用户，角色，租户 等 id")
    private Long relatedId;
    /**
     * 通知类型(字典 notice_type)1是用户，其他的不管
     */
    @NotBlank(message = "[通知类型(字典 notice_type)1是用户，其他的不管]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "通知类型(字典 notice_type)1是用户，其他的不管")
    @Length(max = 100, message = "编码长度不能超过100")
    private String noticeType;
    /**
     * 状态(0不可见 1 已读 2待办)
     */
    @NotNull(message = "[状态(0不可见 1 已读 2待办)]不能为空")
    @Schema(description = "状态(0不可见 1 已读 2待办)")
    @TableFieldDefault("1")
    private Byte status;

}