package tk.fishfish.oauth2.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import tk.fishfish.oauth2.provider.OAuth2AuthenticationProvider;

/**
 * 实现 DefaultTokenServices 服务
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class CustomTokenServices extends DefaultTokenServices {

    @Getter
    @Setter
    private OAuth2AuthenticationProvider oAuth2AuthenticationProvider;

    @Override
    public OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException, InvalidTokenException {
        OAuth2Authentication authentication = super.loadAuthentication(accessTokenValue);
        if (oAuth2AuthenticationProvider == null) {
            return authentication;
        }
        // 从 spring security 中获取到 authentication 后（一般从缓存中直接取出），可能权限变更了，需要进行重建
        try {
            return oAuth2AuthenticationProvider.renew(authentication);
        } catch (Exception e) {
            throw new InvalidTokenException("Load authentication failed", e);
        }
    }

}
