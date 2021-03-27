package tk.fishfish.mybatis.autoconfigure;

import org.apache.ibatis.type.TypeHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.mybatis.condition.ConditionParser;
import tk.fishfish.mybatis.condition.DefaultConditionParser;
import tk.fishfish.mybatis.type.StringArrayTypeHandler;

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

    @Bean
    public TypeHandler<String[]> stringArrayTypeHandler() {
        return new StringArrayTypeHandler();
    }

}
