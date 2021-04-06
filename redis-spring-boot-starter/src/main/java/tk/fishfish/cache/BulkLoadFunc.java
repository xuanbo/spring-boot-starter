package tk.fishfish.cache;

import java.util.List;
import java.util.Map;

/**
 * 导入方法
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@FunctionalInterface
public interface BulkLoadFunc<T> {

    Map<String, T> loadByKeys(List<String> keys);

}
