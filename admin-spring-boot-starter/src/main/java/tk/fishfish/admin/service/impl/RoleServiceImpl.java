package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.entity.UserRole;
import tk.fishfish.admin.repository.UserRoleRepository;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.RoleService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.mybatis.mapper.entity.Condition;

import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    private final UserRoleRepository userRoleRepository;

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
    protected void beforeInsert(Role role) {
        role.setCreatedAt(new Date());
        role.setCreatedBy(UserContextHolder.username());
    }

    @Override
    protected void beforeUpdate(Role role) {
        role.setUpdatedAt(new Date());
        role.setUpdatedBy(UserContextHolder.username());
    }

}
