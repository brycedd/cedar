package com.dd.common.util.lock;

/**
 * @author Bryce_dd 2022/3/20 21:22
 */
@FunctionalInterface
public interface Handler<T> {

    T callback();
}
