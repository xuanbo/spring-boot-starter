package tk.fishfish.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.RoleCondition;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;

import java.util.List;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequestMapping("/v1/role")
public class RoleController extends BaseController<Role> {

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Role findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Role insert(@Validated(Group.Insert.class) @RequestBody Role role) {
        return super.insert(role);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Role update(@Validated(Group.Update.class) @RequestBody Role role) {
        return super.update(role);
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
    public Page<Role> page(@RequestBody Query<RoleCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
