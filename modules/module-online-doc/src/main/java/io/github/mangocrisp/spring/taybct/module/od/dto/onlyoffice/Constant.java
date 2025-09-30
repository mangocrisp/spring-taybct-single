package io.github.mangocrisp.spring.taybct.module.od.dto.onlyoffice;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * 常量
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/4/7 09:40
 */
public interface Constant {

    /**
     * 定义要打开的文档类型
     */
    Map<String, Set<String>> documentType = Map.of("word", Set.of(".doc", ".docm", ".docx", ".dot", ".dotm", ".dotx", ".epub", ".fb2", ".fodt", ".htm", ".html", ".mht", ".mhtml", ".odt", ".ott", ".pages", ".rtf", ".stw", ".sxw", ".txt", ".wps", ".wpt", ".xml"),
            "cell", Set.of(".csv", ".et", ".ett", ".fods", ".numbers", ".ods", ".ots", ".sxc", ".xls", ".xlsb", ".xlsm", ".xlsx", ".xlt", ".xltm", ".xltx", ".xml"),
            "slide", Set.of(".dps", ".dpt", ".fodp", ".key", ".odp", ".otp", ".pot", ".potm", ".potx", ".pps", ".ppsm", ".ppsx", ".ppt", ".pptm", ".pptx", ".sxi"),
            "pdf", Set.of(".djvu", ".docxf", ".oform", ".oxps", ".pdf", ".xps"));

    /**
     * 定义查看或编辑的源文档的文件类型。必须是小写。以下文件类型可用
     */
    Set<String> fileType = Set.of(".csv", ".djvu", ".doc", ".docm", ".docx", ".docxf", ".dot", ".dotm", ".dotx", ".epub", ".fb2", ".fodp", ".fods", ".fodt", ".htm", ".html", ".key", ".mht", ".numbers", ".odp", ".ods", ".odt", ".oform", ".otp", ".ots", ".ott", ".oxps", ".pages", ".pdf", ".pot", ".potm", ".potx", ".pps", ".ppsm", ".ppsx", ".ppt", ".pptm", ".pptx", ".rtf", ".txt", ".xls", ".xlsb", ".xlsm", ".xlsx", ".xlt", ".xltm", ".xltx", ".xml", ".xps");

}
