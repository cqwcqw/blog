package com.cqw.cblog.aspect;

import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
//@Aspect
//@Component组件扫描
public class LogAspect {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.cqw.cblog..*.*(..))")
    public void log(){

    }
    @Before("log()")
//  Before哪个切入点
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String RequestClass=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        requestLog requestLog=new requestLog(url,ip,RequestClass,args);
        logger.info("RequestLog : "+requestLog);
    }
    @After("log()")
    public void doAfter(){
        logger.info("---------doAfter------------");
    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("result:{}"+result);
    }
    @Data
    private class requestLog{
        private String url;
        private String ip;
        private String RequestClass;
        private Object[] args;
        public requestLog(String url, String ip, String requestClass, Object[] args) {
            this.url = url;
            this.ip = ip;
            RequestClass = requestClass;
            this.args = args;
        }
    }
}
