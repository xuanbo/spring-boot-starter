package tk.fishfish.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.PermissionCondition;
import tk.fishfish.admin.entity.Permission;
import tk.fishfish.admin.security.annotation.ApiAuthorize;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;

import java.util.List;

/**
 * 权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequestMapping("/v1/permission")
public class PermissionController extends BaseController<Permission> {

    @Override
    @ApiAuthorize(code = "sys:permission:query:id", name = "主键查询权限")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:permission:query:id')")
    public Permission findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:permission:create", name = "创建权限")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:permission:create')")
    public Permission insert(@Validated(Group.Insert.class) @RequestBody Permission permission) {
        return super.insert(permission);
    }

    @Override
    @ApiAuthorize(code = "sys:permission:modify", name = "更新权限")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:permission:modify')")
    public Permission update(@Validated(Group.Update.class) @RequestBody Permission permission) {
        return super.update(permission);
    }

    @Override
    @ApiAuthorize(code = "sys:permission:del:id", name = "主键删除权限")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:permission:del:id')")
    public void deleteById(@PathVariable String id) {
        super.deleteById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:permission:del:ids", name = "主键批量删除权限")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:permission:del:ids')")
    public void deleteByIds(@RequestBody List<String> ids) {
        super.deleteByIds(ids);
    }

    @PostMapping("/page")
    @ApiAuthorize(code = "sys:permission:query:page", name = "分页查询权限")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:permission:query:page')")
    public Page<Permission> page(@RequestBody Query<PermissionCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
