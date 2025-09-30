package io.github.mangocrisp.spring.taybct.module.lf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.PresentProcess;

/**
 * @author admin
 * <br>description 针对表【lf_present_process(当前正在进行的流程)】的数据库操作Service
 * @since 2023-07-03 11:32:23
 */
public interface IPresentProcessService extends IService<PresentProcess> {
    /**
     * 保存当前节点信息
     *
     * @param nodes 节点信息
     * @return boolean
     */
    boolean save(Nodes nodes);

}
