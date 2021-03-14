# oauth2-spring-boot-starter

> 认证服务器、资源服务器

## 依赖

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>oauth2-spring-boot-starter</artifactId>
    <version>1.5.0-SNAPSHOT</version>
</dependency>
```

## 快速使用

### 认证服务器

对于单体服务，认证服务器与资源服务器都是自己，则均需要开启

配置

```yaml
fish:
  oauth2:
    authorization:
      # 是否支持refresh token
      supportRefreshToken: true
      # access_token过期时间
      accessTokenValiditySeconds: 3600
      # refresh_token过期时间
      RefreshTokenValiditySeconds: 7200
```

启动

```java
@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
public class AuthorizationApplication {

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT", "8080");
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
```

注意： @EnableAuthorizationServer 必须在 @EnableResourceServer 前面使用，否则会导致配置 tokenServices 错误。

#### 自定义UserDetailsService

可自定义 UserDetailsService Bean 提供用户信息

默认提供永远返回当前用户，密码为 123456 的 UserDetailsService 实现：

```java
@Bean
@ConditionalOnClass
public UserDetailsService userDetailsService() {
    // 永远返回当前用户，密码为123456
    log.warn("由于你没有自定义 {} , 默认配置永远返回当前用户，密码为123456的实现", UserDetailsService.class.getName());
    return username -> new User(username, "123456", Collections.emptyList());
}
```

#### 自定义PasswordEncoder

可自定义 PasswordEncoder Bean 进行密码加密

默认提供密码永远 123456，永远校验通过的 PasswordEncoder 实现：

```java
@Bean
@ConditionalOnClass
public PasswordEncoder passwordEncoder() {
    // 密码永远123456，永远校验通过
    log.warn("由于你没有自定义 {} , 默认配置密码永远123456，永远校验通过的实现", PasswordEncoder.class.getName());
    return new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return "123456";
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return true;
        }
    };
}
```

#### 自定义ClientDetailsService

可自定义 ClientDetailsService Bean 提供客户端信息

默认提供：

```java
@Bean
@ConditionalOnClass
public ClientDetailsServiceProvider clientDetailsServiceProvider() throws Exception {
    log.warn("由于你没有自定义 {} , 默认配置基于内存的客户端管理", ClientDetailsServiceProvider.class.getName());
    return clientId -> {
        BaseClientDetails details = new BaseClientDetails(clientId, "fish", "read,write", "password,refresh_token", "");
        details.setClientSecret("secret");
        return details;
    };
}
```

注意，自定义 ClientDetailsServiceProvider 接口继承 ClientDetailsService 防止认证时 stackoverflow 异常

因此需要提供 ClientDetailsServiceProvider 返回类型的自定义 Bean

### 资源服务器

对于微服务，每个业务模块统一远程访问认证服务器，则只需要开启资源服务器

配置

```yaml
fish:
  oauth2:
    resource:
      # 配置该服务的资源ID，如果认证不匹配则无法访问
      resourceId: fish
      # 忽略保护的路径
      ignorePatterns:
        - /v1/**
      remote:
        # 远程校验token地址
        checkTokenEndpointUrl: http://127.0.0.1:8080/oauth/check_token
        clientId: client
        clientSecret: secret
```

启动

```java
@EnableResourceServer
@SpringBootApplication
public class ResourceApplication {

    public static void main(String[] args) {
        System.setProperty("SERVER.PORT", "9090");
        SpringApplication.run(ResourceApplication.class, args);
    }

}
```

## 版本

### 1.5.0-SNAPSHOT

- @EnableResourceServer 开启资源服务器
- @EnableAuthorizationServer 开启认证服务器
