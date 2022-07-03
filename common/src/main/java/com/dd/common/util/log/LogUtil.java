package com.dd.common.util.log;

import com.dd.common.util.log.LogCallback;
import com.dd.common.util.log.LogTextHolder;
import org.slf4j.Logger;

import java.util.function.Consumer;

/**
 * @author Bryce_dd 2022/3/20 16:45
 */
public class LogUtil {
    /**
     *  请使用 LogUtil.infoLog(Logger, Consumer)
     */
    @Deprecated
    public static void infoLog(Logger log, String format, Object... arguments) {
        if (null != log && log.isInfoEnabled()) {
            log.info(format, arguments);
        }
    }

    /**
     *  请使用 LogUtil.errorLog(Logger, Consumer)
     */
    @Deprecated
    public static void errorLog(Logger log, String format, Object... arguments) {
        if (null != log && log.isErrorEnabled()) {
            log.error(format, arguments);
        }
    }

    /**
     *  请使用 LogUtil.warnLog(Logger, Consumer)
     */
    @Deprecated
    public static void warnLog(Logger log, String format, Object... arguments) {
        if (null != log && log.isWarnEnabled()) {
            log.warn(format, arguments);
        }
    }

    /**
     *  请使用 LogUtil.debugLog(Logger, Consumer)
     */
    @Deprecated
    public static void debugLog(Logger log, String format, Object... arguments) {
        if (null != log && log.isDebugEnabled()) {
            log.debug(format, arguments);
        }
    }

    /**
     *  请使用 LogUtil.traceLog(Logger, Consumer)
     */
    @Deprecated
    public static void traceLog(Logger log, String format, Object... arguments) {
        if (null != log && log.isTraceEnabled()) {
            log.trace(format, arguments);
        }
    }

    /**
     *  LogUtil.infoLog(log, () -> LogTextHolder.instance("{}", "我是要打印的日志内容"));
     */
    public static void infoLog(Logger log, LogCallback callback) {
        if (null != log && log.isInfoEnabled()) {
            LogTextHolder holder = callback.call();
            if (null != holder) {
                log.info(holder.getFormat(), holder.getArguments());
            }
        }
    }

    /**
     *  LogUtil.infoLog(log, c -> log.info("{}", "我是要打印的日志内容"));
     */
    public static void infoLog(Logger log, Consumer<Void> consumer) {
        if (null != log && log.isInfoEnabled()) {
            consumer.accept(null);
        }
    }

    /**
     *  LogUtil.errorLog(log, () -> LogTextHolder.instance("{}", "我是要打印的日志内容"));
     */
    public static void errorLog(Logger log, LogCallback callback) {
        if (null != log && log.isErrorEnabled()) {
            LogTextHolder holder = callback.call();
            if (null != holder) {
                log.error(holder.getFormat(), holder.getArguments());
            }
        }
    }

    /**
     *  LogUtil.infoLog(log, c -> log.error("{}", "我是要打印的日志内容"));
     */
    public static void errorLog(Logger log, Consumer<Void> consumer) {
        if (null != log && log.isErrorEnabled()) {
            consumer.accept(null);
        }
    }

    /**
     *  LogUtil.warnLog(log, () -> LogTextHolder.instance("{}", "我是要打印的日志内容"));
     */
    public static void warnLog(Logger log, LogCallback callback) {
        if (null != log && log.isWarnEnabled()) {
            LogTextHolder holder = callback.call();
            if (null != holder) {
                log.warn(holder.getFormat(), holder.getArguments());
            }
        }
    }

    /**
     *  LogUtil.infoLog(log, c -> log.warn("{}", "我是要打印的日志内容"));
     */
    public static void warnLog(Logger log, Consumer<Void> consumer) {
        if (null != log && log.isWarnEnabled()) {
            consumer.accept(null);
        }
    }

    /**
     *  LogUtil.debugLog(log, () -> LogTextHolder.instance("{}", "我是要打印的日志内容"));
     */
    public static void debugLog(Logger log, LogCallback callback) {
        if (null != log && log.isDebugEnabled()) {
            LogTextHolder holder = callback.call();
            if (null != holder) {
                log.debug(holder.getFormat(), holder.getArguments());
            }
        }
    }

    /**
     *  LogUtil.infoLog(log, c -> log.debug("{}", "我是要打印的日志内容"));
     */
    public static void debugLog(Logger log, Consumer<Void> consumer) {
        if (null != log && log.isDebugEnabled()) {
            consumer.accept(null);
        }
    }

    /**
     *  LogUtil.traceLog(log, () -> LogTextHolder.instance("{}", "我是要打印的日志内容"));
     */
    public static void traceLog(Logger log, LogCallback callback) {
        if (null != log && log.isTraceEnabled()) {
            LogTextHolder holder = callback.call();
            if (null != holder) {
                log.trace(holder.getFormat(), holder.getArguments());
            }
        }
    }

    /**
     *  LogUtil.infoLog(log, c -> log.trace("{}", "我是要打印的日志内容"));
     */
    public static void traceLog(Logger log, Consumer<Void> consumer) {
        if (null != log && log.isTraceEnabled()) {
            consumer.accept(null);
        }
    }
}
