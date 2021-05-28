package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.fishfish.admin.entity.Client;
import tk.fishfish.admin.entity.enums.ClientStatus;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.ClientService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;

import javax.annotation.Priority;
import java.util.Date;
import java.util.List;

/**
 * 客户端
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
@Priority(10)
@RequiredArgsConstructor
@CacheConfig(cacheNames = Client.NAME)
public class ClientServiceImpl extends BaseServiceImpl<Client> implements ClientService {

    private final PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(key = "'id:' + #p0")
    public Client findById(String id) {
        return super.findById(id);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(Client client) {
        super.update(client);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void updateSelective(Client client) {
        super.updateSelective(client);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id", condition = "#p0.id != null")
    @Transactional(rollbackFor = Exception.class)
    public void save(Client client) {
        super.save(client);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0")
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    @CacheEvict(key = "id", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        super.deleteByIds(ids);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Object condition) {
        super.delete(condition);
    }

    @Override
    public void beforeInsert(Client client) {
        String secret = passwordEncoder.encode(client.getSecret());
        client.setSecret(secret);
        client.setCreatedAt(new Date());
        client.setCreatedBy(UserContextHolder.username());
        if (client.getStatus() == null) {
            client.setStatus(ClientStatus.APPLY);
        }
    }

    @Override
    public void beforeUpdate(Client client) {
        client.setUpdatedAt(new Date());
        client.setUpdatedBy(UserContextHolder.username());
    }

}
