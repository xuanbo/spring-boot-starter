package tk.fishfish.admin.repository;

import org.apache.ibatis.annotations.Mapper;
import tk.fishfish.admin.entity.Audit;
import tk.fishfish.mybatis.repository.Repository;

/**
 * 审计
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Mapper
public interface AuditRepository extends Repository<Audit> {
}
