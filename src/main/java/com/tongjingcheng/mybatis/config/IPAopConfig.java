package com.tongjingcheng.mybatis.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author tongjingcheng
 * @since 2019-01-31 14:48
 */
@Configuration
@Aspect
@Order(-99) // 控制多个Aspect的执行顺序，越小越先执行,为了要在Spring的事务之后执行,所以给他设置99
public class IPAopConfig {


    private final static String REQUEST_ID = "requestId";
    private static final Logger logger = LoggerFactory.getLogger(IPAopConfig.class);

    /**
     * 定义切点Pointcut
     * 第一个*号：表示返回类型， *号表示所有的类型
     * 第二个*号：表示类名，*号表示所有的类
     * 第三个*号：表示方法名，*号表示所有的方法
     * 后面括弧里面表示方法的参数，两个句点表示任何参数
     */
    @Pointcut("execution(* com.tongjingcheng.mybatis.controller..*.*(..))")
    public void executionService() {

    }


    /**
     * 方法调用之前调用
     * @param joinPoint
     */
    @Before(value = "executionService()")
    public void doBefore(JoinPoint joinPoint){
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        String remoteIp;
        remoteIp = IPAopConfig.getRemoteIp(request);
        String uuid;
        uuid = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, uuid);
        logger.info("本次请求=> uuid:{}, client ip:{}, X-Forwarded-For:{}", uuid, remoteIp, xForwardedForHeader);


        String className=joinPoint.getTarget().getClass().getName();
        String methodName=joinPoint.getSignature().getName();
        logger.info("@Before请求数据：class_method={}，args={}",className+"."+methodName+"()", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 方法之后调用
     * @param joinPoint
     * @param returnValue
     */
    @AfterReturning(pointcut = "executionService()",returning="returnValue")
    public void  doAfterReturning(JoinPoint joinPoint,Object returnValue){
        String uuid = MDC.get(REQUEST_ID);
        logger.info("@AfterReturning：响应参数为：{}",returnValue);
        logger.info("本次请求结束=> uuid ({}) from logger", uuid);
        MDC.remove(REQUEST_ID);
        MDC.clear();
    }

    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    @Around("executionService()")
    public Object timeAround(ProceedingJoinPoint joinPoint) {

        //获取开始执行的时间
        long startTime = System.currentTimeMillis();

        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        //Object[] args = joinPoint.getArgs();

        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error("=====>统计某方法执行耗时环绕通知出错", e);
        }

        // 获取执行结束的时间
        long endTime = System.currentTimeMillis();
        // 打印耗时的信息
        logger.info("=====>处理本次请求共耗时：{} ms",endTime-startTime);
        return obj;
    }

    /**
     * 获取来源ip
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
