package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.fishfish.admin.entity.User;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface UserRepository extends Repository<User> {

    User loadByUsername(@Param("username") String username);

}
