package com.example.test.aspect;

import com.alibaba.fastjson.JSON;
import com.example.test.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * <p>切面记录请求日志信息</p>
 *
 * @author neo
 * @date 2020/11/18 15:07
 */
@Aspect
@Component
@Slf4j
public class AopLog {
    private static final String REQUEST_TIME = "request-time";

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.example.test.controller.*Controller.*(..))||" +
            "execution(public * com.example.test.*.provider.*Provider.*(..))||"+
            "execution(public * com.example.test.*.op.*Provider.*(..))")
    public void log() {
        // Do nothing because of X and Y.
    }

    /**
     * 前置操作
     *
     * @param point 切入点
     */
    @Before("log()")
    public void beforeLog(JoinPoint point) {
        //获取日志输出类型
        MethodSignature sign = (MethodSignature) point.getSignature();
        Method method = sign.getMethod();
        SimpleResponseLog annotation = method.getAnnotation(SimpleResponseLog.class);
        if (annotation != null && annotation.logType() == LogType.UN_LOG) {
            //不输出
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Map<String, String[]> params = request.getParameterMap();
        String body = JSON.toJSONString(point.getArgs());
        log.info("[请求] IP:{} URL:{} Method:{} RequestParams:{} Body:{}",
                IPUtils.getIpAddr(request), request.getServletPath(), request.getMethod(), JSON.toJSONString(params), body);
        Long start = System.currentTimeMillis();
        request.setAttribute(REQUEST_TIME, start);
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        Long end = System.currentTimeMillis();
        //获取日志输出类型
        MethodSignature sign = (MethodSignature) point.getSignature();
        Method method = sign.getMethod();
        SimpleResponseLog annotation = method.getAnnotation(SimpleResponseLog.class);
        if (annotation != null && annotation.logType() == LogType.UN_LOG) {
            //不输出
            return result;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Long start = (Long) request.getAttribute(REQUEST_TIME);
        Map<String, String[]> params = request.getParameterMap();
        String body = JSON.toJSONString(point.getArgs());
        if (annotation != null && annotation.logType() == LogType.SIMPLE) {
            //简单输出
//            log.info("[请求] IP:{} URL:{}  OPENID:{} RequestParams:{} Body:{} 耗时:{}ms", IPUtils.getIpAddr(request), request.getServletPath(),
//                    request.getHeader("OPEN-ID"), JSON.toJSONString(params), body, end - start);

            log.info("[请求] IP:{} URL:{} RequestParams:{} Body:{} 耗时:{}ms", IPUtils.getIpAddr(request), request.getServletPath(),
                    JSON.toJSONString(params), body, end - start);
            return result;
        }
        //详细输出
//        log.info("[请求] IP:{} URL:{}  OPENID:{} RequestParams:{} Body:{} 返回值{} 耗时:{}ms", IPUtils.getIpAddr(request), request.getServletPath(),
//                request.getHeader("OPEN-ID"), JSON.toJSONString(params), body, String.valueOf(result), end - start);

        log.info("[请求] IP:{} URL:{} RequestParams:{} Body:{} 返回值{} 耗时:{}ms", IPUtils.getIpAddr(request), request.getServletPath(),
                 JSON.toJSONString(params), body, String.valueOf(result), end - start);

        return result;
    }

    @AfterThrowing(pointcut = "log()", throwing = "ex")
    public void AfterThrowing(JoinPoint point, Exception ex) {
        Long end = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Long start = (Long) request.getAttribute(REQUEST_TIME);
        Map<String, String[]> params = request.getParameterMap();
        String body = JSON.toJSONString(point.getArgs());
//        //输出
//        log.info("[请求] 异常响应 IP:{} URL:{}  OPENID:{} RequestParams:{} Body:{} 异常信息:{} 耗时:{}ms", IPUtils.getIpAddr(request), request.getServletPath(),
//                request.getHeader("OPEN-ID"), JSON.toJSONString(params), body, ex.getMessage(), end - start);
        //输出
        log.info("[请求] 异常响应 IP:{} URL:{}  RequestParams:{} Body:{} 异常信息:{} 耗时:{}ms", IPUtils.getIpAddr(request), request.getServletPath(), JSON.toJSONString(params), body, ex.getMessage(), end - start);
    }

}
