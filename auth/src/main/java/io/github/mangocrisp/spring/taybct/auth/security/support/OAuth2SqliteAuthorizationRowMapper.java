package io.github.mangocrisp.spring.taybct.auth.security.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <pre>
 * 读取数据库数据解析器
 * </pre>
 *
 * @author XiJieYin
 * @since 2025/9/16 15:31
 */
public class OAuth2SqliteAuthorizationRowMapper implements RowMapper<OAuth2Authorization> {

    @Setter
    @Getter
    private LobHandler lobHandler = new DefaultLobHandler();
    @Setter
    @Getter
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TABLE_NAME = "oauth2_authorization";
    @Getter
    private final RegisteredClientRepository registeredClientRepository;
    @Getter
    private static Map<String, ColumnMetadata> columnMetadataMap;
    @Getter
    private static final List<DateTimeFormatter> df = new ArrayList<>();

    static {
        df.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA));
    }

    public OAuth2SqliteAuthorizationRowMapper(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;

        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(securityModules);
        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        initColumnMetadata(jdbcOperations);
    }

    @Override
    public OAuth2Authorization mapRow(ResultSet rs, int rowNum) throws SQLException {
        String registeredClientId = rs.getString("registered_client_id");
        RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }

        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);
        String id = rs.getString("id");
        String principalName = rs.getString("principal_name");
        String authorizationGrantType = rs.getString("authorization_grant_type");
        Set<String> authorizedScopes = Collections.emptySet();
        String authorizedScopesString = rs.getString("authorized_scopes");
        if (authorizedScopesString != null) {
            authorizedScopes = StringUtils.commaDelimitedListToSet(authorizedScopesString);
        }
        Map<String, Object> attributes = parseMap(getLobValue(rs, "attributes"));

        builder.id(id)
                .principalName(principalName)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .authorizedScopes(authorizedScopes)
                .attributes((attrs) -> attrs.putAll(attributes));

        String state = rs.getString("state");
        if (StringUtils.hasText(state)) {
            builder.attribute(OAuth2ParameterNames.STATE, state);
        }

        Instant tokenIssuedAt;
        Instant tokenExpiresAt;
        String authorizationCodeValue = getLobValue(rs, "authorization_code_value");

        if (StringUtils.hasText(authorizationCodeValue)) {
            tokenIssuedAt = getTimestampValue(rs, "authorization_code_issued_at");
            tokenExpiresAt = getTimestampValue(rs, "authorization_code_expires_at");
            Map<String, Object> authorizationCodeMetadata = parseMap(getLobValue(rs, "authorization_code_metadata"));

            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    authorizationCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(authorizationCode, (metadata) -> metadata.putAll(authorizationCodeMetadata));
        }

        String accessTokenValue = getLobValue(rs, "access_token_value");
        if (StringUtils.hasText(accessTokenValue)) {
            tokenIssuedAt = getTimestampValue(rs, "access_token_issued_at");
            tokenExpiresAt = getTimestampValue(rs, "access_token_expires_at");
            Map<String, Object> accessTokenMetadata = parseMap(getLobValue(rs, "access_token_metadata"));
            OAuth2AccessToken.TokenType tokenType = null;
            if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(rs.getString("access_token_type"))) {
                tokenType = OAuth2AccessToken.TokenType.BEARER;
            }

            Set<String> scopes = Collections.emptySet();
            String accessTokenScopes = rs.getString("access_token_scopes");
            if (accessTokenScopes != null) {
                scopes = StringUtils.commaDelimitedListToSet(accessTokenScopes);
            }
            OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, accessTokenValue, tokenIssuedAt, tokenExpiresAt, scopes);
            builder.token(accessToken, (metadata) -> metadata.putAll(accessTokenMetadata));
        }

        String oidcIdTokenValue = getLobValue(rs, "oidc_id_token_value");
        if (StringUtils.hasText(oidcIdTokenValue)) {
            tokenIssuedAt = getTimestampValue(rs, "oidc_id_token_issued_at");
            tokenExpiresAt = getTimestampValue(rs, "oidc_id_token_expires_at");
            Map<String, Object> oidcTokenMetadata = parseMap(getLobValue(rs, "oidc_id_token_metadata"));

            OidcIdToken oidcToken = new OidcIdToken(
                    oidcIdTokenValue, tokenIssuedAt, tokenExpiresAt, (Map<String, Object>) oidcTokenMetadata.get(OAuth2Authorization.Token.CLAIMS_METADATA_NAME));
            builder.token(oidcToken, (metadata) -> metadata.putAll(oidcTokenMetadata));
        }

        String refreshTokenValue = getLobValue(rs, "refresh_token_value");
        if (StringUtils.hasText(refreshTokenValue)) {
            tokenIssuedAt = getTimestampValue(rs, "refresh_token_issued_at");
            tokenExpiresAt = getTimestampValue(rs, "refresh_token_expires_at");
            ;
//            Timestamp refreshTokenExpiresAt = rs.getTimestamp("refresh_token_expires_at");
//            if (refreshTokenExpiresAt != null) {
//                tokenExpiresAt = refreshTokenExpiresAt.toInstant();
//            }
            Map<String, Object> refreshTokenMetadata = parseMap(getLobValue(rs, "refresh_token_metadata"));

            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    refreshTokenValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(refreshToken, (metadata) -> metadata.putAll(refreshTokenMetadata));
        }

        String userCodeValue = getLobValue(rs, "user_code_value");
        if (StringUtils.hasText(userCodeValue)) {
            tokenIssuedAt = getTimestampValue(rs, "user_code_issued_at");
            tokenExpiresAt = getTimestampValue(rs, "user_code_expires_at");
            Map<String, Object> userCodeMetadata = parseMap(getLobValue(rs, "user_code_metadata"));

            OAuth2UserCode userCode = new OAuth2UserCode(userCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(userCode, (metadata) -> metadata.putAll(userCodeMetadata));
        }

        String deviceCodeValue = getLobValue(rs, "device_code_value");
        if (StringUtils.hasText(deviceCodeValue)) {
            tokenIssuedAt = getTimestampValue(rs, "device_code_issued_at");
            tokenExpiresAt = getTimestampValue(rs, "device_code_expires_at");
            Map<String, Object> deviceCodeMetadata = parseMap(getLobValue(rs, "device_code_metadata"));

            OAuth2DeviceCode deviceCode = new OAuth2DeviceCode(deviceCodeValue, tokenIssuedAt, tokenExpiresAt);
            builder.token(deviceCode, (metadata) -> metadata.putAll(deviceCodeMetadata));
        }

        return builder.build();
    }

    private Instant getTimestampValue(ResultSet rs, String columnLabel) throws SQLException {
        long timestampLong = rs.getLong(columnLabel);
        if (timestampLong == 0) {
            return null;
        }
        return Instant.ofEpochMilli(timestampLong);
//        for (DateTimeFormatter dateFormat : df) {
//            try {
//                return LocalDateTime.parse(timestampStr, dateFormat).toInstant(ZoneOffset.ofHours(8));
//            } catch (Exception ignored) {
//            }
//        }
//        throw new RuntimeException("转换数据库日期时间【" + columnLabel + "】失败");
    }

    private static void initColumnMetadata(JdbcOperations jdbcOperations) {
        columnMetadataMap = new HashMap<>();
        ColumnMetadata columnMetadata;

        columnMetadata = getColumnMetadata(jdbcOperations, "attributes", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "authorization_code_value", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "authorization_code_metadata", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "access_token_value", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "access_token_metadata", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "oidc_id_token_value", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "oidc_id_token_metadata", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "refresh_token_value", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "refresh_token_metadata", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "user_code_value", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "user_code_metadata", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "device_code_value", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
        columnMetadata = getColumnMetadata(jdbcOperations, "device_code_metadata", Types.BLOB);
        columnMetadataMap.put(columnMetadata.getColumnName(), columnMetadata);
    }

    private static ColumnMetadata getColumnMetadata(JdbcOperations jdbcOperations, String columnName, int defaultDataType) {
        Integer dataType = jdbcOperations.execute((ConnectionCallback<Integer>) conn -> {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet rs = databaseMetaData.getColumns(null, null, TABLE_NAME, columnName);
            if (rs.next()) {
                return rs.getInt("DATA_TYPE");
            }
            // NOTE: (Applies to HSQL)
            // When a database object is created with one of the CREATE statements or renamed with the ALTER statement,
            // if the name is enclosed in double quotes, the exact name is used as the case-normal form.
            // But if it is not enclosed in double quotes,
            // the name is converted to uppercase and this uppercase version is stored in the database as the case-normal form.
            rs = databaseMetaData.getColumns(null, null, TABLE_NAME.toUpperCase(), columnName.toUpperCase());
            if (rs.next()) {
                return rs.getInt("DATA_TYPE");
            }
            return null;
        });
        return new ColumnMetadata(columnName, dataType != null ? dataType : defaultDataType);
    }

    private String getLobValue(ResultSet rs, String columnName) throws SQLException {
        String columnValue = null;
        ColumnMetadata columnMetadata = columnMetadataMap.get(columnName);
        if (Types.BLOB == columnMetadata.getDataType()) {
            byte[] columnValueBytes = this.lobHandler.getBlobAsBytes(rs, columnName);
            if (columnValueBytes != null) {
                columnValue = new String(columnValueBytes, StandardCharsets.UTF_8);
            }
        } else if (Types.CLOB == columnMetadata.getDataType()) {
            columnValue = this.lobHandler.getClobAsString(rs, columnName);
        } else {
            columnValue = rs.getString(columnName);
        }
        return columnValue;
    }

    private Map<String, Object> parseMap(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 字段元数据
     */
    private static final class ColumnMetadata {
        private final String columnName;
        private final int dataType;

        private ColumnMetadata(String columnName, int dataType) {
            this.columnName = columnName;
            this.dataType = dataType;
        }

        private String getColumnName() {
            return this.columnName;
        }

        private int getDataType() {
            return this.dataType;
        }

    }
}
