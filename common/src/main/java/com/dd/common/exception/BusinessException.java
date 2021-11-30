package com.dd.common.exception;

/**
 * @author Bryce_dd 2021/8/22
 */
public class BusinessException extends BaseException{

    private static final long serialVersionUID = -2060550471039432071L;

    public BusinessException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public BusinessException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }

}
