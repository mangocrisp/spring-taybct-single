package io.github.mangocrisp.spring.taybct.common.message.sysfile;

import com.alibaba.fastjson2.JSONArray;
import io.github.mangocrisp.spring.taybct.tool.core.message.Message;
import lombok.*;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件关联
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FileSendDTO implements Message {

    @Serial
    private static final long serialVersionUID = 1L;

    @Singular("addLinks")
    public List<FileLinkDTO> links = new ArrayList<>();

    @Override
    public String getPayload() {
        return JSONArray.toJSONString(links);
    }
}