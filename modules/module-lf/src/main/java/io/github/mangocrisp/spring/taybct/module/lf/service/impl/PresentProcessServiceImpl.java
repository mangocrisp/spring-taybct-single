package io.github.mangocrisp.spring.taybct.module.lf.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.mangocrisp.spring.taybct.module.lf.domain.Nodes;
import io.github.mangocrisp.spring.taybct.module.lf.domain.PresentProcess;
import io.github.mangocrisp.spring.taybct.module.lf.mapper.PresentProcessMapper;
import io.github.mangocrisp.spring.taybct.module.lf.service.IPresentProcessService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author admin
 * <br>description 针对表【lf_present_process(当前正在进行的流程)】的数据库操作Service实现
 * @since 2023-07-03 11:32:23
 */
public class PresentProcessServiceImpl extends ServiceImpl<PresentProcessMapper, PresentProcess>
        implements IPresentProcessService {

    @Override
    @Transactional
    public boolean save(Nodes nodes) {
        // 先删除流程之前的节点信息
        super.remove(Wrappers.<PresentProcess>lambdaQuery().eq(PresentProcess::getProcessId, nodes.getProcessId()));
        PresentProcess pp = new PresentProcess();
        pp.setNodeType(nodes.getType());
        pp.setNodeId(nodes.getId());
        pp.setProcessId(nodes.getProcessId());
        // 再保存当前节点
        return super.save(pp);
    }
}




