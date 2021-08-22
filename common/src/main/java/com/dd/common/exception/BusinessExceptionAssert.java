package com.dd.common.exception;

import java.text.MessageFormat;

/**
 * @author Bryce_dd
 * @date 2021/8/22
 */
public interface BusinessExceptionAssert extends IResponseEnum,Assert{

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }

}
