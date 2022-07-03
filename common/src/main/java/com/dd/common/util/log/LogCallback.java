package com.dd.common.util.log;

/**
 * @author Bryce_dd 2022/3/20 16:52
 */
@FunctionalInterface
public interface LogCallback {

    LogTextHolder call();
}
