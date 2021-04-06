package tk.fishfish.mybatis.repository;

import org.apache.ibatis.annotations.InsertProvider;
import tk.fishfish.mybatis.entity.Entity;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 批量写入
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@RegisterMapper
public interface BatchInsertRepository<T extends Entity> {

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如 MySQL、H2 等
     * 注意：需要指定主键
     *
     * @param records 记录
     * @return 影响数据库结果条数
     */
    @InsertProvider(type = BatchInsertRepositoryProvider.class, method = "dynamicSQL")
    int insertList(List<T> records);

    /**
     * 批量插入（字段不为空），支持批量插入的数据库可以使用，例如 MySQL、H2 等
     * 注意：需要指定主键
     *
     * @param records 记录
     * @return 影响数据库结果条数
     */
    @InsertProvider(type = BatchInsertRepositoryProvider.class, method = "dynamicSQL")
    int insertListSelective(List<T> records);

}
