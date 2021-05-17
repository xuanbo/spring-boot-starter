package tk.fishfish.mybatis.service.hook;

import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * 删除勾子
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface DeleteHook {

    /**
     * Delete前调用
     *
     * @param id 主键
     */
    default void beforeDelete(String id) {
    }

    /**
     * Delete后调用
     *
     * @param id 主键
     */
    default void afterDelete(String id) {
    }

    /**
     * Delete前调用
     *
     * @param ids 主键
     */
    default void beforeDelete(List<String> ids) {
    }

    /**
     * Delete后调用
     *
     * @param ids 主键
     */
    default void afterDelete(List<String> ids) {
    }

    /**
     * Delete前调用
     *
     * @param condition 条件
     */
    default void beforeDelete(Condition condition) {
    }

    /**
     * Delete后调用
     *
     * @param condition 条件
     */
    default void afterDelete(Condition condition) {
    }

}
