# json-spring-boot-starter

> json 业务库，方便业务统一使用

## 功能

- json 读写（基于 jackson 库）
- json path（基于 com.jayway.jsonpath 库）
- json 脚本提取（groovy 脚本）
- 统一 JSON 工具类

## 使用

### 依赖

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>json-spring-boot-starter</artifactId>
    <version>1.5.0-SNAPSHOT</version>
</dependency>
```

### json读写

提供 Json 接口进行序列化、反序列化

```java
package tk.fishfish.json;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.fishfish.json.core.Json;

import java.util.Map;

/**
 * json tests
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JacksonAutoConfiguration.class,
        JsonConfiguration.class
})
public class JsonTest {

    private final Logger logger = LoggerFactory.getLogger(JsonTest.class);

    @Autowired
    private Json json;

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
        Map<String, Object> map = this.json.readMap(json);
        logger.info("map: {}", map);
    }

}
```

### jsonpath

当我们对接某些接口时，只需要提取部分数据，此时基于 json path 的 [语法](https://github.com/json-path/JsonPath#operators) 进行数据提取

提供 JsonPath 接口处理 json path

```java
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
```

### json 脚本提取

某些 api 平台，需要对 json 结果进行转换或封装，则可基于 groovy 脚本进行数据提取与组装

提供 JsonExtractor 接口运行脚本。

```java
package tk.fishfish.json;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
        JsonConfiguration.class
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
```

其中，脚本中默认变量：

- value

  extract 方法传递的 json 字符串

- json

  Json 对象，可进行序列化、反序列化

- jsonPath

  JsonPath 对象，可进行 json path 语法提取

注意：

- 在 groovy 中，$ 是特殊的字符串插值变量，因此需要转义

## 版本

### 1.5.0-SNAPSHOT

- 迁移到 github 包管理
- 统一 JSON 工具类

### 1.0.0.RELEASE

依赖：

- Spring Boot 2.3.7.RELEASE
- Jackson 2.11.3
- json-path 2.4.0
