package tk.fishfish.admin.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import tk.fishfish.admin.dto.UserPermission;
import tk.fishfish.admin.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 默认 {@link UserDetails} 实现
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultUserDetails extends org.springframework.security.core.userdetails.User {

    private String id;

    private String name;

    /**
     * 用户额外信息
     */
    private Map<String, Object> extra;

    private DefaultUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public static DefaultUserDetails of(User user) {
        boolean enable = Optional.ofNullable(user.getEnable())
                .orElse(true);
        boolean accountNonExpired = false;
        Date accountExpireAt = user.getAccountExpireAt();
        if (accountExpireAt == null || accountExpireAt.after(new Date())) {
            accountNonExpired = true;
        }
        boolean credentialsNonExpired = false;
        Date credentialsExpireAt = user.getCredentialsExpireAt();
        if (credentialsExpireAt == null || credentialsExpireAt.after(new Date())) {
            credentialsNonExpired = true;
        }
        boolean accountLock = Optional.ofNullable(user.getAccountLock())
                .orElse(false);
        DefaultUserDetails userDetails;
        if (user instanceof UserPermission) {
            List<SimpleGrantedAuthority> authorities = new LinkedList<>();
            UserPermission userPermission = (UserPermission) user;
            // 角色
            List<String> roles = userPermission.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                authorities.addAll(roles.stream().map(e -> "ROLE_" + e).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
            // 权限
            List<String> permissions = userPermission.getPermissions();
            if (!CollectionUtils.isEmpty(roles)) {
                authorities.addAll(permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
            userDetails = new DefaultUserDetails(user.getUsername(), user.getPassword(), enable, accountNonExpired, credentialsNonExpired, !accountLock, authorities);
        } else {
            userDetails = new DefaultUserDetails(user.getUsername(), user.getPassword(), enable, accountNonExpired, credentialsNonExpired, !accountLock, Collections.emptyList());
        }
        userDetails.setId(user.getId());
        userDetails.setName(user.getName());
        userDetails.setExtra(new HashMap<String, Object>() {{
            put("user", user);
        }});
        return userDetails;
    }

}
