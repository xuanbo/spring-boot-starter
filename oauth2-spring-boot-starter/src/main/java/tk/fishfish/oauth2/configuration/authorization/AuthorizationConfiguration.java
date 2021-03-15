package tk.fishfish.oauth2.configuration.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import tk.fishfish.oauth2.provider.ClientDetailsServiceProvider;
import tk.fishfish.oauth2.token.JdkSerializationStrategy;

import java.util.Collections;

/**
 * 认证配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@EnableConfigurationProperties(AuthorizationProperties.class)
public class AuthorizationConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        // 永远返回当前用户，密码为123456
        log.warn("由于你没有自定义 {} , 默认配置永远返回当前用户，密码为123456的实现", UserDetailsService.class.getName());
        return username -> new User(username, "123456", Collections.emptyList());
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        // 密码永远123456，永远校验通过
        log.warn("由于你没有自定义 {} , 默认配置密码永远123456，永远校验通过的实现", PasswordEncoder.class.getName());
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return "123456";
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return true;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientDetailsServiceProvider clientDetailsServiceProvider() {
        log.warn("由于你没有自定义 {} , 默认配置基于内存的客户端管理", ClientDetailsServiceProvider.class.getName());
        return clientId -> {
            BaseClientDetails details = new BaseClientDetails(clientId, "fish", "read,write", "password,refresh_token", "");
            details.setClientSecret("secret");
            return details;
        };
    }

    @Bean
    public TokenStore tokenStore(
            RedisConnectionFactory redisConnectionFactory,
            AuthorizationProperties properties,
            @Autowired(required = false) RedisTokenStoreSerializationStrategy strategy
    ) {
        if (strategy == null) {
            log.warn("检测到未配置 {} ，默认配置基于 jackson 的序列化规则 {}", RedisTokenStoreSerializationStrategy.class.getName(), JdkSerializationStrategy.class);
            strategy = new JdkSerializationStrategy();
        }
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(properties.getTokenPrefix());
        tokenStore.setSerializationStrategy(strategy);
        return tokenStore;
    }

    @Bean
    public ResourceServerTokenServices tokenServices(
            TokenStore tokenStore,
            ClientDetailsServiceProvider clientDetailsService,
            AuthorizationProperties properties
    ) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(properties.getSupportRefreshToken());
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAccessTokenValiditySeconds(properties.getAccessTokenValiditySeconds());
        tokenServices.setRefreshTokenValiditySeconds(properties.getRefreshTokenValiditySeconds());
        tokenServices.setReuseRefreshToken(false);
        return tokenServices;
    }

}
