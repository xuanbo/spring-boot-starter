package tk.fishfish.json;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.json.autoconfigure.JsonAutoConfiguration;
import tk.fishfish.json.core.JsonExtractor;

/**
 * json extractor tests
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JacksonAutoConfiguration.class,
        JsonAutoConfiguration.class
})
public class JsonExtractorTest {

    private final Logger logger = LoggerFactory.getLogger(JsonExtractorTest.class);

    @Autowired
    private JsonExtractor jsonExtractor;

    @Test
    public void extract() {
        String json = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";
        Object result = jsonExtractor.extract(json,
                "list = jsonPath.readList(value, '\\$.store.book')\n" +
                        "bicycle = jsonPath.readMap(value, '\\$.store.bicycle')\n" +
                        "list.each{ book ->\n" +
                        "    book['bicycleColor'] = bicycle.color\n" +
                        "    book['bicyclePrice'] = bicycle.price\n" +
                        "}\n" +
                        "list");
        logger.info("result: {}", result);
    }

}
