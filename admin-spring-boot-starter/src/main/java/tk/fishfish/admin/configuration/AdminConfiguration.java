package tk.fishfish.admin.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import tk.fishfish.admin.security.token.CustomAccessTokenConverter;
import tk.fishfish.admin.security.token.CustomUserAuthenticationConverter;
import tk.fishfish.oauth2.provider.ClientDetailsServiceProvider;

/**
 * 后台管理配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@MapperScan(AdminConfiguration.PACKAGE)
@ComponentScan(AdminConfiguration.PACKAGE)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AdminConfiguration {

    public static final String PACKAGE = "tk.fishfish.admin";

    /**
     * 自定义AccessTokenConverter，让程序在单体、微服务情况下都能获取额外的信息
     *
     * @return CustomAccessTokenConverter
     */
    @Bean
    public AccessTokenConverter customAccessTokenConverter() {
        CustomAccessTokenConverter accessTokenConverter = new CustomAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        return accessTokenConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientDetailsServiceProvider clientDetailsServiceProvider(PasswordEncoder passwordEncoder) {
        return clientId -> {
            BaseClientDetails details = new BaseClientDetails(clientId, "fish", "read,write", "password,refresh_token,authorization_code", "");
            details.setClientSecret(passwordEncoder.encode("secret"));
            return details;
        };
    }

}
