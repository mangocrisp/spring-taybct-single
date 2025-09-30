package io.github.mangocrisp.spring.taybct.auth.service.impl;

import cn.hutool.core.convert.Convert;
import io.github.mangocrisp.spring.taybct.api.system.domain.SysOauth2Client;
import io.github.mangocrisp.spring.taybct.auth.security.util.RegisteredClientUtil;
import io.github.mangocrisp.spring.taybct.auth.service.IRegisteredService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

/**
 * IRegisteredService 实现
 *
 * @author xijieyin <br> 2023/3/7 下午8:30
 */
@AutoConfiguration
@Service
@RequiredArgsConstructor
public class RegisteredServiceImpl implements IRegisteredService {

    final PasswordEncoder passwordEncoder;

    final RegisteredClientRepository registeredClientRepository;
    @Nullable
    final JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "oauth2_registered_client";
    private static final String PK_FILTER = "id = ?";

    @Override
    public boolean save(SysOauth2Client sysOauth2Client) {
        // 这里要使用加密器加密一下密钥
        sysOauth2Client.setClientSecret(passwordEncoder.encode(sysOauth2Client.getClientSecret()));
        registeredClientRepository.save(RegisteredClientUtil.generateCommonClient(sysOauth2Client));
        if (jdbcTemplate != null) {
            // JdbcRegisteredClientRepository 不会自动更新 client_secret,没办法,我这里手动给他更新一下吧
            jdbcTemplate.update(String.format("UPDATE %s SET client_secret = ? WHERE %s", TABLE_NAME, PK_FILTER)
                    , ps -> {
                        ps.setString(1, sysOauth2Client.getClientSecret());
                        ps.setString(2, Convert.toStr(sysOauth2Client.getId()));
                    });
        }
        return true;
    }

}
