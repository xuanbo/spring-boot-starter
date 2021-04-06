package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.fishfish.admin.entity.Permission;
import tk.fishfish.mybatis.repository.Repository;

import java.util.List;

/**
 * 权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface PermissionRepository extends Repository<Permission> {

    @Select({
            "<script>",
            "SELECT sys_permission.*, sys_role_resource.role_id AS roleId FROM sys_permission",
            "LEFT JOIN sys_resource_permission ON sys_resource_permission.permission_id = sys_permission.id",
            "LEFT JOIN sys_resource ON sys_resource_permission.resource_id = sys_resource.id",
            "LEFT JOIN sys_role_resource ON sys_resource.id = sys_role_resource.resource_id",
            "WHERE sys_role_resource.role_id IN",
            "<foreach collection='roleIds' item='roleId' open='(' separator=', ' close=')'>",
            "#{roleId}",
            "</foreach>",
            "</script>"
    })
    List<Permission> findByRoleIds(@Param("roleIds") List<String> roleIds);

}
