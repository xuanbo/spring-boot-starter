package tk.fishfish.admin.controller.oauth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "oauth/index";
    }

}
