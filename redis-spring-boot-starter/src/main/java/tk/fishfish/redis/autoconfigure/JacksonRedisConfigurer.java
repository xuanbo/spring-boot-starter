package tk.fishfish.redis.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis jackson自定义
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public interface JacksonRedisConfigurer {

    void configure(ObjectMapper om);

}
