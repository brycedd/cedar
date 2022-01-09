package com.dd.cache.support;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.support.AbstractValueDecoder;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author Bryce_dd 2022/1/9 16:40
 */
public class JacksonValueDecoder extends AbstractValueDecoder {

    public static final JacksonValueDecoder INSTANCE = new JacksonValueDecoder(false);

    private Jackson2JsonRedisSerializer<CacheValueHolder> jackson2JsonRedisSerializer = create();

    public JacksonValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected Object doApply(byte[] buffer){
        if (buffer == null || buffer.length == 0) {
            return null;
        }
        try {
            return jackson2JsonRedisSerializer.deserialize(buffer);
        } catch (Exception e) {
            throw new SerializationException("Could not read JSON: " + e.getMessage(), e);
        }
    }

    private Jackson2JsonRedisSerializer create() {
        Jackson2JsonRedisSerializer<CacheValueHolder> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(CacheValueHolder.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }
}
