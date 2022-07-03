package com.dd.common.util.log;

import lombok.Data;

/**
 * @author Bryce_dd 2022/3/20 16:49
 */
@Data
public class LogTextHolder {
    private String format;
    private Object[] arguments;

    public LogTextHolder(String format, Object[] arguments) {
        this.format = format;
        this.arguments = arguments;
    }

    public static LogTextHolder instance(String format, Object... arguments) {
        return new LogTextHolder(format, arguments);


    }
}
