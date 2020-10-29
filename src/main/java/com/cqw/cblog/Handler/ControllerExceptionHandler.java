package com.cqw.cblog.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    public final Logger logger= LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(value=Exception.class)
    public ModelAndView ExceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);
        ModelAndView modelAndView=new ModelAndView();
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            modelAndView.addObject("msg",e.getMessage());
            throw e;
        }
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
