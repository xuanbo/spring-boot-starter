package tk.fishfish.admin.service;

import tk.fishfish.admin.entity.Permission;

/**
 * 权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface PermissionService {

    Permission findById(String id);

    void insertSelective(Permission permission);

    void updateSelective(Permission permission);

}
