package com.atguigu.handler;

import com.atguigu.exception.EduException;
import com.atguigu.response.RetVal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//只要出现异常 就会交给这个类进行处理
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RetVal error(Exception e){
        e.printStackTrace();
        System.out.println("全局异常生效了");
        return RetVal.error().message("全局异常生效了");
    }
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public RetVal error(ArithmeticException e){
        e.printStackTrace();
        System.out.println("特殊异常生效了");
        return RetVal.error().message("特殊异常生效了");
    }
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public RetVal error(EduException e){
        e.printStackTrace();
        System.out.println(e.getMessage());
        return RetVal.error().message(e.getMessage());
    }
}
