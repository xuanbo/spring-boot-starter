package tk.fishfish.oauth2.configuration.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import tk.fishfish.oauth2.configuration.token.TokenConverterConfiguration;

import java.util.Optional;

/**
 * 资源服务器，负责提供受保护资源，需要到授权服务器进行token验证
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Order(6)
@EnableResourceServer
@EnableConfigurationProperties(ResourceProperties.class)
@Import({
        TokenConverterConfiguration.class,
        ResourceConfiguration.class
})
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final String[] defaultIgnorePatterns = new String[]{
            "/login", "/logout", "/actuator/**",
            "/favicon.ico", "/",
            "/v0/**"
    };

    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Autowired
    private ResourceProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] ignorePatterns = Optional.ofNullable(properties.getIgnorePatterns())
                .orElse(new String[]{});
        http.authorizeRequests()
                .antMatchers(defaultIgnorePatterns).permitAll()
                .antMatchers(ignorePatterns).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 配置该服务的资源ID，如果认证不匹配则无法访问
        resources.resourceId(properties.getResourceId())
                // 如果需要将资源服务器与授权服务器分开，则配置RemoteTokenServices，进行远程访问（微服务）
                .tokenServices(tokenServices);
    }

}
