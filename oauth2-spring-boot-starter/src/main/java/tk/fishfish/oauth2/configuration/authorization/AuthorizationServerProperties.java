package tk.fishfish.oauth2.configuration.authorization;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 授权服务器配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@ConfigurationProperties(prefix = "fish.oauth2.authorization")
public class AuthorizationServerProperties {

    /**
     * redis存储token前缀
     */
    private String tokenPrefix = "fish:oauth2:";

    /**
     * 是否支持refresh token
     */
    private Boolean supportRefreshToken = true;

    /**
     * access_token过期时间，2小时
     */
    private Integer accessTokenValiditySeconds = 60 * 60 * 2;

    /**
     * refresh_token过期时间，1天
     */
    private Integer RefreshTokenValiditySeconds = 60 * 60 * 24 * 30;

}
