package io.github.mangocrisp.spring.taybct.module.lf.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.mangocrisp.spring.taybct.tool.core.constant.DateConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程列表查询 VO 对象
 *
 * @author XiJieYin <br> 2023/7/17 10:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "流程列表查询 VO 对象")
public class ProcessListVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2494815245297446809L;

    /**
     * 待办 id
     */
    @Schema(description = "待办 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long todoId;
    /**
     * 流程发布 id（可以知道当前流程是基于什么版本的设计在运行的）
     */
    @Schema(description = "流程发布 id（可以知道当前流程是基于什么版本的设计在运行的）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long releaseId;
    /**
     * 所属流程图 id
     */
    @Schema(description = "流程图 id（可以知道当前流程是基于什么原始设计运行的）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long designId;
    /**
     * 所属流程名称
     */
    @Schema(description = "所属流程名称")
    private String designName;
    /**
     * 运行流程 id
     */
    @Schema(description = "运行流程 id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long processId;
    /**
     * 流程标题
     */
    @Schema(description = "流程标题")
    private String title;
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createUser;
    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    private String createUserName;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    @JsonFormat(pattern = DateConstants.format.YYYY_MM_DD_HH_mm_ss)
    private LocalDateTime createTime;
    /**
     * 当前节点 id
     */
    @Schema(description = "当前节点 id")
    private String nodeId;
    /**
     * 当前节点标题
     */
    @Schema(description = "当前节点标题")
    private String nodeText;
    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;
    /**
     * 最后版本号
     */
    @Schema(description = "最后版本号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lastVersion;
    /**
     * 流程状态（1、流程进行中 0、流程已经完成 2、流程已归档 -1、流程中止）
     */
    @Schema(description = "流程状态（1、流程进行中 0、流程已经完成 2、流程已归档 -1、流程中止）")
    private Byte processStatus;

    /**
     * 状态（1、待办、0、已办）
     */
    @Schema(description = "状态（1、待办、0、已办）")
    private Byte status;
    /**
     * 流程类型（字典项 lf_process_type）
     */
    @Schema(description = "流程类型（字典项 lf_process_type）")
    private String type;

    /**
     * 待办状态（1、待处理 2、待阅 3、驳回）
     */
    @Schema(description = "待办状态（1、待处理 2、待阅 3、驳回）")
    private Byte todoStatus;

    /**
     * 已办状态（这个可以行写自动处理 bean 去自定义状态）
     */
    @Schema(description = "已办状态（这个可以行写自动处理 bean 去自定义状态）")
    private Byte doneStatus;

    /**
     * 待办类型（1、任务待办 2、抄送待办 ...其他类型自定义）
     */
    @Schema(description = "待办类型（1、任务待办 2、抄送待办 ...其他类型自定义）")
    private String todoType;

}
