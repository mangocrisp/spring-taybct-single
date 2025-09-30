package io.github.mangocrisp.spring.taybct.api.system.feign;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysNotice;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysNoticeDTO;
import io.github.mangocrisp.spring.taybct.api.system.feign.factory.NoticeFallbackFactory;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.tool.core.constant.AppConstants;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.support.WSR;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <pre>
 * 消息通知客户端
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/8/21 18:04
 */
@FeignClient(primary = false, contextId = "noticeClient", value = ServeConstants.SERVE_ID_SYSTEM, fallbackFactory = NoticeFallbackFactory.class)
public interface INoticeClient {

    /**
     * 新增消息通知
     *
     * @param dto 通知数据传输对象
     * @return 新增结果
     */
    @PostMapping("v" + AppConstants.DEFAULT_API_VERSION + "/notice/relatedNotices")
    R<SysNotice> addRelatedNotices(@RequestBody SysNoticeDTO dto);

    /**
     * 发送当前用户消息
     *
     * @param message 消息内容
     */
    @PostMapping("v" + AppConstants.DEFAULT_API_VERSION + "/notice/sendCurrentUserMessage")
    R<?> sendCurrentUserMessage(@RequestParam String message);

    /**
     * 发送简单消息
     *
     * @param message 消息内容
     */
    @PostMapping("v" + AppConstants.DEFAULT_API_VERSION + "/notice/sendMessage")
    R<?> sendMessage(@RequestBody WSR<?> message);

    /**
     * 发送所有用户消息
     *
     * @param message 消息内容
     */
    @PostMapping("v" + AppConstants.DEFAULT_API_VERSION + "/notice/sendAllMessage")
    R<?> sendAllMessage(@RequestBody WSR<?> message);

}
