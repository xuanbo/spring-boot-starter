# actuator-spring-boot-starter

> actuator 监控

## 功能

- HTTP 调用耗时日志记录

## 依赖

```xml
<dependency>
    <groupId>tk.fishfish</groupId>
    <artifactId>actuator-spring-boot-starter</artifactId>
    <version>1.5.0-SNAPSHOT</version>
</dependency>
```

### 快速使用

开启 spring boot actuator 配置后：

```yaml
# actuator 允许 httptrace
management:
  endpoints:
    web:
      exposure:
        include: httptrace

fish:
  actuator:
    http:
      # 500毫秒记录
      timeout: 500
```

## 版本

### 1.5.0-SNAPSHOT

- HTTP 调用耗时日志记录
