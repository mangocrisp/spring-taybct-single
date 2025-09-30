
package io.github.mangocrisp.spring.taybct.module.lf.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户申请的 流程查询 DTO
 *
 * @author XiJieYin <br> 2023/7/14 16:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "用户申请的 流程查询 DTO")
public class UserRequestListQueryDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6231624597804786448L;

    /**
     * 流程图 id（可以知道当前流程是基于什么原始设计运行的）
     */
    @Schema(description = "流程图 id（可以知道当前流程是基于什么原始设计运行的）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long designId;
    /**
     * 流程标题
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "流程标题")
    @Length(max = 100, message = "编码长度不能超过100")
    private String title;
//    /**
//     * 流程发起人 id
//     */
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long userId;
    /**
     * 流程发布 id（可以知道当前流程是基于什么版本的设计在运行的）
     */
    @Schema(description = "流程发布 id（可以知道当前流程是基于什么版本的设计在运行的）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long releaseId;
    /**
     * 流程状态（1、流程进行中 0、流程已经完成 2、流程已归档 -1、流程中止）
     */
    @Schema(description = "流程状态（1、流程进行中 0、流程已经完成 2、流程已归档 -1、流程中止）")
    private Byte processStatus;
    /**
     * 流程类型（字典项 lf_process_type）
     */
    @Size(max = 100, message = "编码长度不能超过100")
    @Schema(description = "流程类型（字典项 lf_process_type）")
    @Length(max = 100, message = "编码长度不能超过100")
    private String type;
    /**
     * 是否是我提交的申请
     */
    @Schema(description = "是否是我提交的申请")
    private Boolean isMe;
}
