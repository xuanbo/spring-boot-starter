package tk.fishfish.admin.controller.oauth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * OAuth2登录页面替换
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Map<String, Object> model) {
        return "oauth/login";
    }

}
