package com.dd.cache.autoconfigure;

import com.alicp.jetcache.anno.SerialPolicy;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.dd.cache.support.FastjsonValueDecoder;
import com.dd.cache.support.FastjsonValueEncoder;
import com.dd.cache.support.JacksonValueDecoder;
import com.dd.cache.support.JacksonValueEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author Bryce_dd 2022/1/9 16:38
 */
@Configuration
public class FastJsonSerialAutoConfiguration {

    @Bean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider() {

            @Override
            public Function<Object, byte[]> parseValueEncoder(String valueEncoder) {
                String lowerValueEncoder = valueEncoder.toLowerCase();
                if ("fastjson".equals(lowerValueEncoder)) {
                    return new FastjsonValueEncoder(false);
                } else {
                    if ("jackson".equals(lowerValueEncoder)) {
                        return new JacksonValueEncoder(false);
                    } else {
                        return super.parseValueEncoder(valueEncoder);
                    }
                }
            }

            @Override
            public Function<byte[], Object> parseValueDecoder(String valueDecoder) {
                String toLowerCase = valueDecoder.toLowerCase();
                if ("fastjson".equals(toLowerCase)) {
                    return new FastjsonValueDecoder(false);
                } else {
                    if ("jackson".equals(toLowerCase)) {
                        return new JacksonValueDecoder(false);
                    } else {
                        return super.parseValueDecoder(valueDecoder);
                    }
                }
            }
        };
    }

    @Bean
    public SerialPolicy jsonSerialPolicy() {
        return new SerialPolicy() {
            @Override
            public Function<Object, byte[]> encoder() {
                return new FastjsonValueEncoder(true);
            }

            @Override
            public Function<byte[], Object> decoder() {
                return new FastjsonValueDecoder(true);
            }
        };
    }

    @Bean
    public SerialPolicy jacksonSerialPolicy() {
        return new SerialPolicy() {
            @Override
            public Function<Object, byte[]> encoder() {
                return new JacksonValueEncoder(true);
            }

            @Override
            public Function<byte[], Object> decoder() {
                return new JacksonValueDecoder(true);
            }
        };
    }
}
