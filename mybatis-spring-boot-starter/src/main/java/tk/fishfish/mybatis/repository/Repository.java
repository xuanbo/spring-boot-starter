package tk.fishfish.mybatis.repository;

import tk.fishfish.mybatis.entity.Entity;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 自定义通用Mapper
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@RegisterMapper
public interface Repository<T extends Entity> extends Mapper<T> {

}

