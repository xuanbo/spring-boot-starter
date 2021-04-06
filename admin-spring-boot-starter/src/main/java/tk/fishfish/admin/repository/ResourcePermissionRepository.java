package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.ResourcePermission;
import tk.fishfish.admin.entity.RoleResource;
import tk.fishfish.mybatis.repository.BatchInsertRepository;
import tk.fishfish.mybatis.repository.Repository;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 资源权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface ResourcePermissionRepository extends Repository<ResourcePermission>, BatchInsertRepository<ResourcePermission> {

    default void deleteByResourceId(String resourceId) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria().andEqualTo("resourceId", resourceId);
        deleteByExample(condition);
    }

    default void deleteByResourceIds(List<String> resourceIds) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria().andIn("resourceId", resourceIds);
        deleteByExample(condition);
    }

    default void deleteByPermissionId(String permissionId) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria().andEqualTo("permissionId", permissionId);
        deleteByExample(condition);
    }

    default void deleteByPermissionIds(List<String> permissionIds) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria().andIn("permissionId", permissionIds);
        deleteByExample(condition);
    }

    default void deleteByResourceIdPermissionIds(String resourceId, List<String> permissionIds) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria()
                .andEqualTo("resourceId", resourceId)
                .andIn("permissionId", permissionIds);
        deleteByExample(condition);
    }

}
