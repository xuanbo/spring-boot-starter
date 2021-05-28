package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import tk.fishfish.admin.dto.UserPermission;
import tk.fishfish.admin.entity.User;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 用户
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
@CacheConfig(cacheNames = User.NAME)
public interface UserRepository extends Repository<User> {

    @Cacheable(key = "'username:' + #p0")
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    UserPermission loadByUsername(@Param("username") String username);

    @Update("UPDATE sys_user SET password = #{password} WHERE username = #{username}")
    void updatePassword(@Param("username") String username, @Param("password") String password);

}
