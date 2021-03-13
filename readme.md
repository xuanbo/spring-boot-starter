# spring-boot-starter

> 自用 spring boot starter

## 版本

- Spring Boot 2.3.7.RELEASE

## 模块

- [mybatis-spring-boot-starter](./mybatis-spring-boot-starter)

  - mybatis 集成 pagehelper、通用 mapper
  - 通用增删改查
  - 自定义 condition 查询，注解式查询
  - 枚举类型

- [rest-spring-boot-starter](./rest-spring-boot-starter)

  - 统一返回格式包装
  - 统一业务异常处理

- [json-spring-boot-starter](./json-spring-boot-starter)

  - json 读写（基于 jackson 库）
  - json path (基于 com.jayway.jsonpath 库)
  - json 脚本提取（groovy 脚本）

- [oauth2-spring-boot-starter](./oauth2-spring-boot-starter)

 - @EnableResourceServer 开启资源服务器
 - @EnableAuthorizationServer 开启认证服务器

## 依赖

- 在 maven 的 settings.xml 文件 (～/.m2/settings.xml) 中增加 Github 认证，[配置文档](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages)

  ```xml
  <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                        http://maven.apache.org/xsd/settings-1.0.0.xsd">
  
    <servers>
      <server>
        <id>github</id>
        <!-- 你的 GitHub 账号 -->
        <username>USERNAME</username>
        <!-- 申请到的 TOKEN -->
        <!-- 在该 https://github.com/settings/tokens 地址下的 Personal access tokens 中创建一个，赋予 write:packages 权限 -->
        <password>TOKEN</password>
      </server>
    </servers>
  
  </settings>
  ```

- 在项目 pom.xml 中指定该仓库地址

  ```xml
  <repositories>
    <repository>
      <id>github</id>
      <url>https://maven.pkg.github.com/xuanbo/spring-boot-starter</url>
    </repository>
  </repositories>
  ```

- 依赖使用到的模块，比如：

  ```xml
  <dependencies>
    <dependency>
      <groupId>tk.fishfish</groupId>
      <artifactId>rest-spring-boot-starter</artifactId>
      <version>使用到的版本</version>
    </dependency>
  </dependencies>
  ```
