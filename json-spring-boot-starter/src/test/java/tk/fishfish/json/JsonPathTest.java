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
import tk.fishfish.json.core.JsonPath;

import java.util.List;
import java.util.Map;

/**
 * json path tests
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JacksonAutoConfiguration.class,
        JsonAutoConfiguration.class
})
public class JsonPathTest {

    private final Logger logger = LoggerFactory.getLogger(JsonPathTest.class);

    @Autowired
    private JsonPath jsonPath;

    @Test
    public void read() {
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
        List<Map<String, Object>> list = jsonPath.readList(json, "$.store.book");
        logger.info("list: {}", list);

        List<Book> books = jsonPath.readList(json, "$.store.book", Book.class);
        logger.info("books: {}", books);

        Map<String, Object> map = jsonPath.readMap(json, "$.store.bicycle");
        logger.info("map: {}", map);
    }

    public static class Book {

        private String category;

        private String author;

        private String title;

        private Double price;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "category='" + category + '\'' +
                    ", author='" + author + '\'' +
                    ", title='" + title + '\'' +
                    ", price=" + price +
                    '}';
        }

    }

}
