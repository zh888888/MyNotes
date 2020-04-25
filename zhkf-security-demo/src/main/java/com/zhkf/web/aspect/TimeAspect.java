package com.zhkf.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Aspect
//@Component
public class TimeAspect {

    @Around("execution(* com.zhkf.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{      //表示当前拦截的方法的一个对象
        System.out.println("tome aspect start");
        long start =new Date().getTime();
       Object object= proceedingJoinPoint.proceed();          //调用控制器中被拦截的方法
        System.out.println("time Aspect 耗时:"+(new Date().getTime()-start));
        System.out.println("time aspect end");
        return null;
    }
}
