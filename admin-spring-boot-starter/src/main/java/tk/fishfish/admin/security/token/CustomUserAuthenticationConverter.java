package tk.fishfish.admin.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.json.util.JSON;

import java.util.List;
import java.util.Map;

/**
 * 当认证服务与资源服务分离时，资源服务远程访问认证服务时，此时的Principal为当前用户名
 * 下面让资源服务远程访问认证服务时，也能让Principal为DefaultUserDetails对象
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RequiredArgsConstructor
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Override
    @SuppressWarnings("unchecked")
    public Authentication extractAuthentication(Map<String, ?> map) {
        Authentication authentication = super.extractAuthentication(map);
        Object extra = map.get("extra");
        if (extra == null) {
            return authentication;
        }
        // 下面让资源服务远程访问认证服务时，也能让Principal为DefaultUserDetails对象
        if (extra instanceof Map) {
            // 用户
            String json = JSON.write(((Map<?, ?>) extra).get("user"));
            User user = JSON.read(json, User.class);
            user.setPassword("N/A");
            // 角色
            json = JSON.write(((Map<?, ?>) extra).get("roles"));
            List<Role> roles = JSON.readList(json, Role.class);
            DefaultUserDetails principal = DefaultUserDetails.of(user, roles, (Map<String, Object>) extra);
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authentication.getAuthorities());
        }
        return authentication;
    }

}
