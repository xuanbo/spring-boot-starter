package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.UserRole;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 用户角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface UserRoleRepository extends Repository<UserRole> {
}
