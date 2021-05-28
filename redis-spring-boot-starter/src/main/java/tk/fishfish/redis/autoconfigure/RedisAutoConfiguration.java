package tk.fishfish.redis.autoconfigure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;
import java.util.List;

/**
 * redis配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@Slf4j
@Configuration
@EnableCaching
@EnableRedisHttpSession
@AutoConfigureBefore(org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class RedisAutoConfiguration {

    @Autowired(required = false)
    private List<Module> modules;

    @Autowired(required = false)
    private List<JacksonRedisConfigurer> configurers;

    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        ObjectMapper om = new ObjectMapper();
        if (configurers == null) {
            log.info("配置默认 redis jackson 序列化实现，可通过 {} 自定义 {}", JacksonRedisConfigurer.class.getName(), ObjectMapper.class.getName());
            if (modules != null) {
                modules.forEach(om::registerModule);
            }
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // 范型时用 @class 标志具体的类型
            om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        } else {
            log.info("检测到自定义 redis jackson 序列化");
            // 自定义配置
            for (JacksonRedisConfigurer configurer : configurers) {
                configurer.configure(om);
            }
        }
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory, Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        setSerializer(template, jsonRedisSerializer);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory, Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        setSerializer(template, jsonRedisSerializer);
        return template;
    }

    private void setSerializer(RedisTemplate<?, ?> redisTemplate, Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties, Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer)
        ).serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
        );
        // 过期时间默认5分钟
        if (redisProperties.getTimeToLive() == null) {
            config = config.entryTtl(Duration.ofMinutes(5));
        } else {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        // 修改默认的cacheName::
        if (redisProperties.getKeyPrefix() == null) {
            config = config.computePrefixWith(cacheName -> cacheName + ":");
        } else {
            config = config.computePrefixWith(cacheName -> redisProperties.getKeyPrefix() + ":" + cacheName + ":");
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

}
