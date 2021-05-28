package tk.fishfish.admin.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Component;
import tk.fishfish.admin.entity.Client;
import tk.fishfish.admin.entity.enums.ClientStatus;
import tk.fishfish.admin.security.DefaultClientDetails;
import tk.fishfish.admin.security.exception.ClientExpiredException;
import tk.fishfish.admin.security.exception.ClientStatusException;
import tk.fishfish.admin.service.ClientService;
import tk.fishfish.oauth2.provider.ClientDetailsServiceProvider;

import java.util.Date;
import java.util.Optional;

/**
 * 客户端提供
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Component
public class ClientDetailsServiceImpl implements ClientDetailsServiceProvider {

    @Autowired
    private ClientService clientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = Optional.ofNullable(clientService.findById(clientId))
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

}
