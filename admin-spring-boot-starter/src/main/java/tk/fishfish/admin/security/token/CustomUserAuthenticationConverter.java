package tk.fishfish.admin.security.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.rest.BizException;

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

    private final ObjectMapper objectMapper;

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
            User user = read(write(((Map<?, ?>) extra).get("user")), User.class);
            user.setPassword("N/A");
            DefaultUserDetails principal = DefaultUserDetails.of(user, (Map<String, Object>) extra);
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authentication.getAuthorities());
        }
        return authentication;
    }

    private <T> T read(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw BizException.of(500, "读取json错误", e);
        }
    }

    private <T> String write(T obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw BizException.of(500, "写入json错误", e);
        }
    }

}
