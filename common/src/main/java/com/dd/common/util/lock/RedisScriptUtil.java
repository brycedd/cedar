package com.dd.common.util.lock;

import com.dd.common.util.log.LogTextHolder;
import com.dd.common.util.log.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Bryce_dd 2022/3/20 17:54
 */
@Slf4j
@Component
public class RedisScriptUtil {

    private static RedisTemplate<String, Object> redisTemplate;
    private static RedisSerializer<String> stringRedisSerializer;

    /**
     * 此处，由于我们自己定义了redisTemplate，并更改了序列化方式，且类型为<String, Object> 所以，
     * 这个地方，也选择注入此类型 redisTemplate；
     * @param redisTemplate
     */
    @Autowired
    public void autowired(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate) {
        setRedisTemplate(redisTemplate);
        setStringRedisSerializer(redisTemplate.getStringSerializer());
    }

    public static void setStringRedisSerializer(RedisSerializer<String> stringSerializer) {
        RedisScriptUtil.stringRedisSerializer = stringSerializer;
    }

    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisScriptUtil.redisTemplate = redisTemplate;
    }

    /**
     * 执行redis脚本
     */
    public static String eval(RedisScript<String> redisScript, String key, String... args) {
        String obj = null;
        do {
            if (null == key || !isInit()) {
                break;
            }
            try {
                obj = redisTemplate.execute(redisScript,
                        stringRedisSerializer,
                        stringRedisSerializer,
                        Collections.singletonList(key),
                        Arrays.asList(args).toArray());
            } catch (Exception e) {
                log.error("####redisLock#### RedisScriptUtil.eval {} failed. key={},error={}", redisScript.getScriptAsString(), key, e);
                throw e;
            }
            String result = obj;
            LogUtil.debugLog(log, () -> LogTextHolder.instance("####redisLock#### RedisScriptUtil.eval {} key={}, value={}", redisScript.getScriptAsString(), key, result));
        } while (false);
        return obj;

    }

    private static boolean isInit() {
        if (null == redisTemplate) {
            log.error("####redisLock#### RedisScriptUtil.eval redisTemplate is not init");
            return false;
        }
        if (null == stringRedisSerializer) {
            log.error("####redisLock#### RedisScriptUtil.eval stringRedisSerializer is not init");
            return false;
        }
        return true;
    }

    public static long ttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }


}
