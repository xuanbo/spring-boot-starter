package tk.fishfish.admin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.fishfish.admin.condition.RoleCondition;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.mybatis.controller.BaseController;
import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Query;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RestController
@RequestMapping("/v1/role")
public class RoleController extends BaseController<Role> {

    @PostMapping("/page")
    public Page<Role> page(@RequestBody Query<RoleCondition> query) {
        return service.page(query.getCondition(), query.getPage());
    }

}
