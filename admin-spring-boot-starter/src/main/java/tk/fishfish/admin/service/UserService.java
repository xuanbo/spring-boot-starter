package tk.fishfish.admin.service;

import tk.fishfish.admin.dto.Select;
import tk.fishfish.admin.dto.UserPermission;

import java.util.List;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface UserService {

    UserPermission loadByUsername(String username);

    void changePassword(String username, String oldPassword, String newPassword);

    void grant(String id, List<Select<String>> selects);

}
