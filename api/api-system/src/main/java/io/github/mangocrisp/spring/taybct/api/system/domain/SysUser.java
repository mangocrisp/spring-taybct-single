package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.mangocrisp.spring.taybct.api.system.handle.PasswordHandler;
import io.github.mangocrisp.spring.taybct.common.dict.SysDict;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElement;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.EnhanceElements;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
import io.github.mangocrisp.spring.taybct.tool.core.bean.validated.InsertGroup;
import io.github.mangocrisp.spring.taybct.tool.core.bean.validated.UpdateGroup;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
 * 用户<br>
 * sys_user
 *
 * @author xijieyin <br> 2022/8/5 10:06
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "系统用户")
@EnhanceElements
public class SysUser extends UniqueDeleteLogic<Long, Long> implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 4811597827423826538L;
    /**
     * 登录用户名
     */
    @Schema(description = "用户名（登录用户名）", required = true)
    @NotBlank(message = "用户名不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @Size(max = 64, message = "用户名长度不能超过64", groups = {InsertGroup.class, UpdateGroup.class})
    @Length(max = 64, message = "用户名长度不能超过64", groups = {InsertGroup.class, UpdateGroup.class})
    @TableField(condition = SqlCondition.LIKE)
    private String username;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    @Size(max = 64, message = "真实姓名长度不能超过64", groups = {InsertGroup.class, UpdateGroup.class})
    @Length(max = 64, message = "真实姓名长度不能超过64", groups = {InsertGroup.class, UpdateGroup.class})
    @TableField(condition = SqlCondition.LIKE)
    private String realName;

    /**
     * 昵称
     */
    @Schema(description = "昵称", required = true)
    @NotBlank(message = "昵称不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @Size(max = 64, message = "昵称长度不能超过64", groups = {InsertGroup.class, UpdateGroup.class})
    @Length(max = 64, message = "昵称长度不能超过64", groups = {InsertGroup.class, UpdateGroup.class})
    @TableField(condition = SqlCondition.LIKE)
    private String nickname;

    /**
     * 性别
     *
     * @see SysDict.Gender
     */
    @Schema(description = "性别", required = true)
    @NotBlank(message = "性别不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String gender;

    /**
     * 密码
     */
    @Schema(description = "密码", required = true)
    @NotBlank(message = "密码不能为空", groups = {InsertGroup.class})
    @Size(max = 500, message = "密码太长了", groups = {InsertGroup.class, UpdateGroup.class})
    @Length(max = 500, message = "密码太长了", groups = {InsertGroup.class, UpdateGroup.class})
    @EnhanceElement(parameterHandler = {PasswordHandler.En.class}, resultHandler = {PasswordHandler.De.class})
    private String password;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 电话
     */
    @Schema(description = "电话", required = true)
    @NotBlank(message = "电话不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @Size(max = 32, message = "电话长度不能超过32", groups = {InsertGroup.class, UpdateGroup.class})
    @Length(max = 32, message = "电话长度不能超过32", groups = {InsertGroup.class, UpdateGroup.class})
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确", groups = {InsertGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 用户类型（00系统用户）
     *
     * @see SysDict.UserType
     */
    @Schema(description = "用户类型")
    @TableFieldDefault("00")
    private String userType;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @TableFieldDefault(" ")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime loginDate;

    /**
     * 状态(1 有效 0 无效  2 冻结)
     */
    @Schema(description = "状态(1 有效 0 无效  2 冻结)")
    @TableFieldDefault("1")
    private Byte status;

    /**
     * 微信小程序 openid
     */
    @Schema(description = "微信小程序 openid")
    private String openid;

    /**
     * 密码更新时间
     */
    @Schema(description = "密码更新时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @TableFieldDefault(isTimeNow = true)
    private LocalDateTime passwdTime;

}
