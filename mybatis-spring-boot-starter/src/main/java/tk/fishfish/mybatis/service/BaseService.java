package tk.fishfish.mybatis.service;

import tk.fishfish.mybatis.domain.Page;
import tk.fishfish.mybatis.domain.Pageable;
import tk.fishfish.mybatis.domain.Sort;
import tk.fishfish.mybatis.entity.Entity;

import java.util.List;

/**
 * 通用服务
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public interface BaseService<T extends Entity> {

    /**
     * 分页查询
     *
     * @param pageable 分页参数
     * @return 分页数据
     */
    Page<T> page(Pageable pageable);

    /**
     * 条件分页查询
     *
     * @param condition 条件
     * @param pageable  分页参数
     * @return 分页数据
     */
    Page<T> page(Object condition, Pageable pageable);

    /**
     * 条件查询
     *
     * @param condition 条件
     * @param sorts     排序
     * @return 数据
     */
    List<T> query(Object condition, Sort... sorts);

    /**
     * 条件查询
     *
     * @param condition 条件
     * @return 数据
     */
    T single(Object condition);

    /**
     * 条件查询
     *
     * @param condition 条件
     * @return 条数
     */
    long count(Object condition);

    /**
     * 条件是否存在
     *
     * @param condition 条件
     * @return 是否存在
     */
    boolean exist(Object condition);

    /**
     * 新增，所有字段写入
     *
     * @param entity 实体
     */
    void insert(T entity);

    /**
     * 新增，字段不为null写入
     *
     * @param entity 实体
     */
    void insertSelective(T entity);

    /**
     * 更新，所有字段写入
     *
     * @param entity 实体
     */
    void update(T entity);

    /**
     * 更新，字段不为null写入
     *
     * @param entity 实体
     */
    void updateSelective(T entity);

    /**
     * 新增或更新，主键存在调用update，不存在调用insert
     *
     * @param entity 实体
     */
    void save(T entity);

    /**
     * 查询
     *
     * @param id 主键
     * @return 实体
     */
    T findById(String id);

    /**
     * 批量查询
     *
     * @param ids 主键
     * @return 实体
     */
    List<T> findByIds(List<String> ids);

    /**
     * 删除
     *
     * @param id 主键
     */
    void deleteById(String id);

    /**
     * 批量删除
     *
     * @param ids 主键
     */
    void deleteByIds(List<String> ids);

}
