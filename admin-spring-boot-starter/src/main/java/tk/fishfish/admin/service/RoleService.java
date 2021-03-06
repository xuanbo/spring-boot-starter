package tk.fishfish.admin.service;

import tk.fishfish.admin.dto.Select;

import java.util.List;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface RoleService {

    void grant(String id, List<Select<String>> selects);

}
