package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * Univer Sheets 中的单元格数据
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/3/27 22:21
 */
@Schema(description = "Univer Sheets 中的单元格数据")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FileData implements Serializable {

    @Serial
    private static final long serialVersionUID = -3440825142930202378L;

    /**
     * 定义要打开的文档类型
     */
    @Schema(description = "定义要打开的文档类型")
    private String documentType;
    /**
     * 为查看或编辑的文档定义所需的文件名
     */
    @Schema(description = "为查看或编辑的文档定义所需的文件名")
    private String title;
    /**
     * 定义查看或编辑的源文档的文件类型
     */
    @Schema(description = "定义查看或编辑的源文档的文件类型")
    private String fileType;
    /**
     * 定义存储查看或编辑的源文档的绝对 URL（原始文件）
     */
    @Schema(description = "定义存储查看或编辑的源文档的绝对 URL（原始文件）")
    private String originalUrl;
    /**
     * 定义存储查看或编辑的源文档的绝对 URL
     */
    @Schema(description = "定义存储查看或编辑的源文档的绝对 URL")
    private String url;
    /**
     * 显示文档版本历史 DTO
     */
    @Schema(description = "显示文档版本历史 DTO")
    private RefreshHistoryDTO refreshHistoryDTO;
    /**
     * 历史版本记录数据
     */
    @Schema(description = "历史版本记录数据")
    private List<HistoryData> historyData;

}
