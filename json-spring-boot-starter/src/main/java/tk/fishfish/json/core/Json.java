package tk.fishfish.json.core;

import java.io.InputStream;
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

    <T> T read(byte[] jsonBytes, Class<T> clazz);

    <T> T read(InputStream jsonInputStream, Class<T> clazz);

    Map<String, Object> readMap(String json);

    Map<String, Object> readMap(byte[] jsonBytes);

    Map<String, Object> readMap(InputStream jsonInputStream);

    List<Map<String, Object>> readList(String json);

    List<Map<String, Object>> readList(byte[] jsonBytes);

    List<Map<String, Object>> readList(InputStream jsonInputStream);

    <T> List<T> readList(String json, Class<T> clazz);

    <T> List<T> readList(byte[] jsonBytes, Class<T> clazz);

    <T> List<T> readList(InputStream jsonInputStream, Class<T> clazz);

    <T> String write(T obj);

}
