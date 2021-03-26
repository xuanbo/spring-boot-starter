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
    @PreAuthorize("hasRole('ADMIN')")
    public Audit findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Audit insert(@Validated(Group.Insert.class) @RequestBody Audit audit) {
        throw HttpException.of(HttpStatus.NOT_IMPLEMENTED, 501, "审计安全日志只读");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Audit update(@Validated(Group.Update.class) @RequestBody Audit audit) {
        throw HttpException.of(HttpStatus.NOT_IMPLEMENTED, 501, "审计安全日志只读");
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
    public Page<Audit> page(@RequestBody Query<AuditCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
