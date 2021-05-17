package tk.fishfish.mybatis.service.hook;

import tk.fishfish.persistence.Entity;

/**
 * 新增勾子
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface InsertHook<T extends Entity> {

    /**
     * Insert前调用
     *
     * @param entity 实体
     */
    default void beforeInsert(T entity) {
    }

    /**
     * Insert后调用
     *
     * @param entity 实体
     */
    default void afterInsert(T entity) {
    }

}
