package tk.fishfish.oauth2.provider;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * OAuth2 认证提供，用于重建 OAuth2Authentication 信息
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@FunctionalInterface
public interface OAuth2AuthenticationProvider {

    OAuth2Authentication renew(OAuth2Authentication oldOAuth2Authentication);

}
