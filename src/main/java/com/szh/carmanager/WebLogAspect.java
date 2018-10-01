/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @autoor szh
 */

package com.szh.carmanager;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author  szh
 * QQ:873689
 * @date 2018-7-19 8:35:47
 */


@Log4j
@Aspect
@Component
public class WebLogAspect {

    
    @Pointcut("execution(public * com.szh.carmanager.controller.*.*(..))")
    public void webLog(){
        
    }
    
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        //收到请求，记录请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求
        log.info("URL : "+request.getRequestURL());
        log.info("HTTP_METHOD : "+request.getMethod());
        log.info("IP : "+ request.getRemoteAddr());
        log.info("USER : "+request.getRemoteUser());
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String name = (String) en.nextElement();
            log.info("name: "+name+" ,value: "+request.getParameter(name));
        }
    }
    
    
    @AfterReturning(returning = "ret" , pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable{
        //处理完请求，返回内容
        log.info("RESPONSE : "+ ret);
    }
    
    
}
