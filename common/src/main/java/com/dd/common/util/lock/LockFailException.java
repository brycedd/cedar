package com.dd.common.util.lock;

/**
 * @author Bryce_dd 2022/3/20 21:24
 */
public class LockFailException extends Exception{

    private static final long serialVersionUID = -1816495250718231702L;

    public LockFailException(String msg) {
        super(msg);
    }
}
