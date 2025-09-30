package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 用户_租户关联
 *
 * @author xijieyin <br> 2022/8/17 17:13
 * @since 1.0.1
 */
@TableName(value = "sys_user_tenant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户关联租户")
public class SysUserTenant implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = -8270646437526196058L;
    /**
     * 用户id
     */
    @NotNull(message = "[用户id]不能为空")
    @Schema(description = "用户id")
    private Long userId;
    /**
     * 租户表的 id ，不是租户 id
     */
    @NotBlank(message = "[租户表 id]不能为空")
    @Size(max = 12, message = "编码长度不能超过12")
    @Schema(description = "租户表 id")
    @Length(max = 12, message = "编码长度不能超过12")
    private String tenantId;

}