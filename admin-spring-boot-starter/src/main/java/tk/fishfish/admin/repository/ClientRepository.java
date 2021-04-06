package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.Client;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 客户端
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface ClientRepository extends Repository<Client> {
}
