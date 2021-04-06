package tk.fishfish.admin.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.fishfish.admin.entity.User;
import tk.fishfish.rest.execption.BizException;

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

    @SuppressWarnings("unchecked")
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
        List<SimpleGrantedAuthority> grantedAuthorities;
        Object authorities = extra.get("authorities");
        if (authorities == null) {
            grantedAuthorities = Collections.emptyList();
        } else {
            if (authorities instanceof List) {
                grantedAuthorities = ((List<String>) authorities).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            } else {
                throw BizException.of(500, "不正确的权限配置");
            }
        }
        DefaultUserDetails details = new DefaultUserDetails(user.getUsername(), user.getPassword(), enable, accountNonExpired, credentialsNonExpired, !accountLock, grantedAuthorities);
        // 用户信息
        details.setId(user.getId());
        // 用户额外信息
        details.setExtra(extra);
        return details;
    }

}
