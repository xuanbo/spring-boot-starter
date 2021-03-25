package tk.fishfish.json.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * jackson json path
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class JacksonJsonPath implements JsonPath {

    private final Configuration configuration;

    private final ObjectMapper objectMapper;

    private final TypeRef<Map<String, Object>> mapTypeRef = new TypeRef<Map<String, Object>>() {
    };
    private final TypeRef<List<Map<String, Object>>> listMapTypeRef = new TypeRef<List<Map<String, Object>>>() {
    };

    public JacksonJsonPath(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        configuration = new Configuration.ConfigurationBuilder()
                .jsonProvider(new JacksonJsonProvider(this.objectMapper))
                .mappingProvider(new JacksonMappingProvider(this.objectMapper))
                .options(EnumSet.noneOf(Option.class))
                .build();
    }

    @Override
    public <T> T read(String json, String path, Class<T> clazz) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(json)
                .read(path, clazz);
    }

    @Override
    public <T> T read(InputStream jsonInputStream, String path, Class<T> clazz) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(jsonInputStream)
                .read(path, clazz);
    }

    @Override
    public Map<String, Object> readMap(String json, String path) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(json)
                .read(path, mapTypeRef);
    }

    @Override
    public Map<String, Object> readMap(InputStream jsonInputStream, String path) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(jsonInputStream)
                .read(path, mapTypeRef);
    }

    @Override
    public List<Map<String, Object>> readList(String json, String path) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(json)
                .read(path, listMapTypeRef);
    }

    @Override
    public List<Map<String, Object>> readList(InputStream jsonInputStream, String path) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(jsonInputStream)
                .read(path, listMapTypeRef);
    }

    @Override
    public <T> List<T> readList(String json, String path, Class<T> clazz) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(json)
                .read(path, new TypeRef<List<T>>() {
                    @Override
                    public Type getType() {
                        return objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
                    }
                });
    }

    @Override
    public <T> List<T> readList(InputStream jsonInputStream, String path, Class<T> clazz) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(jsonInputStream)
                .read(path, new TypeRef<List<T>>() {
                    @Override
                    public Type getType() {
                        return objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
                    }
                });
    }

}
