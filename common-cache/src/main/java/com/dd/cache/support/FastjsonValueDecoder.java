package com.dd.cache.support;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.support.AbstractValueDecoder;

/**
 * @author Bryce_dd 2022/1/9 16:55
 */
public class FastjsonValueDecoder extends AbstractValueDecoder {

    public static final FastjsonValueDecoder INSTANCE = new FastjsonValueDecoder(false);

    public FastjsonValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected Object doApply(byte[] buffer) {
        if (useIdentityNumber) {
            byte[] bytes = new byte[buffer.length - 4];
            System.arraycopy(buffer, 4, bytes, 0, bytes.length);
            return JSON.parse(bytes);
        } else {
        return JSON.parse(buffer);
        }
    }
}
