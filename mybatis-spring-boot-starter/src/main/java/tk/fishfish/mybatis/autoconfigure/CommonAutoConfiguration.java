package tk.fishfish.mybatis.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.mybatis.condition.ConditionParser;
import tk.fishfish.mybatis.condition.DefaultConditionParser;

/**
 * 通用配置
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@Configuration
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ConditionParser conditionParser() {
        return new DefaultConditionParser();
    }

}
