package tk.fishfish.oauth2.configuration.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import tk.fishfish.oauth2.configuration.token.TokenConverterConfiguration;
import tk.fishfish.oauth2.provider.ClientDetailsServiceProvider;

/**
 * 授权服务器，负责用户登录、授权、token验证等
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@EnableAuthorizationServer
@Import({
        AuthorizationConfiguration.class,
        WebSecurityConfiguration.class,
        TokenConverterConfiguration.class
})
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    /**
     * 这里自定义ClientDetailsServiceProvider接口继承ClientDetailsService，防止注入JDK代理Bean，导致认证时stackoverflow
     */
    @Autowired
    private ClientDetailsServiceProvider clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AccessTokenConverter accessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 允许form表单获取token
        // POST /oauth/token?client_id=client&client_secret=secret&grant_type=password&username=user&password=123456
        security.allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)
                // 通过验证返回token信息
                .checkTokenAccess("isAuthenticated()")
                // 获取token请求不进行拦截
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter);
    }

}
