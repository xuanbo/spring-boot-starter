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
import tk.fishfish.admin.condition.ResourceCondition;
import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.entity.Resource;
import tk.fishfish.admin.security.annotation.ApiAuthorize;
import tk.fishfish.admin.service.ResourceService;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;

import java.util.List;

/**
 * 资源
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/resource")
public class ResourceController extends BaseController<Resource> {

    private final ResourceService resourceService;

    @Override
    @ApiAuthorize(code = "sys:resource:query:id", name = "主键查询资源")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:query:id')")
    public Resource findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:resource:create", name = "新增资源")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:create')")
    public Resource insert(@Validated(Group.Insert.class) @RequestBody Resource resource) {
        return super.insert(resource);
    }

    @Override
    @ApiAuthorize(code = "sys:resource:modify", name = "更新资源")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:modify')")
    public Resource update(@Validated(Group.Update.class) @RequestBody Resource resource) {
        return super.update(resource);
    }

    @Override
    @ApiAuthorize(code = "sys:resource:del:id", name = "主键删除资源")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:del:id')")
    public void deleteById(@PathVariable String id) {
        super.deleteById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:resource:del:ids", name = "主键批量删除资源")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:del:ids')")
    public void deleteByIds(@RequestBody List<String> ids) {
        super.deleteByIds(ids);
    }

    @PostMapping("/page")
    @ApiAuthorize(code = "sys:resource:query:page", name = "分页查询资源")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:query:page')")
    public Page<Resource> page(@RequestBody Query<ResourceCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

    @PostMapping("/tree")
    @ApiAuthorize(code = "sys:resource:query:tree", name = "资源树")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:query:tree')")
    public List<Resource> tree() {
        return resourceService.tree();
    }

    @PutMapping("/{id}/grant")
    @ApiAuthorize(code = "sys:resource:modify:grant", name = "资源分配权限")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:resource:modify:grant')")
    public void grant(@PathVariable String id, @RequestBody List<Select<String>> selects) {
        resourceService.grant(id, selects);
    }

}
