package io.github.mangocrisp.spring.taybct.api.system.feign.factory;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysNotice;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysNoticeDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.INoticeClient;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.support.WSR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * <pre>
 * 消息通知回调（Sentinel）
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/8/21 18:08
 */
@AutoConfiguration
@Slf4j
@ConditionalOnClass({FallbackFactory.class})
public class NoticeFallbackFactory implements FallbackFactory<INoticeClient> {
    @Override
    public INoticeClient create(Throwable cause) {
        log.error("系统模块 - INoticeClient 调用失败！", cause);
        return new INoticeClient() {
            @Override
            public R<SysNotice> addRelatedNotices(SysNoticeDTO dto) {
                return getR("添加消息通知失败！");
            }

            @Override
            public R<?> sendCurrentUserMessage(String message) {
                return getR("发送当前用户消息失败！");
            }

            @Override
            public R<?> sendMessage(WSR<?> message) {
                return getR("发送消息失败！");
            }

            @Override
            public R<?> sendAllMessage(WSR<?> message) {
                return getR("发送所有消息失败！");
            }
        };
    }

    private <S> R<S> getR(String message) {
        log.error(message);
        return R.fail(ResultCode.FEIGN_ERROR.getCode(), message);
    }
}
