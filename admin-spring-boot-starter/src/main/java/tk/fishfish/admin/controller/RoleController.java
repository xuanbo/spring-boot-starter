package tk.fishfish.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.RoleCondition;
import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.admin.security.annotation.ApiAuthorize;
import tk.fishfish.admin.service.RoleService;
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
@RequiredArgsConstructor
@RequestMapping("/v1/role")
public class RoleController extends BaseController<Role> {

    private final RoleService roleService;

    @Override
    @ApiAuthorize(code = "sys:role:query:id", name = "主键查询角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:query:id')")
    public Role findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:role:create", name = "新增角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:create')")
    public Role insert(@Validated(Group.Insert.class) @RequestBody Role role) {
        return super.insert(role);
    }

    @Override
    @ApiAuthorize(code = "sys:role:modify", name = "更新角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:modify')")
    public Role update(@Validated(Group.Update.class) @RequestBody Role role) {
        return super.update(role);
    }

    @Override
    @ApiAuthorize(code = "sys:role:del:id", name = "主键删除角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:del:id')")
    public void deleteById(@PathVariable String id) {
        super.deleteById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:role:del:ids", name = "主键批量删除角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:del:ids')")
    public void deleteByIds(@RequestBody List<String> ids) {
        super.deleteByIds(ids);
    }

    @PostMapping("/page")
    @ApiAuthorize(code = "sys:role:query:page", name = "分页查询角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:query:page')")
    public Page<Role> page(@RequestBody Query<RoleCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

    @PostMapping("/list")
    @ApiAuthorize(code = "sys:role:query:list", name = "列表查询角色")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:query:list')")
    public List<Role> list(@RequestBody RoleCondition condition) {
        return service.query(condition);
    }

    @PutMapping("/{id}/grant")
    @ApiAuthorize(code = "sys:role:modify:grant", name = "角色授权资源")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:role:modify:grant')")
    public void grant(@PathVariable String id, @RequestBody List<Select<String>> selectResourceIds) {
        roleService.grant(id, selectResourceIds);
    }

}
