package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.entity.UserRole;
import tk.fishfish.admin.repository.UserRepository;
import tk.fishfish.admin.repository.UserRoleRepository;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.UserService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.mybatis.mapper.entity.Condition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void deleteById(String id) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andEqualTo("userId", id);
        userRoleRepository.deleteByExample(condition);
        super.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andIn("userId", ids);
        userRoleRepository.deleteByExample(condition);
        super.deleteByIds(ids);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.loadByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("账号不存在: " + username));
        Map<String, Object> extra = new HashMap<>();
        extra.put("user", user);
        return DefaultUserDetails.of(user, extra);
    }

    @Override
    protected void beforeInsert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setCreatedAt(new Date());
        user.setCreatedBy(UserContextHolder.username());
    }

    @Override
    protected void beforeUpdate(User user) {
        user.setUpdatedAt(new Date());
        user.setUpdatedBy(UserContextHolder.username());
    }

}
