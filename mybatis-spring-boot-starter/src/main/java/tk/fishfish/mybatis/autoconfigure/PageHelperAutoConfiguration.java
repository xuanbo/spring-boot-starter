package tk.fishfish.mybatis.autoconfigure;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * PageHelper自动配置
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Configuration
@AutoConfigureBefore(org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration.class)
public class PageHelperAutoConfiguration {

    private static final String PAGE_HELPER_PREFIX = "mybatis.page-helper";

    /**
     * 分⻚插件配置
     *
     * @return Properties
     */
    @Bean
    @ConfigurationProperties(PAGE_HELPER_PREFIX)
    public Properties pageProperties() {
        return new Properties();
    }

    /**
     * 注册分页插件
     *
     * @return PageInterceptor
     */
    @Bean
    public Interceptor pageInterceptor(Properties pageProperties) {
        Interceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(pageProperties);
        return pageInterceptor;
    }

}
