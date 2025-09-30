package io.github.mangocrisp.spring.taybct.module.system.handle.def;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.common.constants.ServeConstants;
import io.github.mangocrisp.spring.taybct.module.system.handle.AuthServeClientHandle;
import io.github.mangocrisp.spring.taybct.tool.core.exception.def.BaseException;
import io.github.mangocrisp.spring.taybct.tool.core.result.R;
import io.github.mangocrisp.spring.taybct.tool.core.util.HttpClientUtil;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * 鉴权客户端处理 Fieng 实现
 *
 * @author xijieyin <br> 2023/3/7 下午8:56
 */
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnMissingBean(AuthServeClientHandle.class)
public class AuthServeClientFeignHandle implements AuthServeClientHandle {

    final DiscoveryClient discoveryClient;

    @Value("${" + ServeConstants.SERVE + "." + ServeConstants.AUTH + ".service-id: taybct-auth}")
    private String authServiceId;

    @Override
    public boolean save(SysOauth2Client client) {
        List<ServiceInstance> instances = discoveryClient.getInstances(authServiceId);
        for (ServiceInstance instance : instances) {
            String s = HttpClientUtil.doRequestJson("http://" + instance.getHost() + ":" + instance.getPort() + "/registeredClient"
                    , new Header[]{}
                    , HttpClientUtil::Post
                    , JSONObject.toJSONString(client, JSONWriter.Feature.WriteMapNullValue));
            R r = JSONObject.parseObject(s, R.class);
            if (r.isOk()) {
                return true;
            }
        }
        throw new BaseException("鉴权服务器客户端信息保存失败!");
    }
}
