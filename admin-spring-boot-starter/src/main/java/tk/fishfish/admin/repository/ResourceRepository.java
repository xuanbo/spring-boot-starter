package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tk.fishfish.admin.entity.Resource;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 资源
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface ResourceRepository extends Repository<Resource> {

    @Select("SELECT * FROM sys_resource WHERE code = #{code}")
    Resource findByCode(String code);

}
