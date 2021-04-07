package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.fishfish.admin.cache.PermissionCache;
import tk.fishfish.admin.cache.RoleCache;
import tk.fishfish.admin.cache.UserCache;
import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.entity.Permission;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.entity.UserRole;
import tk.fishfish.admin.repository.UserRepository;
import tk.fishfish.admin.repository.UserRoleRepository;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.UserService;
import tk.fishfish.execption.BizException;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Priority;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Service
@Priority(10)
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserCache userCache;
    private final RoleCache roleCache;
    private final PermissionCache permissionCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        userRoleRepository.deleteByUserId(id);
        super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        userRoleRepository.deleteByUserIds(ids);
        super.deleteByIds(ids);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userCache.username(username))
                .orElseThrow(() -> new UsernameNotFoundException("账号不存在: " + username));
        List<Role> roles = roleCache.userId(user.getId());
        Map<String, Object> extra = new HashMap<>(4);
        extra.put("user", user);
        extra.put("roles", roles);
        List<String> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        Set<String> authorities = roles.stream().map(Role::getCode).map(e -> "ROLE_" + e).collect(Collectors.toSet());
        if (!roleIds.isEmpty()) {
            List<Permission> permissions = permissionCache.roleIds(roleIds);
            authorities.addAll(permissions.stream().map(Permission::getId).collect(Collectors.toList()));
        }
        extra.put("authorities", authorities);
        return DefaultUserDetails.of(user, extra);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = Optional.ofNullable(userCache.username(username))
                .orElseThrow(() -> BizException.of(404, "账号不存在: %s", username));
        boolean matches = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw BizException.of(400, "旧密码验证不通过");
        }
        String password = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(username, password);
        // 清除缓存
        userCache.evictByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grant(String id, List<Select<String>> selects) {
        if (CollectionUtils.isEmpty(selects)) {
            return;
        }
        List<UserRole> selected = new LinkedList<>();
        List<String> unSelected = new LinkedList<>();
        for (Select<String> select : selects) {
            if (select.isSelected()) {
                UserRole userRole = new UserRole();
                userRole.setId(generateId());
                userRole.setUserId(id);
                userRole.setRoleId(select.getData());
                selected.add(userRole);
            } else {
                unSelected.add(select.getData());
            }
        }
        if (!selected.isEmpty()) {
            log.info("用户授权角色，新增 {} 条，id: {}", selected.size(), id);
            userRoleRepository.insertList(selected);
        }
        if (!unSelected.isEmpty()) {
            log.info("用户授权角色，取消 {} 条，id: {}", unSelected.size(), id);
            userRoleRepository.deleteByUserIdAndRoleIds(id, unSelected);
        }
        // 清除缓存
        roleCache.evictByUserId(id);
    }

    @Override
    protected void beforeInsert(User user) {
        if (userCache.username(user.getUsername()) != null) {
            throw BizException.of(400, "账号重复: %s", user.getUsername());
        }
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

    @Override
    protected void afterDelete(String id) {
        // user 存在 username 缓存未清除，可以容忍
        userCache.evictById(id);
        roleCache.evictByUserId(id);
    }

    @Override
    protected void afterDelete(List<String> ids) {
        // user 存在 username 缓存未清除，可以容忍
        userCache.evictByIds(ids);
        roleCache.evictByUserIds(ids);
    }

    @Override
    protected void afterDelete(Condition condition) {
        // 可以容忍短时间脏读 userCache.evict(); roleCache.evict();
    }

}
