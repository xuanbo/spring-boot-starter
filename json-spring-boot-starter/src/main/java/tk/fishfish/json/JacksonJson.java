package tk.fishfish.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * jackson json
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class JacksonJson implements Json {

    private final ObjectMapper objectMapper;

    private final TypeReference<Map<String, Object>> mapTypeReference = new TypeReference<Map<String, Object>>() {
    };
    private final TypeReference<List<Map<String, Object>>> listMapTypeReference = new TypeReference<List<Map<String, Object>>>() {
    };

    public JacksonJson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T read(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public Map<String, Object> readMap(String json) {
        try {
            return objectMapper.readValue(json, mapTypeReference);
        } catch (JsonProcessingException e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public List<Map<String, Object>> readList(String json) {
        try {
            return objectMapper.readValue(json, listMapTypeReference);
        } catch (JsonProcessingException e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public <T> List<T> readList(String json, Class<T> clazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public <T> String write(T obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonException("写入json错误", e);
        }
    }

    public JsonNode readTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new JsonException("读取json错误", e);
        }
    }

}
