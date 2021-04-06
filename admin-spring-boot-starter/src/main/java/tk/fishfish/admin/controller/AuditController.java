package tk.fishfish.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.AuditCondition;
import tk.fishfish.admin.entity.Audit;
import tk.fishfish.admin.security.annotation.ApiAuthorize;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;
import tk.fishfish.rest.execption.HttpException;

import java.util.List;

/**
 * 审计日志
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequestMapping("/v1/audit")
public class AuditController extends BaseController<Audit> {

    @Override
    @ApiAuthorize(code = "sys:audit:query:id", name = "主键查询审计")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:audit:query:id')")
    public Audit findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:audit:create", name = "创建审计")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:audit:create')")
    public Audit insert(@Validated(Group.Insert.class) @RequestBody Audit audit) {
        throw HttpException.of(HttpStatus.NOT_IMPLEMENTED, 501, "审计安全日志只读");
    }

    @Override
    @ApiAuthorize(code = "sys:audit:modify", name = "修改审计")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:audit:modify')")
    public Audit update(@Validated(Group.Update.class) @RequestBody Audit audit) {
        throw HttpException.of(HttpStatus.NOT_IMPLEMENTED, 501, "审计安全日志只读");
    }

    @Override
    @ApiAuthorize(code = "sys:audit:del:id", name = "主键删除审计")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:audit:del:id')")
    public void deleteById(@PathVariable String id) {
        super.deleteById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:audit:del:ids", name = "主键批量删除审计")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:audit:del:ids')")
    public void deleteByIds(@RequestBody List<String> ids) {
        super.deleteByIds(ids);
    }

    @PostMapping("/page")
    @ApiAuthorize(code = "sys:audit:query:page", name = "分页查询审计")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:audit:query:page')")
    public Page<Audit> page(@RequestBody Query<AuditCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
