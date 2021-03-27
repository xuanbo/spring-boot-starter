package tk.fishfish.oauth2.token;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.nio.charset.StandardCharsets;

/**
 * 基于Redis管理授权码
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RequiredArgsConstructor
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private static final String CODE = "code:";

    private final RedisConnectionFactory connectionFactory;

    @Setter
    private String prefix = "";

    /**
     * 过期时间，默认10分钟
     */
    @Setter
    private Integer codeValiditySeconds = 10 * 60;

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        try (RedisConnection connection = getConnection()) {
            connection.set(
                    serializeKey(code), SerializationUtils.serialize(authentication),
                    Expiration.seconds(codeValiditySeconds), RedisStringCommands.SetOption.SET_IF_ABSENT
            );
        }
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        try (RedisConnection connection = getConnection()) {
            byte[] value = connection.get(serializeKey(code));
            if (value == null) {
                return null;
            }
            return SerializationUtils.deserialize(value);
        }
    }

    private RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    private byte[] serializeKey(String code) {
        return (prefix + CODE + code).getBytes(StandardCharsets.UTF_8);
    }

}
