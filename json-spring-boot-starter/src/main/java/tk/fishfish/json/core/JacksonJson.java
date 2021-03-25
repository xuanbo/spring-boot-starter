package tk.fishfish.json.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import tk.fishfish.json.exception.JsonException;

import java.io.InputStream;
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
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public <T> T read(byte[] jsonBytes, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonBytes, clazz);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public <T> T read(InputStream jsonInputStream, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonInputStream, clazz);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public Map<String, Object> readMap(String json) {
        try {
            return objectMapper.readValue(json, mapTypeReference);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public Map<String, Object> readMap(byte[] jsonBytes) {
        try {
            return objectMapper.readValue(jsonBytes, mapTypeReference);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public Map<String, Object> readMap(InputStream jsonInputStream) {
        try {
            return objectMapper.readValue(jsonInputStream, mapTypeReference);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public List<Map<String, Object>> readList(String json) {
        try {
            return objectMapper.readValue(json, listMapTypeReference);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public List<Map<String, Object>> readList(byte[] jsonBytes) {
        try {
            return objectMapper.readValue(jsonBytes, listMapTypeReference);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public List<Map<String, Object>> readList(InputStream jsonInputStream) {
        try {
            return objectMapper.readValue(jsonInputStream, listMapTypeReference);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public <T> List<T> readList(String json, Class<T> clazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public <T> List<T> readList(byte[] jsonBytes, Class<T> clazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(jsonBytes, type);
        } catch (Exception e) {
            throw new JsonException("读取json错误", e);
        }
    }

    @Override
    public <T> List<T> readList(InputStream jsonInputStream, Class<T> clazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(jsonInputStream, type);
        } catch (Exception e) {
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

}
