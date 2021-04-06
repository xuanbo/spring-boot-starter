package tk.fishfish.admin.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.repository.RoleRepository;
import tk.fishfish.redis.cache.RedisCache;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色缓存
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Component
public class RoleCache extends RedisCache<Role> {

    @Autowired
    private RoleRepository roleRepository;

    public Role code(String code) {
        return loadByKey("code:" + code, key -> roleRepository.findByCode(code));
    }

    public void evictByCode(String code) {
        this.evict("code:" + code);
    }

    public List<Role> userId(String userId) {
        return loadListByKey("user-id:" + userId, key -> roleRepository.findByUserId(userId));
    }

    public void evictByUserId(String userId) {
        this.evict("user-id:" + userId);
    }

    public void evictByUserIds(List<String> userIds) {
        List<String> keys = userIds.stream().map(userId -> "user-id:" + userId).collect(Collectors.toList());
        this.evict(keys);
    }

    @Override
    protected String prefix() {
        return Role.NAME + ":";
    }

}
