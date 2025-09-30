package io.github.mangocrisp.spring.taybct.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldJSON;
import io.github.mangocrisp.spring.taybct.tool.core.bean.DeleteLogicEntity;
import io.github.mangocrisp.spring.taybct.tool.core.support.ToJSONObjectSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息通知
 * <br> sys_notice
 *
 * @author xijieyin <br> 2022/10/10 15:44
 * @since 1.0.5
 */
@TableName(value = "sys_notice")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "消息通知")
public class SysNotice extends DeleteLogicEntity<Long, Long> implements Serializable {
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = -9092697837754920072L;

    /**
     * 标题
     */
    @NotBlank(message = "[标题]不能为空")
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "标题")
    @Length(max = 100, message = "编码长度不能超过100")
    private String title;
    /**
     * 通知消息
     */
    @NotBlank(message = "[通知消息]不能为空")
    @Schema(description = "通知消息")
    private String message;
    /**
     * 级别(字典 notice_level)
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "级别(字典 notice_level)")
    @Length(max = 100, message = "编码长度不能超过100")
    private String level;
    /**
     * 是否是确定指定的通知(1是 0 否)
     */
    @NotNull(message = "[是否是确定指定的通知(1是 0 否)]不能为空")
    @Schema(description = "是否是确定指定的通知(1是 0 否)")
    @TableFieldDefault("0")
    private Integer positive;
    /**
     * 通知数据
     */
    @TableFieldJSON
    @Schema(description = "通知数据")
    @JsonSerialize(using = ToJSONObjectSerializer.class)
    @TableFieldDefault("{}")
    private Object data;
    /**
     * 主题
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "主题")
    private String topic;
    /**
     * 子类型
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "子类型")
    private String subType;
    /**
     * 发送人
     */
    @Schema(description = "发送人")
    private Long fromUser;
    /**
     * 发送人名称
     */
    @Schema(description = "发送人名称")
    private String fromUserName;
    /**
     * 发送人头像
     */
    @Schema(description = "发送人头像")
    private String fromUserAvatar;

}