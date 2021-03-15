package tk.fishfish.oauth2.configuration.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 资源服务器配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Data
@ConfigurationProperties(prefix = "fish.oauth2.resource")
public class ResourceProperties {

    /**
     * 配置该服务的资源ID，如果认证不匹配则无法访问
     */
    private String resourceId = "fish";

    /**
     * 不保护的资源路径，ant风格，比如/v0/**
     */
    private String[] ignorePatterns;

    /**
     * 远程校验token
     */
    private Remote remote;

    @Data
    public static class Remote {

        /**
         * 校验token地址
         */
        private String checkTokenEndpointUrl = "http://127.0.0.1:8080/oauth/check_token";

        private String clientId = "client";

        private String clientSecret = "secret";

    }

}
