package io.github.mangocrisp.spring.taybct.module.od.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.api.system.handle.current.CurrentUserDeptId;
import io.github.mangocrisp.spring.taybct.api.system.handle.current.CurrentUserDeptName;
import io.github.mangocrisp.spring.taybct.api.system.handle.current.CurrentUserRealName;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldJSON;
import io.github.mangocrisp.spring.taybct.tool.core.bean.UniqueDeleteLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * <pre>
 * 在线文档[t_online_doc]
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/19 23:56
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_online_doc")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "在线文档")
public class OnlineDoc extends UniqueDeleteLogic<Long, Long> {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @Schema(hidden = true)
    private Integer easy_poi_excel_index = 1;
    /**
     * 文档名称
     */
    @NotBlank(message = "[文档名称]不能为空")
    @Size(max = 255, message = "[文档名称]长度不能超过255")
    @Length(max = 255, message = "[文档名称]长度不能超过255")
    @Schema(description = "文档名称")
    @TableField(value = "name")
    private String name;
    /**
     * 文档是否共享
     */
    @Schema(description = "文档是否共享")
    @TableField(value = "share")
    private Integer share;
    /**
     * 唯一字段
     */
    @Size(max = 255, message = "[唯一字段]长度不能超过255")
    @Length(max = 255, message = "[唯一字段]长度不能超过255")
    @Schema(description = "唯一字段")
    @TableField(value = "unique_field")
    private String uniqueField;

    /**
     * 文档属性设置（字段等）
     */
    @TableField(value = "properties")
    @TableFieldJSON
    private Object properties;

    /**
     * 所属部门id
     */
    @Schema(description = "所属部门id")
    @TableField(value = "dept_id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableFieldDefault(handler = CurrentUserDeptId.class)
    private Long deptId;
    /**
     * 所属部门名称
     */
    @Size(max = 255, message = "[所属部门名称]长度不能超过255")
    @Length(max = 255, message = "[所属部门名称]长度不能超过255")
    @Schema(description = "所属部门名称")
    @TableField(value = "dept_name")
    @TableFieldDefault(handler = CurrentUserDeptName.class)
    private String deptName;
    /**
     * 数据
     */
    @Schema(description = "数据")
    @TableField(value = "data")
    @TableFieldJSON
    private Object data;
    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    @TableField(value = "create_user_name")
    @TableFieldDefault(handler = CurrentUserRealName.class)
    private String createUserName;
    /**
     * 修改人姓名
     */
    @Schema(description = "修改人姓名")
    @TableField(value = "update_user_name")
    @TableFieldDefault(handler = CurrentUserRealName.class)
    private String updateUserName;
}
