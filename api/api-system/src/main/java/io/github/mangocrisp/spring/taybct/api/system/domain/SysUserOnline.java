package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.mangocrisp.spring.taybct.tool.core.bean.BaseEntity;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 在线用户<br>
 * sys_user_online
 *
 * @author xijieyin <br> 2022/8/5 10:06
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_online")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "在线用户")
public class SysUserOnline extends BaseEntity<Long, Long> implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 5867389110367732511L;
    /**
     * jwt token id
     */
    @NotBlank(message = "[jwt token id]不能为空")
    @Size(max = 50, message = "编码长度不能超过50")
    @Schema(description = "jwt token id")
    @Length(max = 50, message = "编码长度不能超过50")
    private String jti;
    /**
     * ip 地址
     */
    @Size(max = 20, message = "编码长度不能超过20")
    @Schema(description = "ip 地址")
    @Length(max = 20, message = "编码长度不能超过20")
    private String ip;
    /**
     * 客户端 id
     */
    @NotBlank(message = "[客户端 id]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "客户端 id")
    @Length(max = 100, message = "编码长度不能超过100")
    private String clientId;

    /**
     * 认证方式
     */
    @NotBlank(message = "[认证方式]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "认证方式")
    @Length(max = 100, message = "编码长度不能超过100")
    private String authMethod;
    /**
     * 用户名
     */
    @NotBlank(message = "[用户名]不能为空")
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "用户名")
    @Length(max = 255, message = "编码长度不能超过255")
    private String userName;
    /**
     * 登录时间
     */
    @NotNull(message = "[登录时间]不能为空")
    @Schema(description = "登录时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime loginTime;
    /**
     * 超时时间
     */
    @NotNull(message = "[超时时间]不能为空")
    @Schema(description = "超时时间")
    private Long exp;
    /**
     * 在什么时候超时
     */
    @Schema(description = "在什么时候超时")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime expTime;

    /**
     * 租户 id 区分不同租户的在线用户
     */
    @Schema(description = "租户 id 区分不同租户的在线用户")
    private String tenantId;

    /**
     * 用户 id
     */
    @Schema(description = "用户 id")
    private Long userId;

    /**
     * 访问的 token 值
     */
    @Schema(description = "访问的 token 值")
    private String accessTokenValue;

}
