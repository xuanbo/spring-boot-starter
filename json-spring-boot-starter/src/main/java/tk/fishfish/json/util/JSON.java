package tk.fishfish.json.util;

import tk.fishfish.json.Json;

import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public final class JSON {

    private static Json json = null;

    private JSON() {
    }

    public static <T> T read(String json, Class<T> clazz) {
        return JSON.json.read(json, clazz);
    }

    public static Map<String, Object> readMap(String json) {
        return JSON.json.readMap(json);
    }

    public static List<Map<String, Object>> readList(String json) {
        return JSON.json.readList(json);
    }

    public static <T> List<T> readList(String json, Class<T> clazz) {
        return JSON.json.readList(json, clazz);
    }

    public static <T> String write(T value) {
        return JSON.json.write(value);
    }

    public static void setJson(Json json) {
        JSON.json = json;
    }

}
