package tk.fishfish.admin.controller.oauth2;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;
import java.util.Set;

/**
 * OAuth2授权页面替换
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizationController {

    @GetMapping("/oauth/confirm_access")
    public String confirmAccess(Map<String, Object> model) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        String clientId = authorizationRequest.getClientId();
        Set<String> scopes = authorizationRequest.getScope();
        model.put("clientId", clientId);
        model.put("scopes", scopes);
        return "oauth/confirm_access";
    }

}
