package tk.fishfish.dbrowser.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.dbrowser.entity.Database;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface DatabaseRepository extends Repository<Database> {
}
