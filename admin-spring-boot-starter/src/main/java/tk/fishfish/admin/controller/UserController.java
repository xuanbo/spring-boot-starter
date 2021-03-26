package tk.fishfish.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.UserCondition;
import tk.fishfish.admin.dto.PasswordDTO;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.service.UserService;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;

import java.util.List;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController extends BaseController<User> {

    private final UserService userService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User insert(@Validated(Group.Insert.class) @RequestBody User user) {
        return super.insert(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or principal.username.equals(#p0.username)")
    public User update(@Validated(Group.Update.class) @RequestBody User user) {
        return super.update(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable String id) {
        super.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteByIds(@RequestBody List<String> ids) {
        super.deleteByIds(ids);
    }

    @PostMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> page(@RequestBody Query<UserCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

    @GetMapping("/principal")
    public DefaultUserDetails principal() {
        return UserContextHolder.current();
    }

    @PutMapping("/password")
    public void changePassword(@Validated @RequestBody PasswordDTO dto) {
        String username = UserContextHolder.username();
        userService.changePassword(username, dto.getOldPassword(), dto.getNewPassword());
    }

}
