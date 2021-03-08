package tk.fishfish.rest.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.rest.GlobalExceptionHandler;

/**
 * 全局异常处理配置
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Configuration
public class GlobalExceptionHandlerAutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
