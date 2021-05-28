package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.dto.UserPermission;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.entity.UserRole;
import tk.fishfish.admin.repository.UserRepository;
import tk.fishfish.admin.repository.UserRoleRepository;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.UserService;
import tk.fishfish.execption.BizException;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;

import javax.annotation.Priority;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
@CacheConfig(cacheNames = User.NAME)
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

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
    @Cacheable(key = "'username:' + #p0 + ':details'")
    public UserPermission loadByUsername(String username) {
        return userRepository.loadByUsername(username);
    }

    @Override
    @CacheEvict(key = "'username:' + #p0")
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
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "username", allEntries = true)
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
    }

    @Override
    public void beforeInsert(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw BizException.of(400, "账号重复: %s", user.getUsername());
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setCreatedAt(new Date());
        user.setCreatedBy(UserContextHolder.username());
    }

    @Override
    public void beforeUpdate(User user) {
        user.setUpdatedAt(new Date());
        user.setUpdatedBy(UserContextHolder.username());
    }

}
