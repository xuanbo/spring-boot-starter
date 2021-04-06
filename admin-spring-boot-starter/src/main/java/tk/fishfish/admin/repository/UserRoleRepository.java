package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.UserRole;
import tk.fishfish.mybatis.repository.BatchInsertRepository;
import tk.fishfish.mybatis.repository.Repository;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 用户角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface UserRoleRepository extends Repository<UserRole>, BatchInsertRepository<UserRole> {

    default void deleteByUserId(String userId) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andEqualTo("userId", userId);
        deleteByExample(condition);
    }

    default void deleteByUserIds(List<String> userIds) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andIn("userId", userIds);
        deleteByExample(condition);
    }

    default void deleteByRoleId(String roleId) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andEqualTo("roleId", roleId);
        deleteByExample(condition);
    }

    default void deleteByRoleIds(List<String> roleIds) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria().andIn("roleId", roleIds);
        deleteByExample(condition);
    }

    default void deleteByUserIdAndRoleIds(String userId, List<String> roleIds) {
        Condition condition = new Condition(UserRole.class);
        condition.createCriteria()
                .andEqualTo("userId", userId)
                .andIn("roleId", roleIds);
        deleteByExample(condition);
    }

}
