package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Update("UPDATE sys_user SET password = #{password} WHERE username = #{username}")
    void updatePassword(@Param("username") String username, @Param("password") String password);

}
