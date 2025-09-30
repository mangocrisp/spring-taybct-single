package io.github.mangocrisp.spring.taybct.api.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户有效性校验 DTO
 *
 * @author XiJieYin <br> 2023/8/1 17:35
 */
@Data
@Schema(description = "用户有效性校验 DTO")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPassCheckDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4449967948469150249L;


    /**
     * 修改的时候如果有用户 id
     */
    @Schema(description = "修改的时候如果有用户 id")
    private Long id;

    /**
     * 登录用户名
     */
    @Schema(description = "用户名（登录用户名）")
    private String username;

    /**
     * 电话
     */
    @Schema(description = "电话")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

}
