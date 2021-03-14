package tk.fishfish.admin.service.impl;

import org.springframework.stereotype.Service;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.RoleService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;

import java.util.Date;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

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
