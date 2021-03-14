package tk.fishfish.admin.security.token;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import tk.fishfish.admin.security.DefaultUserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * POST /oauth/check_token 时放入额外信息
 * <pre>
 * {
 *     "aud": [
 *         "fish"
 *     ],
 *     "user_name": "admin",
 *     "scope": [
 *         "read",
 *         "write"
 *     ],
 *     // 额外信息
 *     "extra": {
 *     },
 *     "active": true,
 *     "exp": 1615764082,
 *     "client_id": "client"
 * }
 * </pre>
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> map = new HashMap<>(super.convertAccessToken(token, authentication));
        if (authentication.getPrincipal() instanceof DefaultUserDetails) {
            Map<String, Object> extra = ((DefaultUserDetails) authentication.getPrincipal()).getExtra();
            map.put("extra", extra);
        }
        return map;
    }

}
