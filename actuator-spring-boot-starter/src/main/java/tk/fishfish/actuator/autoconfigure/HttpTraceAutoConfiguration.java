package tk.fishfish.actuator.autoconfigure;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.actuator.http.HttpTraceRepositoryImpl;

/**
 * http trace自动配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "management.trace.http", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(ActuatorProperties.class)
@AutoConfigureBefore(org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceAutoConfiguration.class)
public class HttpTraceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpTraceRepository HttpTraceRepository(ActuatorProperties properties) {
        return new HttpTraceRepositoryImpl(properties.getHttp().getTimeout());
    }

}
