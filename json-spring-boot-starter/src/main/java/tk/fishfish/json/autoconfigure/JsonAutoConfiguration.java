package tk.fishfish.json.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.fishfish.json.GroovyJsonExtractor;
import tk.fishfish.json.JacksonJson;
import tk.fishfish.json.JacksonJsonPath;
import tk.fishfish.json.Json;
import tk.fishfish.json.JsonExtractor;
import tk.fishfish.json.JsonPath;
import tk.fishfish.json.util.JSON;

/**
 * json configuration
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@Configuration
@ConditionalOnBean(ObjectMapper.class)
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class JsonAutoConfiguration {

    @Bean
    public Json json(ObjectMapper objectMapper) {
        JacksonJson json = new JacksonJson(objectMapper);
        JSON.setJson(json);
        return json;
    }

    @Bean
    public JsonPath jsonPath(ObjectMapper objectMapper) {
        return new JacksonJsonPath(objectMapper);
    }

    @Bean
    public JsonExtractor jsonExtractor(Json json, JsonPath jsonPath) {
        return new GroovyJsonExtractor(json, jsonPath);
    }

}
