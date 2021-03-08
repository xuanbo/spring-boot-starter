package tk.fishfish.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

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

    private final TypeRef<Map<String, Object>> mapTypeRef = new TypeRef<Map<String, Object>>() {
    };
    private final TypeRef<List<Map<String, Object>>> listMapTypeRef = new TypeRef<List<Map<String, Object>>>() {
    };

    public JacksonJsonPath(ObjectMapper objectMapper) {
        configuration = new Configuration.ConfigurationBuilder()
                .jsonProvider(new JacksonJsonProvider(objectMapper))
                .mappingProvider(new JacksonMappingProvider(objectMapper))
                .options(EnumSet.noneOf(Option.class))
                .build();
    }

    @Override
    public <T> T read(String json, String path) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(json)
                .read(path);
    }

    @Override
    public Map<String, Object> readMap(String json, String path) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(json)
                .read(path, mapTypeRef);
    }

    @Override
    public List<Map<String, Object>> readList(String json, String path) {
        return com.jayway.jsonpath.JsonPath
                .using(configuration)
                .parse(json)
                .read(path, listMapTypeRef);
    }

}
