package com.dd.common.exception;

/**
 * @author Bryce_dd 2021/8/22
 */
public interface Assert {
    /**
     * 创建异常
     *
     * @param args 参数
     * @return 返回异常
     */
    BaseException newException(Object... args);

    BaseException newException(Throwable t,Object... args);

    default void assertNotNul(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }

}
