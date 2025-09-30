package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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

import java.io.Serial;
import java.io.Serializable;

/**
 * 租户表
 *
 * @author xijieyin <br> 2022/8/17 17:13
 * @since 1.0.1
 */
@TableName(value = "sys_tenant")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "租户")
@EqualsAndHashCode(callSuper = true)
public class SysTenant extends UniqueDeleteLogic<Long, Long> implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 5124583079559480442L;
    /**
     * 租户 id
     */
    @Schema(description = "租户 id")
    @TableFieldDefault(isRandom = true)
    private String tenantId;
    /**
     * 租户名
     */
    @NotBlank(message = "[租户名]不能为空")
    @Size(max = 50, message = "编码长度不能超过50")
    @Schema(description = "租户名")
    @Length(max = 50, message = "编码长度不能超过50")
    @TableField(condition = SqlCondition.LIKE)
    private String tenantName;
    /**
     * 租户管理员
     */
    @NotBlank(message = "[租户管理员]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "租户管理员")
    @Length(max = 100, message = "编码长度不能超过100")
    private String tenantManager;
    /**
     * icon 图标
     */
    @Size(max = 100, message = "图标长度不能超过255")
    @Schema(description = "icon 图标")
    @Length(max = 100, message = "图标长度不能超过255")
    private String icon;
    /**
     * 备注
     */
    @Size(max = 100, message = "备注长度不能超过255")
    @Schema(description = "备注")
    @Length(max = 100, message = "备注长度不能超过255")
    private String remark;
    /**
     * 状态(1 有效 0 禁用)
     */
    @Schema(description = "状态(1 有效 0 禁用 )")
    @TableFieldDefault("1")
    private Byte status;
    /**
     * 排序
     */
    @Schema(description = "排序")
    @TableFieldDefault("1")
    private Integer sort;

    public SysTenant(String tenantId, String tenantName, String tenantManager) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantManager = tenantManager;
    }

    /**
     * 默认租户
     *
     * @return SysTenant
     * @author xijieyin <br> 2022/9/28 9:45
     * @since 1.0.4
     */
    public static SysTenant defaultTenant(String tenantId) {
        return new SysTenant(tenantId, "默认租户", "root");
    }
}