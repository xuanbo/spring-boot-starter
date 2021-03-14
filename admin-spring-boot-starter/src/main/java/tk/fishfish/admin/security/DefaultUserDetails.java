package tk.fishfish.admin.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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

    public static DefaultUserDetails of(User user, Map<String, Object> extra) {
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
        // 权限
        List<SimpleGrantedAuthority> authorities = Optional.ofNullable(user.getRoles())
                .map(roles -> roles.stream().map(Role::getCode).map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        DefaultUserDetails details = new DefaultUserDetails(user.getUsername(), user.getPassword(), enable, accountNonExpired, credentialsNonExpired, !accountLock, authorities);
        // 用户信息
        details.setId(user.getId());
        // 用户额外信息
        details.setExtra(extra);
        return details;
    }

}
