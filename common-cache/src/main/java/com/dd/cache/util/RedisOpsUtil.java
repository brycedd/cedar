package com.dd.cache.util;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Bryce_dd 2021/11/17 23:54
 */
@AllArgsConstructor(onConstructor_ = @Lazy)
public class RedisOpsUtil {

    private RedisTemplate redisTemplate;

    public void set(String key,Object value) {
        redisTemplate.opsForValue().set(key,value);

    }
}
