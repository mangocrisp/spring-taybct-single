package io.github.mangocrisp.spring.taybct.module.lf.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 权限 DTO
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/11 18:46
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PermissionsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7989505614373674753L;
    /**
     * 用户/部门/角色 id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户/部门/角色 名称
     */
    private String name;
}
