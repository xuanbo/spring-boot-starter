package tk.fishfish.admin.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.fishfish.admin.entity.Permission;
import tk.fishfish.admin.repository.PermissionRepository;
import tk.fishfish.redis.cache.RedisCache;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限缓存
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Component
public class PermissionCache extends RedisCache<Permission> {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> roleIds(List<String> roleIds) {
        List<String> keys = roleIds.stream().map(roleId -> "role-id:" + roleId).collect(Collectors.toList());
        return loadListByKeys(keys, key -> {
            List<Permission> permissions = permissionRepository.findByRoleIds(roleIds);
            return permissions.stream().peek(permission -> {
                String roleId = permission.getRoleId();
                permission.setRoleId("role-id:" + roleId);
            }).collect(Collectors.groupingBy(Permission::getRoleId));
        });
    }

    public void evictByRoleId(String roleId) {
        evict("role-id:" + roleId);
    }

    public void evictByRoleIds(List<String> roleIds) {
        List<String> keys = roleIds.stream().map(roleId -> "role-id:" + roleId).collect(Collectors.toList());
        evict(keys);
    }

    @Override
    protected String prefix() {
        return Permission.NAME + ":";
    }

}
