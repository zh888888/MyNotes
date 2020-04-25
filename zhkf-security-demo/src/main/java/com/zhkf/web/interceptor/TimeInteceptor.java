package com.zhkf.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TimeInteceptor implements HandlerInterceptor {

    //在控制器方法之前调用
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle) throws Exception {
        System.out.println("preHandle");
        httpServletRequest.setAttribute("startTime",new Date().getTime());

        System.out.println(((HandlerMethod)handle).getBean().getClass().getName());
        System.out.println(((HandlerMethod)handle).getMethod().getName());
        return true;
    }

    //在控制器方法之后调用，如果控制器里抛出异常，这个方法就不会被调用了
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long start = (long)httpServletRequest.getAttribute("startTime");
        System.out.println("time interceptor 耗时:"+(new Date().getTime()-start));
    }

    //在控制器方法之后调用，无论控制器是否抛出异常，这个方法都会被调用
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("pafterCompletion");
        Long start = (long)httpServletRequest.getAttribute("startTime");
        System.out.println("time interceptor 耗时:"+(new Date().getTime()-start));
        System.out.println("e is "+e);
    }
}
