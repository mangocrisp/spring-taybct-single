package io.github.mangocrisp.spring.taybct.module.system.server;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUser;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysUserOnline;
import io.github.mangocrisp.spring.taybct.module.system.service.ISysUserOnlineService;
import io.github.mangocrisp.spring.taybct.tool.core.constant.TokenConstants;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.ResultCode;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.endpoint.AbstractWebSocketServer;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.enums.MessageUserType;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.support.MessageUser;
import io.github.mangocrisp.spring.taybct.tool.core.websocket.support.WSR;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.function.Function;

/**
 * <pre>
 * websocket 服务
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/6/13 14:46
 */
@AutoConfiguration
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer extends AbstractWebSocketServer {

    public static ISysUserOnlineService sysUserOnlineService;

    public static KeyPair keyPair;

    public static Function<Long, SysUser> sysUserFunction = userId -> new SysUser();

    @SneakyThrows
    @Override
    public void onOpen(Long userId, Session session) {
        List<String> jtiList;
        if (CollectionUtil.isEmpty((jtiList = getRequestParameterMap(session.getId()).get(TokenConstants.JWT_JTI)))) {
            noAuthorizationHandle(userId, session);
        }
        SysUserOnline sysUserOnlineInfo = sysUserOnlineService.getOne(Wrappers.<SysUserOnline>lambdaQuery()
                .eq(SysUserOnline::getJti, jtiList.get(0)));
        if (ObjectUtil.isEmpty(sysUserOnlineInfo)) {
            // 如果找不到对应的 token 信息
            noAuthorizationHandle(userId, session);
        }
        if (!sysUserOnlineInfo.getUserId().equals(userId)) {
            // 防止冒充别人的 jti 来使用
            noAuthorizationHandle(userId, session);
        }
        // 验证 token 有效性
        String token = sysUserOnlineInfo.getAccessTokenValue();
        JWSObject parse = JWSObject.parse(token);
        if (!parse.verify(new RSASSAVerifier((RSAPublicKey) keyPair.getPublic()))) {
            // 如果验证失败，说明 token 已经过期或者被篡改
            noAuthorizationHandle(userId, session);
        }
        super.onOpen(userId, session);
    }

    /**
     * 未鉴权处理
     *
     * @param userId  用户 id
     * @param session session 会话
     * @throws IOException io 异常
     */
    private void noAuthorizationHandle(Long userId, Session session) throws IOException {
        sendMessage(WSR.fail(ResultCode.TOKEN_INVALID_OR_EXPIRED.getCode(), "用户鉴权失败！"), new MessageUser(MessageUserType.USER, userId, session.getId()));
        session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "用户鉴权失败！"));
        // 如果在在线用户列表里面找得到这个 jti 说明是登录成功并且在线的，允许连接，如果不在就报错
        throw new BaseException("用户鉴权失败！").setHttpStatus(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void onMessage(Session session, Long userId, String message) {
        // 从 session 里面拿请求参数，知道需要发送给的用户，这些用户可能是群发
        List<String> toUserIdList = session.getRequestParameterMap().get("toUserId");
        if (CollectionUtil.isNotEmpty(toUserIdList)) {
            SysUser sysUser = sysUserFunction.apply(userId);
            toUserIdList.forEach(toUserId -> sendMessage(WSR.ok(message)
                    .setFromUser(new MessageUser(MessageUserType.USER, userId, session.getId()))
                    .setName(sysUser.getNickname())
                    .setAvatar(sysUser.getAvatar())
                    .setToUserId(Convert.toLong(toUserId))));
        }
    }

}
