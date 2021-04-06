package tk.fishfish.cache;

/**
 * 导入方法
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@FunctionalInterface
public interface LoadFunc<T> {

    T loadByKey(String key);

}
