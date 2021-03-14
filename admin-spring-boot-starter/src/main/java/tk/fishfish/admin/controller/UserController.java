package tk.fishfish.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.UserCondition;
import tk.fishfish.admin.entity.User;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequestMapping("/v1/user")
public class UserController extends BaseController<User> {

    @PostMapping("/page")
    public Page<User> page(@RequestBody Query<UserCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
