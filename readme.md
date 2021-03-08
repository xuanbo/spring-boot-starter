# spring-boot-starter

> 自用 spring boot starter

## 模块

- [mybatis-spring-boot-starter](./mybatis-spring-boot-starter)

  - mybatis 集成 pagehelper、通用 mapper
  - 通用增删改查
  - 自定义 condition 查询，注解式查询
  - 枚举类型

- [rest-spring-boot-starter](./rest-spring-boot-starter)

  - 统一返回格式包装
  - 统一业务异常处理

## 依赖

在 pom.xml 中指定使用的 repository 地址

```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/xuanbo/spring-boot-starter</url>
  </repository>
</repositories>
```

然后依赖使用到的模块

```xml
<dependencies>
  <dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>rest-spring-boot-starter</artifactId>
    <version>使用到的版本</version>
  </dependency>
</dependencies>
```
