package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface RoleRepository extends Repository<Role> {
}
