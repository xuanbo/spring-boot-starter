# admin-spring-boot-starter

> 后台管理接口

## 功能

- 认证（OAuth2）
- 用户 
- 角色

## 依赖

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>admin-spring-boot-starter</artifactId>
    <version>1.5.0-SNAPSHOT</version>
</dependency>
```

## 快速使用

配置：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/fish
    username: root
    password: 123456
  # liquibase表结构管理
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/master.xml

mybatis:
  # mapper的xml位置
  mapper-locations: classpath:mapper/*Mapper.xml
  # 包别名
  type-aliases-package: tk.fishfish.mybatis.entity
  configuration:
    # 下划线转驼峰
    map-underscore-to-camel-case: true
  # 分⻚
  page-helper:
    helper-dialect: mysql
    reasonable: "true"
    params: count=countSql
  # 通用mapper
  mapper:
    mappers:
      - tk.fishfish.mybatis.repository.Repository
    not-empty: true
    safe-delete: true

fish:
  oauth2:
    # 认证服务器
    authorization:
      # token存储redis前缀
      tokenPrefix: 'fish:oauth2:'
      # 是否支持refresh token
      supportRefreshToken: true
      # access_token过期时间
      accessTokenValiditySeconds: 3600
      # refresh_token过期时间
      refreshTokenValiditySeconds: 7200

logging:
  level:
    root: INFO
    tk.fishfish: DEBUG
```

通过 @EnableAdmin 注解开启后台管理：

```java
@EnableAdmin
@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
public class AdminAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminAuthorizationApplication.class, args);
    }

}
```

## 版本

### 1.5.0-SNAPSHOT

- @EnableAdmin 开启后台管理特性
- 认证（OAuth2）
- 用户
- 角色
