package tk.fishfish.admin.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.repository.UserRepository;
import tk.fishfish.redis.cache.RedisCache;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户缓存
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Component
public class UserCache extends RedisCache<User> {

    @Autowired
    private UserRepository userRepository;

    public User id(String id) {
        return loadByKey("id:" + id, key -> userRepository.selectByPrimaryKey(id));
    }

    public void evictById(String id) {
        this.evict("id:" + id);
    }

    public void evictByIds(List<String> ids) {
        List<String> keys = ids.stream().map(id -> "id:" + id).collect(Collectors.toList());
        this.evict(keys);
    }

    public User username(String username) {
        return loadByKey("username:" + username, key -> userRepository.findByUsername(username));
    }

    public void evictByUsername(String username) {
        this.evict("username:" + username);
    }

    @Override
    protected String prefix() {
        return User.NAME + ":";
    }

}
