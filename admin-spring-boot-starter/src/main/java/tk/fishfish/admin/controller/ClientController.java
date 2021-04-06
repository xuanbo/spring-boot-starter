package tk.fishfish.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.ClientCondition;
import tk.fishfish.admin.entity.Client;
import tk.fishfish.admin.security.annotation.ApiAuthorize;
import tk.fishfish.admin.validator.Group;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;

import java.util.List;

/**
 * 客户端
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequestMapping("/v1/client")
public class ClientController extends BaseController<Client> {

    @Override
    @ApiAuthorize(code = "sys:client:query:id", name = "主键查询客户端")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:client:query:id')")
    public Client findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:client:create", name = "创建客户端")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:client:create')")
    public Client insert(@Validated(Group.Insert.class) @RequestBody Client client) {
        return super.insert(client);
    }

    @Override
    @ApiAuthorize(code = "sys:client:modify", name = "更新客户端")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:client:modify')")
    public Client update(@Validated(Group.Update.class) @RequestBody Client client) {
        return super.update(client);
    }

    @Override
    @ApiAuthorize(code = "sys:client:del:id", name = "主键删除客户端")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:client:del:id')")
    public void deleteById(@PathVariable String id) {
        super.deleteById(id);
    }

    @Override
    @ApiAuthorize(code = "sys:client:del:ids", name = "主键批量删除客户端")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:client:del:ids')")
    public void deleteByIds(@RequestBody List<String> ids) {
        super.deleteByIds(ids);
    }

    @PostMapping("/page")
    @ApiAuthorize(code = "sys:client:query:page", name = "分页查询客户端")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('sys:client:query:page')")
    public Page<Client> page(@RequestBody Query<ClientCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
