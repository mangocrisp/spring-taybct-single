package io.github.mangocrisp.spring.taybct.admin.log.es.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 系统日志索引
 *
 * @author xijieyin <br> 2022/8/4 18:54
 * @since 1.0.0
 */
@Data
@Schema(description = "接口日志")
@Accessors(chain = true)
@Document(indexName = "taybct_api_log")
public class ESApiLog implements Serializable {

    @Serial
    private static final long serialVersionUID = -7568551742786415725L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @Id
    private Long id;
    /**
     * 创建人
     */
    @Field(value = "create_user", type = FieldType.Long)
    @Schema(description = "创建人")
    private Long createUser;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @Field(value = "create_time", type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonProperty("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @Field(value = "update_user", type = FieldType.Long)
    @Schema(description = "更新人")
    private Long updateUser;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @Field(value = "update_time", type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonProperty("update_time")
    private LocalDateTime updateTime;
    /**
     * 模块标题
     */
    @Schema(description = "模块标题")
    @Field(value = "title", type = FieldType.Keyword)
    private String title;
    /**
     * 接口描述
     */
    @Schema(description = "接口描述")
    @Field(value = "description", type = FieldType.Text)
    private String description;
    /**
     * 操作人员
     */
    @Schema(description = "操作人员")
    @Field(value = "username", type = FieldType.Keyword)
    private String username;
    /**
     * 客户端类型
     */
    @Schema(description = "客户端类型")
    @Field(value = "client", type = FieldType.Keyword)
    private String client;
    /**
     * 主机地址
     */
    @Schema(description = "模块")
    @Field(value = "module", type = FieldType.Keyword)
    private String module;
    /**
     * 主机地址
     */
    @Schema(description = "主机地址")
    @Field(value = "ip", type = FieldType.Keyword)
    private String ip;
    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    @Field(value = "method_type", type = FieldType.Keyword)
    private String type;
    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    @Field(value = "method", type = FieldType.Keyword)
    private String method;
    /**
     * 请求URL
     */
    @Schema(description = "请求URL")
    @Field(value = "url", type = FieldType.Keyword)
    private String url;
    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    @Field(value = "params", type = FieldType.Text)
    private String params;
    /**
     * 返回参数
     */
    @Schema(description = "返回参数")
    @Field(value = "result", type = FieldType.Text)
    private String result;
    /**
     * 状态码
     */
    @Schema(description = "状态码")
    @Field(value = "code", type = FieldType.Keyword)
    private String code;
    /**
     * 租户 id 区分不同租户的日志
     */
    @Schema(description = "租户 id 区分不同租户的日志")
    @Field(value = "tenant_id", type = FieldType.Keyword)
    private String tenantId;

}
