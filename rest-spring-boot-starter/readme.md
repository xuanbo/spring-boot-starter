# rest-spring-boot-starter

> 统一业务异常处理，统一返回格式包装

## 依赖

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>rest-spring-boot-starter</artifactId>
    <version>1.5.0-SNAPSHOT</version>
</dependency>
```

## 统一业务异常处理

```java
@RestController
public class DemoController {

    @GetMapping("/list")
    public List<String> list() {
        throw new BizException(10001, "xxx错误");
    }

}
```

业务异常统一处理：

```json
{
    "code": 10001,
    "msg": "xxx错误",
    "data": null
}
```

## 统一返回格式包装

```java
@RestController
public class DemoController {

    @GetMapping("/list")
    public List<String> list() {
        return Arrays.asList("1", "2");
    }

}
```

默认（增加 @ApiResultIgnore 注解可忽略）对返回值进行统一包装，忽略`org.springframework.http.ResponseEntity`值类型。

```json
{
    "code": 0,
    "msg": null,
    "data": [
        "1",
        "2"
    ]
}
```

## 版本

### 1.5.0-SNAPSHOT

- BizException.of 构造异常

### 1.1.0.RELEASE

- 增加 @ApiResultIgnore 注解，显式声明不包装 API 返回结果

### 1.0.0.RELEASE

- 统一业务异常处理
- 统一 API 返回格式包装
