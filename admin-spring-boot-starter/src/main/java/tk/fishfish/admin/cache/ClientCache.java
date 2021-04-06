package tk.fishfish.admin.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.fishfish.admin.entity.Client;
import tk.fishfish.admin.repository.ClientRepository;
import tk.fishfish.redis.cache.RedisCache;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户端缓存
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Component
public class ClientCache extends RedisCache<Client> {

    @Autowired
    private ClientRepository clientRepository;

    public Client id(String id) {
        return loadByKey("id:" + id, key -> clientRepository.selectByPrimaryKey(id));
    }

    public void evictById(String id) {
        this.evict("id:" + id);
    }

    public void evictByIds(List<String> ids) {
        List<String> keys = ids.stream().map(id -> "id:" + id).collect(Collectors.toList());
        this.evict(keys);
    }

    @Override
    protected String prefix() {
        return Client.NAME + ":";
    }

}
