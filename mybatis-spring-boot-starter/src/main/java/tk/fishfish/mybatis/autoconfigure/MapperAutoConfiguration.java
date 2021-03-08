package tk.fishfish.mybatis.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.util.Properties;

/**
 * 通用Mapper自动配置
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Configuration
@AutoConfigureBefore(org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration.class)
public class MapperAutoConfiguration {

    private static final String MAPPER_HELPER_PREFIX = "mybatis.mapper";

    /**
     * 通用mapper的配置
     *
     * @return Properties
     */
    @Bean
    @ConfigurationProperties(MAPPER_HELPER_PREFIX)
    public Properties mapperProperties() {
        return new Properties();
    }

    @Bean
    public MapperHelper mapperHelper(Properties mapperProperties) {
        return new MapperHelper(mapperProperties);
    }

}
