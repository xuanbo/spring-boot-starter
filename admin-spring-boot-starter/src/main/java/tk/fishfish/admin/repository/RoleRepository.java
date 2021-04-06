package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.fishfish.admin.entity.Role;
import tk.fishfish.mybatis.repository.Repository;

import java.util.List;

/**
 * 角色
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface RoleRepository extends Repository<Role> {

    @Select({
            "SELECT r.* FROM sys_role r",
            "LEFT JOIN sys_user_role ur ON ur.role_id = r.id",
            "WHERE ur.user_id = #{userId}"
    })
    List<Role> findByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM sys_role WHERE code = #{code}")
    Role findByCode(@Param("code") String code);

}
