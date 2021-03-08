package tk.fishfish.mybatis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.mybatis.condition.UserCondition;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;
import tk.fishfish.mybatis.entity.User;
import tk.fishfish.mybatis.service.UserService;

/**
 * 用户API
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController extends BaseController<User> {

    private final UserService userService;

    @PostMapping("/page")
    public Page<User> page(@RequestBody Query<UserCondition> query) {
        return userService.page(query.getCondition(), query.getPage());
    }

}
