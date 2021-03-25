package tk.fishfish.json.util;

import tk.fishfish.json.core.Json;
import tk.fishfish.json.core.JsonPath;

import java.io.InputStream;
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
    private static JsonPath jsonPath = null;

    private JSON() {
    }

    public static <T> T read(String json, Class<T> clazz) {
        return getJson().read(json, clazz);
    }

    public static <T> T read(byte[] jsonBytes, Class<T> clazz) {
        return getJson().read(jsonBytes, clazz);
    }

    public static <T> T read(InputStream jsonInputStream, Class<T> clazz) {
        return getJson().read(jsonInputStream, clazz);
    }

    public static Map<String, Object> readMap(String json) {
        return getJson().readMap(json);
    }

    public static Map<String, Object> readMap(byte[] jsonBytes) {
        return getJson().readMap(jsonBytes);
    }

    public static Map<String, Object> readMap(InputStream jsonInputStream) {
        return getJson().readMap(jsonInputStream);
    }

    public static List<Map<String, Object>> readList(String json) {
        return getJson().readList(json);
    }

    public static List<Map<String, Object>> readList(byte[] jsonBytes) {
        return getJson().readList(jsonBytes);
    }

    public static List<Map<String, Object>> readList(InputStream jsonInputStream) {
        return getJson().readList(jsonInputStream);
    }

    public static <T> List<T> readList(String json, Class<T> clazz) {
        return getJson().readList(json, clazz);
    }

    public static <T> List<T> readList(byte[] jsonBytes, Class<T> clazz) {
        return getJson().readList(jsonBytes, clazz);
    }

    public static <T> List<T> readList(InputStream jsonInputStream, Class<T> clazz) {
        return getJson().readList(jsonInputStream, clazz);
    }

    public static <T> String write(T value) {
        return getJson().write(value);
    }

    public static <T> T read(String json, String path, Class<T> clazz) {
        return getJsonPath().read(json, path, clazz);
    }

    public static <T> T read(InputStream jsonInputStream, String path, Class<T> clazz) {
        return getJsonPath().read(jsonInputStream, path, clazz);
    }

    public static Map<String, Object> readMap(String json, String path) {
        return getJsonPath().readMap(json, path);
    }

    public static Map<String, Object> readMap(InputStream jsonInputStream, String path) {
        return getJsonPath().readMap(jsonInputStream, path);
    }

    public static List<Map<String, Object>> readList(String json, String path) {
        return getJsonPath().readList(json, path);
    }

    public static List<Map<String, Object>> readList(InputStream jsonInputStream, String path) {
        return getJsonPath().readList(jsonInputStream, path);
    }

    public static <T> List<T> readList(String json, String path, Class<T> clazz) {
        return getJsonPath().readList(json, path, clazz);
    }

    public static <T> List<T> readList(InputStream jsonInputStream, String path, Class<T> clazz) {
        return getJsonPath().readList(jsonInputStream, path, clazz);
    }

    private static Json getJson() {
        if (JSON.json == null) {
            throw new RuntimeException("未正确初始化 Json 对象，请检查配置信息");
        }
        return JSON.json;
    }

    public static void setJson(Json json) {
        JSON.json = json;
    }

    private static JsonPath getJsonPath() {
        if (JSON.jsonPath == null) {
            throw new RuntimeException("未正确初始化 JsonPath 对象，请检查配置信息");
        }
        return JSON.jsonPath;
    }

    public static void setJsonPath(JsonPath jsonPath) {
        JSON.jsonPath = jsonPath;
    }

}
