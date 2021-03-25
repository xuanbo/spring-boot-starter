package tk.fishfish.json.core;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import tk.fishfish.json.exception.JsonException;

/**
 * groovy json extractor
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class GroovyJsonExtractor implements JsonExtractor {

    private final Json json;
    private final JsonPath jsonPath;

    public GroovyJsonExtractor(Json json, JsonPath jsonPath) {
        this.json = json;
        this.jsonPath = jsonPath;
    }

    @Override
    public Object extract(String json, String script) {
        Binding binding = new Binding();
        binding.setVariable("json", this.json);
        binding.setVariable("jsonPath", jsonPath);
        binding.setVariable("value", json);
        GroovyShell shell = new GroovyShell(binding);
        try {
            return shell.evaluate(script);
        } catch (Exception e) {
            throw new JsonException("json提取异常", e);
        }
    }

}
