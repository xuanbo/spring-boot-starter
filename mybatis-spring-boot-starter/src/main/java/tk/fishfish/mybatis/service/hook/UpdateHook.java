package tk.fishfish.mybatis.service.hook;

import tk.fishfish.persistence.Entity;

/**
 * 更新勾子
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface UpdateHook<T extends Entity> {

    /**
     * Update前调用
     *
     * @param entity 实体
     */
    default void beforeUpdate(T entity) {
    }

    /**
     * Update后调用
     *
     * @param entity 实体
     */
    default void afterUpdate(T entity) {
    }

}
