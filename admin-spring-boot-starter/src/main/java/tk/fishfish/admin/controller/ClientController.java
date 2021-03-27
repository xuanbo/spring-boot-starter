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
    @PreAuthorize("hasRole('ADMIN')")
    public Client findById(@PathVariable String id) {
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Client insert(@Validated(Group.Insert.class) @RequestBody Client client) {
        return super.insert(client);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Client update(@Validated(Group.Update.class) @RequestBody Client client) {
        return super.update(client);
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
    public Page<Client> page(@RequestBody Query<ClientCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
