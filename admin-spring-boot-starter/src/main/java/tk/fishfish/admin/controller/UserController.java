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
import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.entity.User;
import tk.fishfish.admin.security.DefaultUserDetails;
import tk.fishfish.admin.security.UserContextHolder;
import tk.fishfish.admin.security.annotation.ApiAuthorize;
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
    @ApiAuthorize(code = "sys:user:query:id", name = "主键查询用户")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:user:query:id')")
    public User findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:user:create", name = "新增用户")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:user:create')")
    public User insert(@Validated(Group.Insert.class) @RequestBody User user) {
        return super.insert(user);
    }

    @Override
    @ApiAuthorize(code = "sys:user:modify", name = "修改用户")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:user:modify') or principal.username.equals(#p0.username)")
    public User update(@Validated(Group.Update.class) @RequestBody User user) {
        return super.update(user);
    }

    @Override
    @ApiAuthorize(code = "sys:user:del:id", name = "主键删除用户")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:user:del:id')")
    public void deleteById(@PathVariable String id) {
        super.deleteById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:user:del:ids", name = "主键批量删除用户")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:user:del:ids')")
    public void deleteByIds(@RequestBody List<String> ids) {
        super.deleteByIds(ids);
    }

    @PostMapping("/page")
    @ApiAuthorize(code = "sys:user:query:page", name = "分页查询用户")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:user:query:page')")
    public Page<User> page(@RequestBody Query<UserCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

    @GetMapping("/principal")
    @ApiAuthorize(code = "sys:user:principal", name = "查询当前用户信息")
    @PreAuthorize("isAuthenticated() or hasAuthority('sys:user:principal')")
    public DefaultUserDetails principal() {
        return UserContextHolder.current();
    }

    @PutMapping("/password")
    @ApiAuthorize(code = "sys:user:modify:password", name = "修改密码")
    @PreAuthorize("isAuthenticated() or hasAuthority('sys:user:modify:password')")
    public void changePassword(@Validated @RequestBody PasswordDTO dto) {
        String username = UserContextHolder.username();
        userService.changePassword(username, dto.getOldPassword(), dto.getNewPassword());
    }

    @PutMapping("/{id}/grant")
    @ApiAuthorize(code = "sys:user:modify:grant", name = "用户授权角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:user:modify:grant')")
    public void grant(@PathVariable String id, @RequestBody List<Select<String>> selectRoleIds) {
        userService.grant(id, selectRoleIds);
    }

}
