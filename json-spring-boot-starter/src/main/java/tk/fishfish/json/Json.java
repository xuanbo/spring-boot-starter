package tk.fishfish.json;

import java.util.List;
import java.util.Map;

/**
 * json
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public interface Json {

    <T> T read(String json, Class<T> clazz);

    Map<String, Object> readMap(String json);

    List<Map<String, Object>> readList(String json);

    <T> String write(T obj);

}
