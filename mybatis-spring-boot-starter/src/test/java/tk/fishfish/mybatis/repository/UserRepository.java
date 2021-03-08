package tk.fishfish.mybatis.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.mybatis.entity.User;

/**
 * 用户DAO
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Mapper
public interface UserRepository extends Repository<User> {
}
