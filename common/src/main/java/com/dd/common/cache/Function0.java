package com.dd.common.cache;

/**
 * @author Bryce_dd 2021/12/13 22:34
 */
@FunctionalInterface
public interface Function0<T> {
    T apply() throws Exception;
}
