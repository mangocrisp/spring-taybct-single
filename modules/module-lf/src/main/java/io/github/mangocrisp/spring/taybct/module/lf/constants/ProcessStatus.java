package io.github.mangocrisp.spring.taybct.module.lf.constants;

/**
 * 流程状态
 *
 * @author XiJieYin <br> 2023/7/13 14:15
 */
public interface ProcessStatus {
    /**
     * 运行中
     */
    byte RUNNING = 1;
    /**
     * 结束
     */
    byte END = 0;
    /**
     * 中断
     */
    byte INTERRUPT = -1;
}
