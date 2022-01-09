package com.dd.cache.support;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.support.AbstractValueEncoder;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author Bryce_dd 2022/1/9 16:40
 */
public class JacksonValueEncoder extends AbstractValueEncoder {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    public static final JacksonValueEncoder INSTANCE = new JacksonValueEncoder(false);

    private Jackson2JsonRedisSerializer<CacheValueHolder> jackson2JsonRedisSerializer = create();

    protected static int IDENTITY_NUMBER = 0x4A953A81;

    public JacksonValueEncoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    public byte[] apply(Object value) {

        if (null == value)
            return EMPTY_ARRAY;
        try {
            return this.jackson2JsonRedisSerializer.serialize(value);
        } catch (Exception e) {
            throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
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
