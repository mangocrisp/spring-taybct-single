package io.github.mangocrisp.spring.taybct.module.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysNotice;
import io.github.mangocrisp.spring.taybct.api.system.dto.SysNoticeUserDTO;
import io.github.mangocrisp.spring.taybct.api.system.vo.SysNoticeVO;
import io.github.mangocrisp.spring.taybct.tool.core.bean.service.IBaseService;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.support.WSR;

import java.util.Collection;
import java.util.Map;

/**
 * 针对表【sys_notice(消息通知)】的数据库操作Service
 *
 * @author xijieyin <br> 2022/10/10 15:46
 * @since 1.0.5
 */
public interface ISysNoticeService extends IBaseService<SysNotice> {

    /**
     * 消除消息通知（全部改为已读）
     *
     * @return 是否清除成功
     */
    boolean clean();

    /**
     * 用户消息分页
     *
     * @param sqlQueryParams sql 查询参数
     * @return {@code IPage<SysNoticeVO>}
     * @author xijieyin <br> 2022/10/10 16:49
     * @since 1.0.5
     */
    IPage<SysNoticeVO> userNoticesPage(Map<String, Object> sqlQueryParams);

    /**
     * 添加消息通知
     *
     * @param notice      消息通知
     * @param noticeUsers 消息通知附加的通知对象关系，如果不指定就是通知公告，所有人可见
     * @return boolean
     * @author xijieyin <br> 2022/10/11 14:03
     * @since 1.0.5
     */
    boolean addRelatedNotices(SysNotice notice, Collection<SysNoticeUserDTO> noticeUsers);

    /**
     * 批量更新用户消息
     *
     * @param status    [状态(0不可见 1 已读 2待办)]不能为空
     * @param noticeIds 消息 id
     * @return boolean
     * @author xijieyin <br> 2022/10/11 10:32
     * @since 1.0.5
     */
    boolean updateUserNotices(int status, Collection<Long> noticeIds);

    /**
     * 发送当前用户消息
     *
     * @param message 消息内容
     * @return boolean 是否发送成功
     * @author xijieyin <br> 2025/08/22 09:38:46
     * @since 3。2。4
     */
    boolean sendCurrentUserMessage(String message);

    /**
     * <pre>
     * 发送消息
     * </pre>
     *
     * @param message 消息
     * @return boolean
     * @author xijieyin
     * @since 3。2。4
     */
    <E> boolean sendMessage(WSR<E> message);

    /**
     * <pre>
     * 发送消息给所有人
     * </pre>
     *
     * @param message 消息
     * @return boolean
     * @author xijieyin
     * @since 3。2。4
     */
    <E> boolean sendAllMessage(WSR<E> message);
}
