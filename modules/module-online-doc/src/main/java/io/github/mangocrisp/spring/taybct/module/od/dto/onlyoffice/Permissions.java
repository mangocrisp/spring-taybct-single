package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * <pre>
 * 文档操作权限
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/4/19 00:19
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文档操作权限")
public class Permissions implements Serializable {

    @Serial
    private static final long serialVersionUID = 4286401453366275826L;

    /**
     * 聊天
     */
    @Schema(description = "聊天")
    private Boolean chat = true;
    /**
     * 下载
     */
    @Schema(description = "下载")
    private Boolean download = true;
    /**
     * 复制
     */
    @Schema(description = "复制")
    private Boolean copy = true;
    /**
     * 评论
     */
    @Schema(description = "评论")
    private Boolean comment = true;
    /**
     * 编辑
     */
    @Schema(description = "编辑")
    private Boolean edit = true;
    /**
     * 打印
     */
    @Schema(description = "打印")
    private Boolean print = true;

}
