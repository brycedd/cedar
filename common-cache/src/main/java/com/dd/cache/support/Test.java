package com.dd.cache.support;

import com.alicp.jetcache.CacheValueHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bryce_dd 2022/1/9 19:12
 */
public class Test {

    public static void main(String[] args) {
        User user = new User();
        User user2 = new User();
        user.setName("dd");
        user2.setName("tt");
        user.setAge(12);
        user2.setAge(22);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        CacheValueHolder<Object> cacheValueHolder = new CacheValueHolder<>();
        cacheValueHolder.setValue(userList);

        JacksonValueEncoder encoder = JacksonValueEncoder.INSTANCE;
        byte[] bytes = encoder.apply(cacheValueHolder);

        JacksonValueDecoder decoder = JacksonValueDecoder.INSTANCE;
        Object o = decoder.apply(bytes);

        CacheValueHolder ch = (CacheValueHolder) decoder.apply(bytes);
        Object value = ch.getValue();
    }
}
