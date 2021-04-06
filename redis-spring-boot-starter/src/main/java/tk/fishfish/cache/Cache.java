package tk.fishfish.cache;

import java.util.List;

/**
 * 缓存
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface Cache<T> {

    /**
     * 根据key导入一条数据
     *
     * @param key  唯一key
     * @param func 导入方法
     * @return 数据
     */
    T loadByKey(String key, LoadFunc<T> func);

    /**
     * 根据key导入多条数据
     *
     * @param key  唯一key
     * @param func 导入方法
     * @return 数据
     */
    List<T> loadListByKey(String key, LoadFunc<List<T>> func);

    /**
     * 根据keys导入多条数据，其中一个key对应一条数据
     *
     * @param keys keys
     * @param func 导入方法
     * @return 数据
     */
    List<T> loadByKeys(List<String> keys, BulkLoadFunc<T> func);

    /**
     * 根据keys导入多条数据，其中一个key对应多条数据
     *
     * @param keys keys
     * @param func 导入方法
     * @return 数据
     */
    List<T> loadListByKeys(List<String> keys, BulkLoadFunc<List<T>> func);

    /**
     * 清除key
     *
     * @param key key
     */
    void evict(String key);

    /**
     * 批量清除keys
     *
     * @param keys keys
     */
    void evict(List<String> keys);

    /**
     * 清除全部
     */
    void evict();

}
