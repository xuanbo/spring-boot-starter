package tk.fishfish.rest.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.rest.ApiResultResponseBodyAdvice;

/**
 * 统一返回值包装
 *
 * @author 奔波儿灞
 * @since 1.0.0
 */
@Configuration
public class ApiResultResponseAutoConfiguration {

    @Bean
    public ApiResultResponseBodyAdvice apiResultResponseBodyAdvice() {
        return new ApiResultResponseBodyAdvice();
    }

}
