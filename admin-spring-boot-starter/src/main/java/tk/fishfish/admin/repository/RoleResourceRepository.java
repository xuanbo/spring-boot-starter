package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.RoleResource;
import tk.fishfish.mybatis.repository.BatchInsertRepository;
import tk.fishfish.mybatis.repository.Repository;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 角色资源
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface RoleResourceRepository extends Repository<RoleResource>, BatchInsertRepository<RoleResource> {

    default void deleteByRoleId(String roleId) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria().andEqualTo("roleId", roleId);
        deleteByExample(condition);
    }

    default void deleteByRoleIds(List<String> roleIds) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria().andIn("roleId", roleIds);
        deleteByExample(condition);
    }

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

    default void deleteByRoleIdAndResourceIds(String roleId, List<String> resourceIds) {
        Condition condition = new Condition(RoleResource.class);
        condition.createCriteria()
                .andEqualTo("roleId", roleId)
                .andIn("resourceId", resourceIds);
        deleteByExample(condition);
    }

}
