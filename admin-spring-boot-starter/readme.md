# admin-spring-boot-starter

> 后台管理接口

## 功能

- 认证（OAuth2）
- 用户
- 角色
- 审计

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

## OAuth2接口

### 获取token（password）

password 授权模式

```text
POST /oauth/token HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded

username=admin&password=123456&client_id=client&client_secret=secret&grant_type=password
```

返回：

```json
{
  "access_token": "430bfc69-205a-4050-81eb-42a3d3cc41e4",
  "token_type": "bearer",
  "refresh_token": "7b6f58b2-faa1-492c-b294-65a38eecf269",
  "expires_in": 29533,
  "scope": "read write"
}
```

### 获取token（client）

client_credentials 授权模式

```text
POST /oauth/token HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials&client_id=client&client_secret=secret
```

返回：

```json
{
  "access_token": "600d109a-b74e-4777-a368-9258737f826e",
  "token_type": "bearer",
  "expires_in": 43199,
  "scope": "read write"
}
```

### 获取token（authorization_code）

授权码模式

1. 先获取 code

    浏览器访问 /oauth/authorize 地址

    ```text
    http://localhost:8080/oauth/authorize?response_type=code&redirect_uri=http://localhost:8080&scope=read&client_id=client&client_secret=secret
    ```

    此时，跳转到登录界面

    ![authorize_code](docs/authorize_code_1.png)

    输入用户名密码后跳转到 code 获取地址：`http://localhost:8080/?code=eLX0VK`
    
    ![authorize_code](docs/authorize_code_2.png)

1. 根据 code 再获取 token

    ```text
    POST /oauth/token? HTTP/1.1
    Host: localhost:8080
    Content-Type: application/x-www-form-urlencoded
    
    grant_type=authorization_code&redirect_uri=http://localhost:8080&client_id=client&client_secret=secret&code=eLX0VK
    ```

   ![authorize_code](docs/authorize_code_3.png)

   注意，code 使用一次就失效了。

### 获取token（implicit）

简化模式

浏览器访问 /oauth/authorize 地址

```text
http://localhost:8080/oauth/authorize?response_type=token&redirect_uri=http://localhost:8080&scope=read&client_id=client&client_secret=secret
```

此时，还是跳转到登录界面，输入用户名密码后直接跳转到获取 token 页面

![implicit](docs/implicit.png)

### 刷新access_token

```text
POST /oauth/token HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded

client_id=client&client_secret=secret&refresh_token=7b6f58b2-faa1-492c-b294-65a38eecf269&grant_type=refresh_token
```

返回：

```json
{
  "access_token": "3e8fd2cf-c2fb-4307-bbcc-d7d6e970eed7",
  "token_type": "bearer",
  "refresh_token": "7b6f58b2-faa1-492c-b294-65a38eecf269",
  "expires_in": 43199,
  "scope": "read write"
}
```

### 检查token

需要将 client_id 与 client_secret 进行 Basic 认证

```text
POST /oauth/check_token HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Basic Y2xpZW50OnNlY3JldA==

token=3e8fd2cf-c2fb-4307-bbcc-d7d6e970eed7
```

返回：

```json5
{
  "aud": [
    "fish"
  ],
  "user_name": "admin",
  "scope": [
    "read",
    "write"
  ],
  // 在 spring boot oauth2 基础上定义的额外信息
  "extra": {
    "roles": [
      {
        "id": "1",
        "createdAt": "2021-03-16 02:18:01",
        "createdBy": "admin",
        "code": "ADMIN",
        "name": "管理员"
      }
    ],
    "user": {
      "id": "1",
      "createdAt": "2021-03-15 02:17:37",
      "createdBy": "admin",
      "username": "admin",
      "password": "$2a$10$TeIUF0hmJQULt4/wnM70pOQi8gnswVws3nUmz9tVezPEfJBGGWEEO",
      "name": "管理员",
      "sex": {
        "name": "男",
        "value": "0"
      },
      "accountLock": false,
      "enable": true
    }
  },
  "active": true,
  "exp": 1616782346,
  "authorities": [
    "ROLE_ADMIN"
  ],
  "client_id": "client"
}
```

### 总结

- 密码模式适合内部系统登录使用
- 客户端模式适合系统间交互
- 授权码、简化模式适合统一认证，单点登录

## 版本

### 1.5.0-SNAPSHOT

- @EnableAdmin 开启后台管理特性
- 认证（OAuth2）
- 用户
- 角色
- 审计
