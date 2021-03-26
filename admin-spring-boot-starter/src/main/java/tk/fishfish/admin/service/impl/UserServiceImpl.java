package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.entity.UserRole;
import tk.fishfish.admin.repository.RoleRepository;
import tk.fishfish.admin.repository.UserRepository;
import tk.fishfish.admin.repository.UserRoleRepository;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.UserService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.fishfish.rest.execption.BizException;
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
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andEqualTo("userId", id);
        userRoleRepository.deleteByExample(condition);
        super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andIn("userId", ids);
        userRoleRepository.deleteByExample(condition);
        super.deleteByIds(ids);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("账号不存在: " + username));
        List<Role> roles = roleRepository.findByUserId(user.getId());
        Map<String, Object> extra = new HashMap<>(4);
        extra.put("user", user);
        extra.put("roles", roles);
        return DefaultUserDetails.of(user, roles, extra);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> BizException.of(404, "账号不存在: %s", username));
        boolean matches = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw BizException.of(400, "旧密码验证不通过");
        }
        String password = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(username, password);
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
