package tk.fishfish.admin.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import tk.fishfish.admin.dto.UserPermission;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.admin.service.UserService;
import tk.fishfish.oauth2.provider.OAuth2AuthenticationProvider;

/**
 * 重建认证，防止 spring security UserDetails 缓存
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Component
public class OAuth2AuthenticationProviderImpl implements OAuth2AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public OAuth2Authentication renew(OAuth2Authentication oldOAuth2Authentication) {
        if (oldOAuth2Authentication.getPrincipal() instanceof DefaultUserDetails) {
            // 查询新的权限
            DefaultUserDetails principal = (DefaultUserDetails) oldOAuth2Authentication.getPrincipal();
            String username = principal.getUsername();
            UserPermission userPermission = userService.loadByUsername(username);
            DefaultUserDetails details = DefaultUserDetails.of(userPermission);
            // 替换认证信息
            Authentication userAuthentication = new UsernamePasswordAuthenticationToken(details, "N/A", details.getAuthorities());
            return new OAuth2Authentication(oldOAuth2Authentication.getOAuth2Request(), userAuthentication);
        }
        return oldOAuth2Authentication;
    }

}
