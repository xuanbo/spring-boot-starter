package tk.fishfish.oauth2.configuration.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.Optional;

/**
 * 配置远程token访问
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
public class ResourceConfiguration {

    @Autowired
    private ResourceProperties properties;

    @Autowired
    private AccessTokenConverter accessTokenConverter;

    @Bean
    @ConditionalOnMissingBean(AuthorizationServerTokenServices.class)
    private ResourceServerTokenServices remoteTokenServices() {
        log.warn("由于你没有自定义 {} , 默认配置 {} 进行远程访问", AuthorizationServerTokenServices.class.getName(), RemoteTokenServices.class.getName());
        ResourceProperties.Remote remote = Optional.ofNullable(properties.getRemote())
                .orElse(new ResourceProperties.Remote());
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(remote.getCheckTokenEndpointUrl());
        tokenServices.setClientId(remote.getClientId());
        tokenServices.setClientSecret(remote.getClientSecret());
        tokenServices.setAccessTokenConverter(accessTokenConverter);
        return tokenServices;
    }

}
