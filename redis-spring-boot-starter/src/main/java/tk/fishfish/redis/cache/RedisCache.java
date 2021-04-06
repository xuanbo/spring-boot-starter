package tk.fishfish.redis.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.CollectionUtils;
import tk.fishfish.cache.BulkLoadFunc;
import tk.fishfish.cache.Cache;
import tk.fishfish.cache.LoadFunc;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Redis实现
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public abstract class RedisCache<T> implements Cache<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private Jackson2JsonRedisSerializer<Object> jsonRedisSerializer;

    @Override
    @SuppressWarnings("unchecked")
    public T loadByKey(String key, LoadFunc<T> func) {
        if (key == null) {
            return null;
        }
        try (RedisConnection connection = getConnection()) {
            byte[] serializeKey = serializeKey(key);
            byte[] bytes = connection.get(serializeKey);
            if (bytes == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("未命中缓存: {}, 加载数据", key);
                }
                T value = func.loadByKey(key);
                if (value != null) {
                    byte[] serializeValue = jsonRedisSerializer.serialize(value);
                    if (serializeValue == null) {
                        throw new SerializationException("无法序列化");
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("数据放入缓存: {}", key);
                    }
                    connection.set(serializeKey, serializeValue, Expiration.seconds(expireSeconds()), RedisStringCommands.SetOption.UPSERT);
                }
                return value;
            }
            T value = (T) jsonRedisSerializer.deserialize(bytes);
            if (value == null) {
                throw new SerializationException("无法反序列化");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("命中缓存: {}", key);
            }
            return value;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> loadListByKey(String key, LoadFunc<List<T>> func) {
        if (key == null) {
            return null;
        }
        try (RedisConnection connection = getConnection()) {
            byte[] serializeKey = serializeKey(key);
            byte[] bytes = connection.get(serializeKey);
            if (bytes == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("未命中缓存: {}, 加载数据", key);
                }
                List<T> value = func.loadByKey(key);
                if (value != null) {
                    byte[] serializeValue = jsonRedisSerializer.serialize(value);
                    if (serializeValue == null) {
                        throw new SerializationException("无法序列化");
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug("数据放入缓存: {}", key);
                    }
                    connection.set(serializeKey, serializeValue, Expiration.seconds(expireSeconds()), RedisStringCommands.SetOption.UPSERT);
                }
                return value;
            }
            List<T> value = (List<T>) jsonRedisSerializer.deserialize(bytes);
            if (value == null) {
                throw new SerializationException("无法反序列化");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("命中缓存: {}", key);
            }
            return value;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> loadByKeys(List<String> keys, BulkLoadFunc<T> func) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        try (RedisConnection connection = getConnection()) {
            connection.openPipeline();
            for (String key : keys) {
                connection.get(serializeKey(key));
            }
            List<T> values = new LinkedList<>();
            List<String> missingKeys = new LinkedList<>();
            List<Object> cacheList = connection.closePipeline();
            for (int i = 0; i < cacheList.size(); i++) {
                Object cacheValue = cacheList.get(i);
                if (cacheValue == null) {
                    missingKeys.add(keys.get(i));
                    continue;
                }
                T value = (T) jsonRedisSerializer.deserialize(((byte[]) cacheValue));
                if (value == null) {
                    throw new SerializationException("无法反序列化");
                }
                values.add(value);
            }
            if (missingKeys.isEmpty()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("命中缓存: {}", keys);
                }
                return values;
            }
            // 未命中
            if (logger.isDebugEnabled()) {
                logger.debug("未命中缓存: {}, 加载数据", missingKeys);
            }
            // 无导入数据
            Map<String, T> loadMap = func.loadByKeys(missingKeys);
            if (CollectionUtils.isEmpty(loadMap)) {
                return values;
            }
            values.addAll(loadMap.values());
            if (logger.isDebugEnabled()) {
                logger.debug("数据放入缓存: {}", missingKeys);
            }
            // 放入缓存
            connection.openPipeline();
            for (Map.Entry<String, T> entry : loadMap.entrySet()) {
                byte[] serializeKey = serializeKey(entry.getKey());
                byte[] serializeValue = jsonRedisSerializer.serialize(entry.getValue());
                if (serializeValue == null) {
                    throw new SerializationException("无法序列化");
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("数据放入缓存: {}", entry.getKey());
                }
                connection.set(serializeKey, serializeValue, Expiration.seconds(expireSeconds()), RedisStringCommands.SetOption.UPSERT);
            }
            connection.closePipeline();
            return values;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> loadListByKeys(List<String> keys, BulkLoadFunc<List<T>> func) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        try (RedisConnection connection = getConnection()) {
            connection.openPipeline();
            for (String key : keys) {
                connection.get(serializeKey(key));
            }
            List<T> values = new LinkedList<>();
            List<String> missingKeys = new LinkedList<>();
            List<Object> cacheList = connection.closePipeline();
            for (int i = 0; i < cacheList.size(); i++) {
                Object cacheValue = cacheList.get(i);
                if (cacheValue == null) {
                    missingKeys.add(keys.get(i));
                    continue;
                }
                List<T> value = (List<T>) jsonRedisSerializer.deserialize(((byte[]) cacheValue));
                if (value == null) {
                    throw new SerializationException("无法反序列化");
                }
                values.addAll(value);
            }
            if (missingKeys.isEmpty()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("命中缓存: {}", keys);
                }
                return values;
            }
            // 未命中
            if (logger.isDebugEnabled()) {
                logger.debug("未命中缓存: {}, 加载数据", missingKeys);
            }
            // 无导入数据
            Map<String, List<T>> loadMap = func.loadByKeys(missingKeys);
            if (CollectionUtils.isEmpty(loadMap)) {
                return values;
            }
            loadMap.values().forEach(values::addAll);
            if (logger.isDebugEnabled()) {
                logger.debug("数据放入缓存: {}", missingKeys);
            }
            // 放入缓存
            connection.openPipeline();
            for (Map.Entry<String, List<T>> entry : loadMap.entrySet()) {
                byte[] serializeKey = serializeKey(entry.getKey());
                byte[] serializeValue = jsonRedisSerializer.serialize(entry.getValue());
                if (serializeValue == null) {
                    throw new SerializationException("无法序列化");
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("数据放入缓存: {}", entry.getKey());
                }
                connection.set(serializeKey, serializeValue, Expiration.seconds(expireSeconds()), RedisStringCommands.SetOption.UPSERT);
            }
            connection.closePipeline();
            return values;
        }
    }

    @Override
    public void evict(String key) {
        if (key == null) {
            return;
        }
        byte[] serializeKey = serializeKey(key);
        try (RedisConnection connection = getConnection()) {
            connection.del(serializeKey);
        }
    }

    @Override
    public void evict(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        byte[][] serializeKeys = keys.stream().map(this::serializeKey).toArray(byte[][]::new);
        try (RedisConnection connection = getConnection()) {
            connection.del(serializeKeys);
        }
    }

    @Override
    public void evict() {
        try (RedisConnection connection = getConnection()) {
            String pattern = prefix() + ":*";
            long count = 1000;
            while (true) {
                ScanOptions options = new ScanOptions.ScanOptionsBuilder().match(pattern).count(count).build();
                Cursor<byte[]> cursor = connection.scan(options);
                List<byte[]> keys = new LinkedList<>();
                while (cursor.hasNext()) {
                    keys.add(cursor.next());
                }
                if (!CollectionUtils.isEmpty(keys)) {
                    connection.del(keys.toArray(new byte[0][]));
                }
                // 最后一批数据
                if (CollectionUtils.isEmpty(keys) || keys.size() < count) {
                    break;
                }
            }
        }
    }

    protected String prefix() {
        return "";
    }

    protected long expireSeconds() {
        return 5 * 60;
    }

    private RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    private byte[] serializeKey(String key) {
        return (prefix() + key).getBytes(StandardCharsets.UTF_8);
    }

}
