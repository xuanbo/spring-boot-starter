package tk.fishfish.admin.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tk.fishfish.admin.dto.UserPermission;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.admin.service.UserService;

import java.util.Optional;

/**
 * 用户信息提供
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPermission user = Optional.ofNullable(userService.loadByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("账号不存在: " + username));
        return DefaultUserDetails.of(user);
    }

}
