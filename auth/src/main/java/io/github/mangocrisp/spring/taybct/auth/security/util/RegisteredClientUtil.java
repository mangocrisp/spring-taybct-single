package io.github.mangocrisp.spring.taybct.auth.security.util;

import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * 注册客户端工具
 *
 * @author xijieyin <br> 2023/1/3 17:41
 */
public class RegisteredClientUtil {

    public static RegisteredClient generateCommonClient(SysOauth2Client sysOauth2Client) {
        Boolean autoApprove = Optional.ofNullable(sysOauth2Client.getAutoApprove()).map(Boolean::parseBoolean).orElse(false);
        RegisteredClient.Builder builder = RegisteredClient.withId(sysOauth2Client.getId().toString())
                // 客户端 id 和上面的那个 id 不一样，这个是唯一标识，上面的是主键？？？
                .clientId(sysOauth2Client.getClientId())
                // Sets the time at which the client identifier was issued.
                // 设置客户端标识开始生效的时间？？？
//                .clientIdIssuedAt(Instant.ofEpochMilli(System.currentTimeMillis() + Duration.ofDays(30).toMillis()))
                // 密钥
                .clientSecret(sysOauth2Client.getClientSecret())
                /*
                   密钥过期时间，一般是最好不要过期吧，当然，这个也可以设置，多久之后就不能用了，这里是指定一个具体的时间点
                   比如这样的，当前时间之后的 30 天有效，但是这个不要动态设置，应该是存在数据库或者是哪里的一个固定值，意思
                   是到了这个时间点，这个密钥就失效了
                 */
                //.clientSecretExpiresAt(Instant.ofEpochMilli(System.currentTimeMillis() + Duration.ofDays(30).toMillis()))
                // 客户端名
                .clientName(sysOauth2Client.getClientName())
                // 客户端设置
                .clientSettings(ClientSettings.builder()
                        // true if authorization consent is required when the client requests access, false otherwise
                        // 这个设置成 true 就需要人去手动确认需要放行的 scope，如果设置成 false 就不管申请什么 scope 都直接通过
                        // 如果客户端请求访问时需要授权同意，则为true，否则为false
                        .requireAuthorizationConsent(!autoApprove).build())
                // 使用 basic 鉴权，这个默认用这个就好了
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

        // 设置鉴权类型，这里，没有我们要的类型就 new 一个
        Optional.ofNullable(sysOauth2Client.getAuthorizedGrantTypes())
                .map(s -> s.split(","))
                .ifPresent(types ->
                        Arrays.stream(types).forEach(type ->
                                builder.authorizationGrantType(new AuthorizationGrantType(type))));

        // 这里设置的是回调地址，可能有多个
        Optional.ofNullable(sysOauth2Client.getWebServerRedirectUri())
                .map(s -> s.split(","))
                .ifPresent(uris -> Arrays.stream(uris).forEach(builder::redirectUri));

        // 设置域
        Optional.ofNullable(sysOauth2Client.getScope())
                .map(s -> s.split(","))
                .ifPresent(arr -> Arrays.stream(arr).forEach(builder::scope));

        // 客户端 token 的设置
        /*
            jwt token payload

            iss: jwt签发者
            sub: jwt所面向的用户
            aud: 接收jwt的一方
            exp: jwt的过期时间，这个过期时间必须要大于签发时间
            nbf: 定义在什么时间之前，该jwt都是不可用的.
            iat: jwt的签发时间
            jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
         */
        builder.tokenSettings(TokenSettings.builder()
                // 授权码过期时长，默认5分钟
                //.authorizationCodeTimeToLive(Duration.ofMinutes(5))
                // access token 过期时间默认 5 分钟，这里按秒算
                .accessTokenTimeToLive(Duration.ofSeconds(sysOauth2Client.getAccessTokenValidity()))
                // access token 格式？？？
                //.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                /*
                   refresh token 有两种使用方式：重复使用(true)、非重复使用(false)，默认为 true
                   1 重复使用：access token 过期刷新时， refresh token 过期时间未改变，仍以初次生成的时间为准
                     当 access token 和 refresh token 都过期了，用户就需要重新登录
                   2 非重复使用：access token 过期刷新时， refresh token 过期时间延续，在 refresh token 有效期内刷新便永不失效达到无需再次登录的目的
                   如果在返回访问令牌响应时重用刷新令牌，则设置为true；如果发出新的刷新令牌，设置为false。
                 */
                .reuseRefreshTokens(true)
                // 刷新 token 过期时长，默认 60 分钟
                .refreshTokenTimeToLive(Duration.ofSeconds(sysOauth2Client.getRefreshTokenValidity()))
                // 设置用于签名ID令牌的JWS算法。
                //.idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
                .build());

        return builder.build();
    }
}
