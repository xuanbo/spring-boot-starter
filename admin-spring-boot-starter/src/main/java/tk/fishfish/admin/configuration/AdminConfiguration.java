package tk.fishfish.admin.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import tk.fishfish.admin.security.token.CustomAccessTokenConverter;
import tk.fishfish.admin.security.token.CustomUserAuthenticationConverter;
import tk.fishfish.mybatis.enums.EnableEnumTypes;

/**
 * 后台管理配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@MapperScan(AdminConfiguration.PACKAGE)
@ComponentScan(AdminConfiguration.PACKAGE)
@EnableEnumTypes(AdminConfiguration.PACKAGE)
class AdminConfiguration {

    public static final String PACKAGE = "tk.fishfish.admin";

    @Bean
    public AccessTokenConverter customAccessTokenConverter(ObjectMapper objectMapper) {
        CustomAccessTokenConverter accessTokenConverter = new CustomAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter(objectMapper));
        return accessTokenConverter;
    }

}
