package tk.fishfish.mybatis.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.util.Snowflake;

/**
 * 雪花id配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Configuration
public class SnowflakeAutoConfiguration {

    @Value("${fish.snowflake.datacenterId:1}")
    protected Long datacenterId;

    @Value("${fish.snowflake.workerId:1}")
    protected Long workerId;

    /**
     * Snowflake配置
     *
     * @return Snowflake
     */
    @Bean
    public Snowflake snowflake() {
        return new Snowflake(datacenterId, workerId);
    }

}
