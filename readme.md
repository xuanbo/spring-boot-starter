# spring-boot-starter

> 自用 spring boot starter ，后台开发利器

## 版本

- Spring Boot 2.3.7.RELEASE

## 模块

- [core-spring-boot-starter](./core-spring-boot-starter)

    - 实体、枚举定义

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
    - json path（基于 com.jayway.jsonpath 库）
    - json 脚本提取（groovy 脚本）
    - 统一 JSON 工具类

- [redis-spring-boot-starter](./redis-spring-boot-starter)

    - redis jackson 序列化
    - redis 缓存 key 规则修改

- [oauth2-spring-boot-starter](./oauth2-spring-boot-starter)

    - @EnableAuthorizationServer 开启认证服务器
    - @EnableResourceServer 开启资源服务器

- [actuator-spring-boot-starter](./actuator-spring-boot-starter)

    - 指标监控

- [admin-spring-boot-starter](./admin-spring-boot-starter)

    - @EnableAdmin 开启后台管理特性
    - 认证、用户、角色

- [codegen](./codegen)

    - 可视化代码生成器服务，适配 admin-spring-boot-starter 模块约定
    - 内嵌 sqlite ，连接外部数据库（MySQL）进行代码生成
    - 自定义模板

- [excel-spring-boot-starter](./excel-spring-boot-starter)

    - 利用 easyexcel 进行数据导入导出

## 依赖

- 在 maven 的 settings.xml 文件 (～/.m2/settings.xml) 中增加 Github
  认证，[配置文档](https://docs.github.com/en/packages/guides/configuring-apache-maven-for-use-with-github-packages)

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
      <artifactId>admin-spring-boot-starter</artifactId>
      <version>使用到的版本</version>
    </dependency>
  </dependencies>
  ```
