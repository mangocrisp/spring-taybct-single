package io.github.mangocrisp.spring.taybct.single.modules.lf.beans;

import io.github.mangocrisp.spring.taybct.module.lf.api.ProcessAutoDealHandler;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Edges;
import io.github.mangocrisp.spring.taybct.module.lf.domain.History;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Process;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <pre>
 * 结束后自动归档数据
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/3 16:11
 */
@AutoConfiguration("flowAutoArchived")
@Slf4j
@RequiredArgsConstructor
public class FlowAutoArchived implements ProcessAutoDealHandler {
    @Override
    public boolean apply(History history, Process process, Edges edges, Nodes nodes) {
        // TODO 保存业务数据，可以根据 JSONObject formData = ProcessUtil.getJSONObject(process.getFormData()); 来获取到所有的表单数据（包含历史循环提交数据）
        log.warn("保存业务数据，可以根据 JSONObject formData = ProcessUtil.getJSONObject(process.getFormData()); 来获取到所有的表单数据（包含历史循环提交数据）");
        return true;
    }
}
