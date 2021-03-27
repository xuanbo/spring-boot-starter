package tk.fishfish.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import tk.fishfish.admin.entity.Client;
import tk.fishfish.admin.entity.enums.ClientStatus;
import tk.fishfish.admin.repository.ClientRepository;
import tk.fishfish.admin.security.DefaultClientDetails;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.security.exception.ClientExpiredException;
import tk.fishfish.admin.security.exception.ClientStatusException;
import tk.fishfish.admin.service.ClientService;
import tk.fishfish.mybatis.service.impl.BaseServiceImpl;
import tk.fishfish.oauth2.provider.ClientDetailsServiceProvider;

import javax.annotation.Priority;
import java.util.Date;
import java.util.Optional;

/**
 * 客户端
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Service
@Priority(10)
@RequiredArgsConstructor
public class ClientServiceImpl extends BaseServiceImpl<Client> implements ClientService, ClientDetailsServiceProvider {

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = Optional.ofNullable(clientRepository.findById(clientId))
                .orElseThrow(() -> new NoSuchClientException("客户端不存在: " + clientId));
        if (client.getStatus() != ClientStatus.PASS) {
            throw new ClientStatusException("客户端未通过审核: " + clientId);
        }
        Date expireAt = client.getExpireAt();
        if (expireAt != null && expireAt.before(new Date())) {
            throw new ClientExpiredException("客户端已过期: " + clientId);
        }
        return DefaultClientDetails.of(client);
    }

    @Override
    protected void beforeInsert(Client client) {
        String secret = passwordEncoder.encode(client.getSecret());
        client.setSecret(secret);
        client.setCreatedAt(new Date());
        client.setCreatedBy(UserContextHolder.username());
        if (client.getStatus() == null) {
            client.setStatus(ClientStatus.APPLY);
        }
    }

    @Override
    protected void beforeUpdate(Client client) {
        client.setUpdatedAt(new Date());
        client.setUpdatedBy(UserContextHolder.username());
    }

}
