# redis-spring-boot-starter

> 集成 redis

## 功能

- redis jackson 序列化
- redis 缓存 key 规则修改（用 : 分割）
- 抽象 Cache<T> 接口

## 依赖

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>admin-spring-boot-starter</artifactId>
    <version>1.5.0-SNAPSHOT</version>
</dependency>
```

## 快速使用

配置与 spring boot redis 一样：

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    database: 0
    password:
    timeout: 3s
    lettuce:
      # 关闭超时时间
      shutdown-timeout: 1s
      pool:
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 32
        # 连接池中的最大空闲连接
        max-idle: 8
        # 连接池中的最小空闲连接
        min-idle: 4
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: 300ms
  cache:
    type: redis
    redis:
      # 过期时间（ms）
      time-to-live: 300_000
      # 缓存null值
      cache-null-values: true
```

## 版本

### 1.5.0-SNAPSHOT

- redis jackson 序列化
- redis 缓存 key 规则修改（用 : 分割）
- 抽象 Cache<T> 接口
