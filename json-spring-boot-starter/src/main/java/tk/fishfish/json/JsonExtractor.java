package tk.fishfish.json;

/**
 * json extractor
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public interface JsonExtractor {

    Object extract(String json, String script);

}
