package tk.fishfish.json.core;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * json path
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public interface JsonPath {

    <T> T read(String json, String path, Class<T> clazz);

    <T> T read(InputStream jsonInputStream, String path, Class<T> clazz);

    Map<String, Object> readMap(String json, String path);

    Map<String, Object> readMap(InputStream jsonInputStream, String path);

    List<Map<String, Object>> readList(String json, String path);

    List<Map<String, Object>> readList(InputStream jsonInputStream, String path);

    <T> List<T> readList(String json, String path, Class<T> clazz);

    <T> List<T> readList(InputStream jsonInputStream, String path, Class<T> clazz);

}
