package tk.fishfish.mybatis.service.hook;

import tk.fishfish.persistence.Entity;

/**
 * 增删改勾子
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface CrudHook<T extends Entity> extends InsertHook<T>, UpdateHook<T>, DeleteHook {
}
