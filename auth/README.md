# RBAC权限模型

RBAC(Role-Based Access Control)基于角色访问控制

# 授权模式

1. authorization_code：授权码模式（官方推荐），适合第三方登录 client -- 带着 client id 去获取授权码 -- 需要登录验证 --
   验证通过后获取到授权码 -- 用获取到的授权码去请求获取 token -- 拿到 token 才能请求资源
2. implicit：简化模式（不常用） ，不再需要授权码 直接就返回了 token ，但是这个第一步的 token 是不能直接用的，是存在 Fragment
   里面的 ，需要到另一个 Web-Hosted Client 服务器去解析，这个服务器会返回一个 script 让客户端来解析，解析之后的 token
   就是我们需要的 token了
3. password：密码模式（用得也不多） ，用户直接输入密码去授权服务器获取 token
4. client_credentials：客户端模式 （不常用），直接认证服务器获取 token
5. refresh_token: 刷新令牌，刷新令牌的时效比 token 要长 直接用刷新令牌获取到 token

## 授权码模式

Authorization 选择 Basic Auth
输入 client_id 和 密钥

1. 获取授权码

>
url: http://10.18.80.14:9102/auth/oauth/authorize?response_type=code&client_id=taybct_pc&redirect_uri=https://www.baidu.com
>
url: http://127.0.0.1:9000/oauth/authorize?response_type=code&client_id=messaging-client&scope=message.read&redirect_uri=http://127.0.0.1:9000/authorized
>
url: http://127.0.0.1:9000/oauth/authorize?response_type=code&client_id=taybct_pc&scope=all&redirect_uri=https://www.baidu.com

2. 获取 token

> url: oauth/token  
> grant_type: authorization_code  
> client_id: client  
> redirect_uri: http://www.baidu.com  
> code: 授权码  
> scope: all

## 密码模式

> url: oauth/token  
> grant_type: password  
> scope: all  
> username: xxx
> password: xxx

## 刷新令牌

> url: oauth/token  
> grant_type: refresh_token  
> refresh_token: xxx

## 生成 jwt.jks ,为了方便，这里面所有的输入都是 taybct

```shell
keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
输入密钥库口令:
再次输入新口令:
您的名字与姓氏是什么?
  [Unknown]:  taybct
您的组织单位名称是什么?
  [Unknown]:  taybct
您的组织名称是什么?
  [Unknown]:  taybct
您所在的城市或区域名称是什么?
  [Unknown]:  taybct
您所在的省/市/自治区名称是什么?
  [Unknown]:  taybct
该单位的双字母国家/地区代码是什么?
  [Unknown]:  taybct
CN=taybct, OU=taybct, O=taybct, L=taybct, ST=taybct, C=taybct是否正确?
  [否]:  y

输入 <jwt> 的密钥口令
        (如果和密钥库口令相同, 按回车):
再次输入新口令:

Warning:
JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore jwt.jks -destkeystore jwt.jks -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。
```

```shell
keytool -importkeystore -srckeystore jwt.jks -destkeystore jwt.jks -deststoretype pkcs12
输入源密钥库口令:
已成功导入别名 jwt 的条目。
已完成导入命令: 1 个条目成功导入, 0 个条目失败或取消

Warning:
已将 "jwt.jks" 迁移到 Non JKS/JCEKS。将 JKS 密钥库作为 "jwt.jks.old" 进行了备份。
```

```bash
# 查看证书，公钥
keytool -list -rfc -keystore jwt.jks -storepass taybct | openssl x509 -inform pem -pubkey
# 导出公钥
keytool -export -alias jwt -keystore jwt.jks -storepass taybct -file jwt-pubkey.cer

```

## 自定义授权模式解析

> AuthenticationManagerBuilder 把 provider 加入  
> -->  
> .performBuild() 构建完  
> -->  
> ProviderManager.authenticate(), 从 provider 里面找到对应的进行鉴权  
> -->  
> 进入到自己定义的 provider 鉴权，但是这里要注意的是，加了多少个 provider 他就会认证多少次，直到有一个结果返回不为空，就算是通过了。
> 如果有设置 provider 支持的 token 他就会只找对应的 token 进行验证
> -->
> 如果所有自己定义的 provider 都没有返回结果的时候，他就会去默认的 AuthenticationManager ，也就是我们在 WebSecurityConfig
> 里面配置 super.AuthenticationManager 认证，下面是源码
>

```java
package org.springframework.security.authentication;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class ProviderManager implements AuthenticationManager, MessageSourceAware, InitializingBean {

    private AuthenticationManager parent;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> toTest = authentication.getClass();
        AuthenticationException lastException = null;
        AuthenticationException parentException = null;
        Authentication result = null;
        Authentication parentResult = null;
        int currentPosition = 0;
        int size = this.providers.size();
        Iterator var9 = this.getProviders().iterator();

        while(var9.hasNext()) {
            AuthenticationProvider provider = (AuthenticationProvider)var9.next();
            // 在这里判断支持的 token
            if (provider.supports(toTest)) {
                if (logger.isTraceEnabled()) {
                    Log var10000 = logger;
                    String var10002 = provider.getClass().getSimpleName();
                    ++currentPosition;
                    var10000.trace(LogMessage.format("Authenticating request with %s (%d/%d)", var10002, currentPosition, size));
                }

                try {
                    result = provider.authenticate(authentication);
                    if (result != null) {
                        this.copyDetails(authentication, result);
                        break;
                    }
                } catch (InternalAuthenticationServiceException | AccountStatusException var14) {
                    this.prepareException(var14, authentication);
                    throw var14;
                } catch (AuthenticationException var15) {
                    lastException = var15;
                }
            }
        }

        // 直接到这里，如果没有拿到结果 就会用默认的 AuthenticationManager 认证授权提供者进行认证授权
        if (result == null && this.parent != null) {
            try {
                parentResult = this.parent.authenticate(authentication);
                result = parentResult;
            } catch (ProviderNotFoundException var12) {
            } catch (AuthenticationException var13) {
                parentException = var13;
                lastException = var13;
            }
        }

        if (result != null) {
            if (this.eraseCredentialsAfterAuthentication && result instanceof CredentialsContainer) {
                ((CredentialsContainer)result).eraseCredentials();
            }

            if (parentResult == null) {
                this.eventPublisher.publishAuthenticationSuccess(result);
            }

            return result;
        } else {
            if (lastException == null) {
                lastException = new ProviderNotFoundException(this.messages.getMessage("ProviderManager.providerNotFound", new Object[]{toTest.getName()}, "No AuthenticationProvider found for {0}"));
            }

            if (parentException == null) {
                this.prepareException((AuthenticationException)lastException, authentication);
            }

            throw lastException;
        }
    }
}
```

