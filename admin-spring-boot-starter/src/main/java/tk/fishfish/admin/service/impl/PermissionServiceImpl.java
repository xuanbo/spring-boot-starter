package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.admin.entity.Permission;
import tk.fishfish.admin.repository.ResourcePermissionRepository;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.PermissionService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;

import javax.annotation.Priority;
import java.util.Date;
import java.util.List;

/**
 * 权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
@Priority(10)
@RequiredArgsConstructor
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    private final ResourcePermissionRepository resourcePermissionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        resourcePermissionRepository.deleteByPermissionId(id);
        super.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        resourcePermissionRepository.deleteByPermissionIds(ids);
        super.deleteByIds(ids);
    }

    @Override
    public void beforeInsert(Permission permission) {
        permission.setCreatedAt(new Date());
        permission.setCreatedBy(UserContextHolder.username());
    }

    @Override
    public void beforeUpdate(Permission permission) {
        permission.setUpdatedAt(new Date());
        permission.setUpdatedBy(UserContextHolder.username());
    }

}
