//package com.example.ooad_project_backend.exception;
//
//
//
//import com.example.ooad_project_backend.common.Result;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//@ControllerAdvice(basePackages="com.example.controller")
//public class GlobalExceptionHandler {
//
//
//
//
//    //统一异常处理@ExceptionHandler,主要用于Exception
//    @ExceptionHandler(Exception.class)
//    @ResponseBody//返回json串
//    public Result error(HttpServletRequest request, Exception e){
//
//        return Result.error();
//    }
//
//    @ExceptionHandler(CustomException.class)
//    @ResponseBody//返回json串
//    public Result customError(HttpServletRequest request, CustomException e){
//        return Result.error(e.getCode(), e.getMsg());
//    }
//}
