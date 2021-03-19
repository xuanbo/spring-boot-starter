package tk.fishfish.codegen.config;

import com.zaxxer.hikari.HikariDataSource;
import jetbrick.template.JetEngine;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import tk.fishfish.codegen.config.datasource.DynamicDataSource;

import javax.sql.DataSource;

/**
 * 代码生成器配置
 *
 * @author 奔波儿灞
 * @since 1.5.0
 */
@Configuration
@EnableConfigurationProperties(CodegenProperties.class)
public class CodegenConfiguration {

    /**
     * 模板引擎
     *
     * @return JetEngine
     */
    @Bean
    public JetEngine engine() {
        return JetEngine.create();
    }

    /**
     * 动态数据源
     *
     * @param properties 默认数据源配置
     * @return DynamicDataSource
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DynamicDataSource dataSource(DataSourceProperties properties) {
        HikariDataSource defaultDataSource = createDataSource(properties, HikariDataSource.class);
        if (StringUtils.hasText(properties.getName())) {
            defaultDataSource.setPoolName(properties.getName());
        }
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        return dynamicDataSource;
    }

    @SuppressWarnings("all")
    private static <T> T createDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
        return (T) properties.initializeDataSourceBuilder().type(type).build();
    }

}
