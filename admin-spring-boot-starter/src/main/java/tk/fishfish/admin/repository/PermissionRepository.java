package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.Permission;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 权限
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface PermissionRepository extends Repository<Permission> {
}
