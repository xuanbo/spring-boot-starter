package tk.fishfish.json;

import java.util.List;
import java.util.Map;

/**
 * json path
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public interface JsonPath {

    <T> T read(String json, String path);

    Map<String, Object> readMap(String json, String path);

    List<Map<String, Object>> readList(String json, String path);

}
