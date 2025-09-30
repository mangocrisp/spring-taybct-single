package io.github.mangocrisp.spring.taybct.admin.log.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.bean.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;


/**
 * 接口日志<br>
 * api_log
 *
 * @author xijieyin <br> 2022/8/5 9:47
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "api_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "接口日志")
public class ApiLog extends BaseEntity<Long, Long> {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 8717743752437492418L;

    /**
     * 模块标题
     */
    @NotBlank(message = "[模块标题]不能为空")
    @Size(max = 50, message = "编码长度不能超过50")
    @Schema(description = "模块标题")
    @Length(max = 50, message = "编码长度不能超过50")
    @TableFieldDefault(" ")
    private String title;
    /**
     * 接口描述
     */
    @Size(max = 200, message = "编码长度不能超过200")
    @Schema(description = "接口描述")
    @Length(max = 200, message = "编码长度不能超过200")
    @TableFieldDefault(" ")
    private String description;
    /**
     * 操作人员
     */
    @Size(max = 50, message = "编码长度不能超过50")
    @Schema(description = "操作人员")
    @Length(max = 50, message = "编码长度不能超过50")
    @TableFieldDefault(" ")
    private String username;
    /**
     * 客户端类型
     */
    @Size(max = 30, message = "编码长度不能超过30")
    @Schema(description = "客户端类型")
    @Length(max = 30, message = "编码长度不能超过30")
    @TableFieldDefault("0")
    private String client;
    /**
     * 主机地址
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "模块")
    @Length(max = 128, message = "编码长度不能超过128")
    private String module;
    /**
     * 主机地址
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @Schema(description = "主机地址")
    @Length(max = 128, message = "编码长度不能超过128")
    @TableFieldDefault(" ")
    private String ip;
    /**
     * 业务类型
     */
    @Size(max = 20, message = "编码长度不能超过20")
    @Schema(description = "业务类型")
    @Length(max = 20, message = "编码长度不能超过20")
    @TableFieldDefault("0")
    private String type;
    /**
     * 请求方式
     */
    @Size(max = 10, message = "编码长度不能超过10")
    @Schema(description = "请求方式")
    @Length(max = 10, message = "编码长度不能超过10")
    @TableFieldDefault(" ")
    private String method;
    /**
     * 请求URL
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @Schema(description = "请求URL")
    @Length(max = 255, message = "编码长度不能超过255")
    @TableFieldDefault(" ")
    private String url;
    /**
     * 请求参数
     */
    @Size(max = 2000, message = "编码长度不能超过2000")
    @Schema(description = "请求参数")
    @Length(max = 2000, message = "编码长度不能超过2000")
    @TableFieldDefault(" ")
    private String params;
    /**
     * 返回参数
     */
    @Size(max = 2000, message = "编码长度不能超过2000")
    @Schema(description = "返回参数")
    @Length(max = 2000, message = "编码长度不能超过2000")
    @TableFieldDefault(" ")
    private String result;
    /**
     * 状态码
     */
    @Size(max = 20, message = "编码长度不能超过20")
    @Schema(description = "状态码")
    @Length(max = 20, message = "编码长度不能超过20")
    @TableFieldDefault("0")
    private String code;

    @Schema(description = "租户 id 区分不同租户的日志")
    private String tenantId;

}
