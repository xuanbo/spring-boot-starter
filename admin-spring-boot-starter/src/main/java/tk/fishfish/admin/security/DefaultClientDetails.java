package tk.fishfish.admin.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import tk.fishfish.admin.entity.Client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 默认 {@link ClientDetails} 实现
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultClientDetails extends BaseClientDetails implements ClientDetails {

    public static DefaultClientDetails of(Client client) {
        DefaultClientDetails clientDetails = new DefaultClientDetails();
        clientDetails.setClientId(client.getId());
        clientDetails.setClientSecret(client.getSecret());
        // resourceIds
        Set<String> resourceIds = Optional.ofNullable(client.getResourceIds())
                .map(e -> Arrays.stream(e).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        clientDetails.setResourceIds(resourceIds);
        // authorizedGrantTypes
        List<String> authorizedGrantTypes = Optional.ofNullable(client.getGrantTypes())
                .map(e -> Arrays.stream(e).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        // redirectUri
        Set<String> redirectUri = Optional.ofNullable(client.getRedirectUris())
                .map(e -> Arrays.stream(e).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        clientDetails.setRegisteredRedirectUri(redirectUri);
        // scope
        Set<String> scope = Optional.ofNullable(client.getScopes())
                .map(e -> Arrays.stream(e).collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        clientDetails.setScope(scope);
        // token有效期
        clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        // 权限
        clientDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT")));
        // 额外信息
        Map<String, Object> additionalInformation = new HashMap<>(8);
        additionalInformation.put("name", client.getName());
        additionalInformation.put("description", client.getDescription());
        additionalInformation.put("logo", client.getLogo());
        clientDetails.setAdditionalInformation(additionalInformation);
        return clientDetails;
    }

}
