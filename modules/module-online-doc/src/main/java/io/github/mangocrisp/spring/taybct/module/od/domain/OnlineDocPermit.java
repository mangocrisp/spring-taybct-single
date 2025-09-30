package io.github.mangocrisp.spring.taybct.module.od.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldDefault;
import io.github.mangocrisp.spring.taybct.tool.core.annotation.TableFieldJSON;
import io.github.mangocrisp.spring.taybct.tool.core.handle.TableFieldDefaultPKHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.security.Permissions;


/**
 * <pre>
 * 在线文档操作权限[t_online_doc_permit]
 * </pre>
 *
 * @author xijieyin
 * @since 2025/9/20 00:03
 */
@TableName(value = "t_online_doc_permit")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OnlineDocPermit implements Serializable {
    @Serial
    private static final long serialVersionUID = 5822119351685997245L;
    /**
     * 主键
     */
    @TableId(value = "id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableFieldDefault(handler = TableFieldDefaultPKHandler.class)
    private Long id;

    /**
     * 文档id
     */
    @TableField(value = "doc_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long docId;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 文档的操作权限
     *
     * @see Permissions
     */
    @TableField(value = "permissions")
    @TableFieldJSON
    private Object permissions;

    /**
     * 是否是管理员
     */
    @TableField(value = "is_admin")
    private Byte isAdmin;

}