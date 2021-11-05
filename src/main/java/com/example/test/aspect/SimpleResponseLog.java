package com.example.test.aspect;

import java.lang.annotation.*;

/**
 * <p>AOP日志输出格式注解
 * </p>
 *
 * @author neo
 * @date 2021/1/22 15:51
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleResponseLog {
    /**
     * 输出类型 默认简单输出
     */
    LogType logType() default LogType.SIMPLE;
}
