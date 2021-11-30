package com.dd.product.component;

/**
 * @author Bryce_dd 2021/8/1
 */
public interface ZKLock {

    boolean lock(String lockPath);
    boolean unLock(String lockPath);
}
