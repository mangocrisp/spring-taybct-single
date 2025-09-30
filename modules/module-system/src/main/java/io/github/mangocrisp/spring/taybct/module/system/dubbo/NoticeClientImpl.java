package io.github.mangocrisp.spring.taybct.module.system.dubbo;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysNotice;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysNoticeDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.INoticeClient;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysNoticeService;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.support.WSR;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * <pre>
 * 消息通知接口实现
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/8/21 18:11
 */
@DubboService(protocol = "dubbo")
@RequiredArgsConstructor
@AutoConfiguration
@ConditionalOnClass(ConfigCenterConfig.class)
public class NoticeClientImpl implements INoticeClient {

    final ISysNoticeService sysNoticeService;

    @Override
    public R<SysNotice> addRelatedNotices(SysNoticeDTO dto) {
        return sysNoticeService.addRelatedNotices(dto.getSysNotice(), dto.getNoticeUsers()) ? R.data(dto.getSysNotice()) : R.fail("添加通知失败");
    }

    @Override
    public R<?> sendCurrentUserMessage(String message) {
        return sysNoticeService.sendCurrentUserMessage(message) ? R.ok("发送成功") : R.fail("发送失败");
    }

    @Override
    public R<?> sendMessage(WSR<?> message) {
        return sysNoticeService.sendMessage(message) ? R.ok("发送成功") : R.fail("发送失败");
    }

    @Override
    public R<?> sendAllMessage(WSR<?> message) {
        return sysNoticeService.sendAllMessage(message) ? R.ok("发送成功") : R.fail("发送失败");
    }
}
