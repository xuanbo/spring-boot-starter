package tk.fishfish.oauth2.configuration.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

/**
 * token转换配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
public class TokenConverterConfiguration {

    @Bean
    @ConditionalOnClass
    public AccessTokenConverter accessTokenConverter() {
        log.warn("由于你没有自定义 {} , 默认配置 {} 进行属性转换", AccessTokenConverter.class.getName(), DefaultAccessTokenConverter.class.getName());
        return new DefaultAccessTokenConverter();
    }

}
